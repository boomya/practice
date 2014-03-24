package net.shan.practice.exception;

/**
 * Created by sam.js on 14-3-24.
 */
public class App {
    public static void main(String[] args){
        try {
            Service.say("hello");
        } catch (CustomCheckedException e) {
            e.printStackTrace();
        }
        try {
            Service.say("test");
        } catch (CustomCheckedException e) {
            e.printStackTrace();
        }

    }
}
