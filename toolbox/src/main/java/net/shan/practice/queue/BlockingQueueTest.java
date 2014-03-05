package net.shan.practice.queue;

import org.apache.commons.collections.Buffer;
import org.apache.commons.collections.BufferUtils;
import org.apache.commons.collections.buffer.CircularFifoBuffer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by sam.js on 14-3-5.
 */
public class BlockingQueueTest {
    public final BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(1000);
    public final Buffer queueFifo = BufferUtils.blockingBuffer(new CircularFifoBuffer());

    public final AtomicLong producerCount = new AtomicLong();
    public final AtomicLong consumerCount = new AtomicLong();
    private final int threadNums = Runtime.getRuntime().availableProcessors() + 1;
    private final ExecutorService producerServices = Executors.newFixedThreadPool(threadNums);
    private final ExecutorService consumerServices = Executors.newFixedThreadPool(threadNums);
    private final CountDownLatch startSignal = new CountDownLatch(1);

    private final List<Future> producerFutures = new ArrayList<Future>();
    private final List<Future> consumerFutures = new ArrayList<Future>();

    private boolean flag = true;

    public static void main(String[] args){
//        new BlockingQueueTest().testBlockingQueue();
//        new BlockingQueueTest().testCircleFifoBuffer();
        new BlockingQueueTest().testBlockingQueueFull();
    }

    private void testBlockingQueueFull(){
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(3);
        for(int i=0; i<10; i++){
            System.out.println(queue.offer(i));
        }
        System.out.println("test:" + queue.size());

    }

    private void testBlockingQueue(){
        for(int i=0; i<threadNums; i++){
            producerFutures.add(producerServices.submit(new Producer(startSignal, queue, producerCount, flag)));
        }
        for(int i=0; i<threadNums; i++){
            consumerFutures.add(consumerServices.submit(new Consumer(queue, consumerCount, flag)));
        }
        startSignal.countDown();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag = false;
        producerServices.shutdown();
        consumerServices.shutdown();
        System.out.println("producer:" + producerCount.get() + " consumer:" + consumerCount.get());
//        for(Future tmp:producerFutures){
//            tmp.cancel(true);
//        }
//        for(Future tmp:consumerFutures){
//            tmp.cancel(true);
//        }

    }

    private void testCircleFifoBuffer(){
        for(int i=0; i<threadNums; i++){
            producerFutures.add(producerServices.submit(new ProducerFifo(startSignal, queueFifo, producerCount, flag)));
        }
        for(int i=0; i<threadNums; i++){
            consumerFutures.add(consumerServices.submit(new ConsumerFifo(queueFifo, consumerCount, flag)));
        }
        startSignal.countDown();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag = false;
        producerServices.shutdown();
        consumerServices.shutdown();
        System.out.println("producer:" + producerCount.get() + " consumer:" + consumerCount.get());
//        for(Future tmp:producerFutures){
//            tmp.cancel(true);
//        }
//        for(Future tmp:consumerFutures){
//            tmp.cancel(true);
//        }

    }



}

class Producer implements Runnable {
    private CountDownLatch startSignal = null;
    private BlockingQueue<Integer> queue = null;
    private AtomicLong count = null;
    private boolean flag = true;

    Producer(CountDownLatch startSignal, BlockingQueue<Integer> queue, AtomicLong count, boolean flag){
        this.startSignal = startSignal;
        this.queue = queue;
        this.count = count;
        this.flag = flag;
    }
    @Override
    public void run() {
        try {
            startSignal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Random r = new Random();
        while(flag){
            try {
                queue.put(r.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count.incrementAndGet();
        }

    }
}

class Consumer implements Runnable {
    private AtomicLong count = null;
    private BlockingQueue<Integer> queue = null;
    private boolean flag = true;
    Consumer(BlockingQueue<Integer> queue, AtomicLong count, boolean flag){
        this.count = count;
        this.queue = queue;
        this.flag = flag;
    }
    @Override
    public void run() {
        while(flag){
            try {
                queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count.incrementAndGet();
        }
    }
}

class ProducerFifo implements Runnable {
    private CountDownLatch startSignal = null;
    private Buffer queue = null;
    private AtomicLong count = null;
    private boolean flag = true;

    ProducerFifo(CountDownLatch startSignal, Buffer queue, AtomicLong count, boolean flag){
        this.startSignal = startSignal;
        this.queue = queue;
        this.count = count;
        this.flag = flag;
    }
    @Override
    public void run() {
        try {
            startSignal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Random r = new Random();
        while(flag){
            queue.add(r.nextInt(1000));
            count.incrementAndGet();
        }

    }
}

class ConsumerFifo implements Runnable {
    private AtomicLong count = null;
    private Buffer queue = null;
    private boolean flag = true;
    ConsumerFifo(Buffer queue, AtomicLong count, boolean flag){
        this.count = count;
        this.queue = queue;
        this.flag = flag;
    }
    @Override
    public void run() {
        while(flag){
            queue.remove();
            count.incrementAndGet();
        }
    }
}

