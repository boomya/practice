package net.shan.practice.threadlocal;

/**
 * Created by sam.js on 14-1-8.
 */
public class Manager {
    private static final ThreadLocal<Manager> holder = new ThreadLocal<Manager>();
//    private static Manager holder = new Manager();

    private String userId = null;

    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getUserId(){
        return userId;
    }

    public static final Manager getCurrentManager(){
        return holder.get();
    }

    public static final void setCurrentManager(Manager manager){
        holder.set(manager);
    }
//    public static final Manager getCurrentManager(){
//        return holder;
//    }
//
//    public static final void setCurrentManager(Manager manager){
//        holder = manager;
//    }
}
