package net.shan.practice.sync;

/**
 * Created by jiangshan on 14-4-28.
 */
public class User {
    private int id = 0;
    public User(int id){
        this.id = id;
    }

    public void showCustomId(int customId){
        synchronized (this){
            System.out.println("showCustomId-->class id:" + id + " customId:" + customId);
        }
    }

    public void testSync(int syncId){
        synchronized (this){
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("testSync-->class id:" + id + " syncId:" + syncId);

            showCustomId(syncId);
        }
    }
}
