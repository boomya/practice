package net.shan.practice.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sam.js on 14-2-20.
 */
public class LRUCacheIsolationTest {
    public static void main(String[] args){
        new LRUCacheIsolationTest().test();
    }

    final static LRUCache cache = new LRUCache(1, 2000);
    private void test(){
        fill(50);
        System.out.println(cache.get("game_1"));
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

    final static void fill(int score){
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(1, score);
        map.put(2, score);
        map.put(3, score);
        cache.put("game_1", map);
    }
}
