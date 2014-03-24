package net.shan.practice.exception;

/**
 * Created by sam.js on 14-3-24.
 */
public class CustomCheckedException extends Exception {
    private int code;
    private String msg;

    public CustomCheckedException(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode(){
        return code;
    }
    public String getMsg(){
        return msg;
    }

}
