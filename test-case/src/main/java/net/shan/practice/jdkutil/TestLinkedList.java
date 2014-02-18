package net.shan.practice.jdkutil;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;

/**
 * Created by sam.js on 14-1-22.
 */
public class TestLinkedList {
    public static void main(String[] args){

    }
    public final LinkedList<String> list = new LinkedList<String>();
    private void singleThreadTest(){

    }

    class Task implements Runnable{
        private final String id;
        private final int count;
        private final CountDownLatch stopSignal;
        Task(String id, int count, CountDownLatch stopSignal){
            this.id = id;
            this.count = count;
            this.stopSignal = stopSignal;
        }
        @Override
        public void run() {
            for(int i=0; i<count; i++){
                list.add(id + "_" + i);
            }
            stopSignal.countDown();
        }
    }

}

