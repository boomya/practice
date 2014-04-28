package net.shan.practice.sync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by jiangshan on 14-4-28.
 */
public class LockPassing {
    public static void main(String[] args){
        final User u1 = new User(1);
        final User u2 = new User(2);

        ExecutorService services = Executors.newCachedThreadPool();
        services.execute(new Runnable() {
            @Override
            public void run() {
                u1.testSync(100);
            }
        });

        services.execute(new Runnable() {
            @Override
            public void run() {
                u1.showCustomId(200);
            }
        });
    }
}
