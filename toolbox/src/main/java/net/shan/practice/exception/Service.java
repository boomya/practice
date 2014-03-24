package net.shan.practice.exception;

/**
 * Created by sam.js on 14-3-24.
 */
public class Service {

    public static String say(String name) throws CustomCheckedException{
        if("test".equals(name)){
            throw new CustomCheckedException(100, "test_msg");
        }
        System.out.println("say:" + name);
        return "say:" + name;
    }
}
