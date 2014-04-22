package net.shan.practice.cache;

/**
 * Created by jiangshan on 14-4-22.
 */
public class SizeOfObject extends SizeOf {

    @Override
    protected Object newInstance() {
        return new Object();
    }

    public static void main(String[] args) throws Exception {
        SizeOf sizeOf = new SizeOfObject();
        System.out.println("所占内存：" + sizeOf.size() + "字节");
    }
}
