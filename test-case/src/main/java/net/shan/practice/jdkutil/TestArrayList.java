package net.shan.practice.jdkutil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by sam.js on 14-1-22.
 */
public class TestArrayList {
    public static void main(String[] args){
        new TestArrayList().test1();
    }

    //找到ConcurrentModificationException的原因
    private void test1(){
        ArrayList<String> list = new ArrayList<String>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");

//        for(String e:list){
//            System.out.println(e);
//            list.remove(e);
//            System.out.println(list.size());
//        }
        Iterator<String> it = list.iterator();
        while(it.hasNext()){
            String s = it.next();
            System.out.println(s);
            it.remove();
//            list.remove(s);
//            list.add(s);

        }
//        ListIterator<String> it = list.listIterator();
//        while(it.hasPrevious()){
//            System.out.println(it.previous());
//        }
        System.out.println(list.size());
    }
}
