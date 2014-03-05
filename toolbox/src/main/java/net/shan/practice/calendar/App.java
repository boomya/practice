package net.shan.practice.calendar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sam.js on 14-2-22.
 */
public class App {
    public static void main(String[] args){
        new App().test1();

        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        System.out.println((Integer)map.get("a").intValue());

    }

    private void test1(){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(format.format(new Date()));
    }
}
