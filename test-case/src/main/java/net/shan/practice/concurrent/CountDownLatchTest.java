package net.shan.practice.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by sam.js on 14-1-21.
 */
public class CountDownLatchTest {
    static int COUNT = 10;


    AtomicInteger result = new AtomicInteger(0);

    public static void main(String[] args){
        new CountDownLatchTest().test();
    }

    private void test(){
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(COUNT);

        ExecutorService services = Executors.newFixedThreadPool(COUNT);
        for(int i=0; i<COUNT; i++){
            services.execute(new Task(startSignal, doneSignal));
        }
        startSignal.countDown();
        System.out.println("Start=======================>>>>>");
        try{
            doneSignal.await();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        System.out.println("END=======================>>>>>");
        System.out.println(result.intValue());

    }

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
                for(int i=0; i<10; i++){
                    Thread.sleep(100);
                    result.incrementAndGet();
                }
                doneSignal.countDown();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
