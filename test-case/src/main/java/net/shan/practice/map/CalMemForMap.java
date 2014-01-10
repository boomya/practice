package net.shan.practice.map;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by sam.js on 14-1-7.
 */
public class CalMemForMap {
    public static void main( String[] args ){

        new CalMemForMap().test();

    }
    private final int SMALL = 10;
    private final int BIG = 102000;
    private final int COUNT = 5;

    private final Random r = new Random();

    private void test(){
        ConcurrentHashMap<Object, CacheObject> smallCaches = new ConcurrentHashMap<Object, CacheObject>();
//        ConcurrentMap<Object, CacheObject> bigCaches = new ConcurrentHashMap<Object, CacheObject>();
        System.out.println("start===============65");
        showMem();
        for(int i=0; i<COUNT; i++){
            System.out.println(i + " ------------------------------------");
            while(true){
                CacheObject co = new CacheObject(r.nextInt(100000), "A");
                if(smallCaches.size() < SMALL){
                    smallCaches.put(co.key, co);
                    System.out.println("for:" + smallCaches.hashCode());
                }else{
                    System.out.println("1.1===============");
                    showMem();
                    ConcurrentMap<Object, CacheObject> temp = new ConcurrentHashMap<Object, CacheObject>();
                    System.out.println(smallCaches.hashCode());
//                    smallCaches = temp;
                    smallCaches.put(co.key, co);
                    System.out.println(smallCaches.hashCode() + " " + temp.hashCode());
//                    System.out.println("2===============");
                    showMem();
                    System.gc();
//                    System.out.println("3===============");
                    showMem();
                    break;
                }
            }
        }

    }

    private void showMem(){
        System.out.println("total " + Runtime.getRuntime().totalMemory()/1024/1024 + ", free " + Runtime.getRuntime().freeMemory()/1024/1024);
    }

//    class CacheObject {
//        private Object key = null;
//        private Object value = null;
//        private ConcurrentMap<Object, CacheObject> parent = null;
//
//        public CacheObject(Object key, Object value, ConcurrentMap<Object, CacheObject> parent){
//            this.key = key;
//            this.value = value;
//            this.parent = parent;
//        }
//
//        public CacheObject setParent(ConcurrentMap<Object, CacheObject> parent){
//            this.parent = parent;
//            return this;
//        }
//    }

    class CacheObject {
        private Object key = null;
        private Object value = null;

        public CacheObject(Object key, Object value){
            this.key = key;
            this.value = value;
        }
    }
}
