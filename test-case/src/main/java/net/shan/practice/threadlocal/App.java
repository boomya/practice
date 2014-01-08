package net.shan.practice.threadlocal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by sam.js on 14-1-8.
 */
public class App {

    Runnable task = new Runnable() {
        @Override
        public void run() {
            for(int i=0; i<2; i++){
                Manager tmp = Manager.getCurrentManager();
                if(tmp == null){
                    System.out.println("getCurrentManager null.");
                    tmp = new Manager();
                    System.out.println(tmp.hashCode());
                    Manager.setCurrentManager(tmp);
                }else{
                    System.out.println("getCurrentManager." + tmp.hashCode());
                }
                try {
                    Thread.sleep(7000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public static void main(String[] args){
        new App().test();


    }
    private void test(){
        ExecutorService services = Executors.newFixedThreadPool(3);
        for(int i=0; i<3; i++){
            services.execute(task);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
