package net.shan.practice.cache;

import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by sam.js on 14-1-21.
 */
public class Performance {

    public static void main(String[] args){
        new Performance().LRUCacheTest2();
    }

    //TEST SerializationUtils.serialize 基本类型和自定义类型的大小
    private void calSize(){
        Integer i = new Integer(100);
        String s = "abcdfdfddf";
//        System.out.println(SerializationUtils.serialize(i).length + " " + SerializationUtils.serialize(s).length + " " +SerializationUtils.serialize(obj).length);
        System.out.println(SerializationUtils.serialize(i).length + " " + SerializationUtils.serialize(s).length );
    }

    //TEST 并发下的各项指标的准确性
    final static LRUCache cache = new LRUCache();
    class Task implements Runnable {
        CountDownLatch startSignal = null;
        CountDownLatch doneSignal = null;
        Task(CountDownLatch startSignal, CountDownLatch doneSignal){
            this.startSignal = startSignal;
            this.doneSignal = doneSignal;
        }
        @Override
        public void run() {
            try{
                startSignal.await();
                for(int i=0; i<100; i++){
                    cache.put(i, i);
                }
                doneSignal.countDown();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

    private void LRUCacheTest1(){
        int count = 100;
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(count);
        ExecutorService services = Executors.newFixedThreadPool(count);
        for(int i=0; i<count; i++){
            services.execute(new Task(startSignal, doneSignal));
        }
        startSignal.countDown();
        System.out.println("Start=======================>>>>>");
        try {
            doneSignal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("END=======================>>>>>");
        System.out.println(cache.toString());
    }

    //TEST 并发下的性能
    static AtomicInteger putCount = new AtomicInteger(0);
    static AtomicInteger getCount = new AtomicInteger(0);
    class PutTask implements Runnable {
        CountDownLatch startSignal = null;
        CountDownLatch doneSignal = null;
        int id = 0;
        PutTask(int id, CountDownLatch startSignal, CountDownLatch doneSignal){
            this.id = id;
            this.startSignal = startSignal;
            this.doneSignal = doneSignal;
        }
        @Override
        public void run() {
            try{
                id = id * 1000000;
                startSignal.await();
                for(int i=0; i<1000000; i++){
                    cache.put(id+i, i);
                    putCount.incrementAndGet();
                }
                doneSignal.countDown();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
    class GetTask implements Runnable {
        CountDownLatch startSignal = null;
        CountDownLatch doneSignal = null;
        int id = 0;
        GetTask(int id, CountDownLatch startSignal, CountDownLatch doneSignal){
            this.id = id;
            this.startSignal = startSignal;
            this.doneSignal = doneSignal;
        }
        @Override
        public void run() {
            try{
                id = id * 1000000;
                startSignal.await();
                for(int i=0; i<1000000; i++){
                    cache.get(id+i);
                    getCount.incrementAndGet();
                }
                doneSignal.countDown();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
    private void LRUCacheTest2(){
        int count = 100;
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(count);
        ExecutorService services = Executors.newFixedThreadPool(count);
        for(int i=0; i<50; i++){
            services.execute(new PutTask(i, startSignal, doneSignal));
        }
        for(int i=0; i<50; i++){
            services.execute(new GetTask(i, startSignal, doneSignal));
        }
        long start = System.currentTimeMillis();
        startSignal.countDown();
        System.out.println("Start=======================>>>>>");
        try {
            doneSignal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("END=======================>>>>>" + (System.currentTimeMillis()-start));
        System.out.println(cache.toString());
        System.out.println(putCount.intValue() + " " + getCount.intValue());

    }
}
