package net.shan.practice.random;

import net.shan.practice.json.Game;
import net.shan.practice.json.GameRuleDO;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by sam.js on 14-2-19.
 */
public class App {
    public static void main(String[] args){
        fill();
        new App().test1();
    }
    final static Random r = new Random(System.currentTimeMillis());
    final static Map<String, Integer> source = new HashMap<String, Integer>();
    final static Map<String, AtomicLong> record = new HashMap<String, AtomicLong>();
    public static void fill(){
//        source.put("A", 200);
//        source.put("B", 200);
//        source.put("C", 200);
//        source.put("D", 200);
//        source.put("E", 200);

//        source.put("A", 300);
//        source.put("B", 300);
//        source.put("C", 300);
//        source.put("D", 300);
//        source.put("E", 300);

        source.put("A", 40);
        source.put("B", 20);
        source.put("C", 10);
        source.put("D", 10);
        source.put("E", 10);

        record.put("A", new AtomicLong(0));
        record.put("B", new AtomicLong(0));
        record.put("C", new AtomicLong(0));
        record.put("D", new AtomicLong(0));
        record.put("E", new AtomicLong(0));
    }
    public void test1(){
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch stopSignal = new CountDownLatch(10);
        ExecutorService services = Executors.newFixedThreadPool(10);
        for(int i=0; i<10; i++){
            services.submit(new Task(startSignal, stopSignal));
        }
        startSignal.countDown();
        try {
            stopSignal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Set<Map.Entry<String,AtomicLong>> entrySet = record.entrySet();
        for(Map.Entry<String,AtomicLong> entry:entrySet){
            System.out.println(entry.getKey() + " " + entry.getValue().get());
        }
        services.shutdown();
    }

    class Task implements Runnable{
        private CountDownLatch startSignal = null;
        private CountDownLatch stopSignal = null;
        Task(CountDownLatch startSignal, CountDownLatch stopSignal){
            this.startSignal = startSignal;
            this.stopSignal = stopSignal;
        }

        @Override
        public void run() {
            int desc = 0;
            Collection<Integer> values = source.values();
            for(Integer value:values){
                desc += value;
            }
            try {
                startSignal.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for(int i=0; i<100000; i++){
                int result = r.nextInt(desc);
                Set<Map.Entry<String,Integer>> entrySet = source.entrySet();
                for(Map.Entry<String,Integer> entry:entrySet){
                    result -= entry.getValue();
                    if(result <= 0){
                        record.get(entry.getKey()).incrementAndGet();
                        break;
                    }
                }
            }

            stopSignal.countDown();
        }
    }
}
