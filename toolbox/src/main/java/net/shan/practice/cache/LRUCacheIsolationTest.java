package net.shan.practice.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sam.js on 14-2-20.
 */
public class LRUCacheIsolationTest {
    public static void main(String[] args){
        new LRUCacheIsolationTest().test2();
    }

    final static LRUCache cache = new LRUCache(1, 2000);
    private void test(){
        fill(50);
        Map map = (Map) cache.get("game_1");
        System.out.println(map);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        fill(100);
        System.out.println(map);
        map = (Map) cache.get("game_1");
        System.out.println(map);

    }

    private void test2(){
        fill2();
        System.out.println(cache.get("game_1"));
        Map map = (Map) cache.get("game_1");
        System.out.println(map);
        int[] b = (int[]) map.get("B");
        System.out.println("b:" + b);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        fill3();
        System.out.println(map);
        System.out.println("b:" + b);
        map = (Map) cache.get("game_1");
        System.out.println(map);

    }

    final static void fill2(){
        Map<String, int[]> map = new HashMap<String, int[]>();
        map.put("A", new int[]{1, 10});
        map.put("B", new int[]{2, 20});
        map.put("C", new int[]{3, 30});
        map.put("D", new int[]{4, 40});
        cache.put("game_1", map);
    }

    final static void fill3(){
        Map<String, int[]> map = new HashMap<String, int[]>();
        map.put("A", new int[]{1, 100});
        map.put("B", new int[]{2, 200});
        map.put("C", new int[]{3, 300});
        map.put("D", new int[]{4, 400});
        cache.put("game_1", map);
    }

    final static void fill(int score){
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(1, score);
        map.put(2, score);
        map.put(3, score);
        cache.put("game_1", map);
    }
}
