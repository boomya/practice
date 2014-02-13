package net.shan.practice.cache;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.SerializationUtils;

public class LRUCache implements Serializable {
    private static final long serialVersionUID = 1992285270564898499L;

//    protected static final Logger      log       = Logger.getLogger(LRUCache.class);

    // 当前访问的Cache
    private Map<Object, CachedObject> oldMap        = new ConcurrentHashMap<Object, CachedObject>();

    // 最近访问的Cache
    private Map<Object, CachedObject> newMap        = new ConcurrentHashMap<Object, CachedObject>();

    // 过期时间，以毫秒为单位 ,默认为一个小时
    private long                      refreshTime   = 60 * 60 * 1000;

    // 最近访问cache允许的容量
    private int                       newCapacity   = 1000;

    // 当前cache允许的容量
    private int                       oldCapacity   = 2000;

//    //写的次数
//    private int                       writeCount    = 0;
//
//    // cache命中的次数
//    private int                       readHitCount  = 0;
//
//    //cache miss 的次数
//    private int                       readMissCount = 0;
    //写的次数
    private AtomicInteger writeCount = new AtomicInteger(0);

    // cache命中的次数
    private AtomicInteger readHitCount = new AtomicInteger(0);

    //cache miss 的次数
    private AtomicInteger readMissCount = new AtomicInteger(0);

    //初始化时间
    private long                      initTime = -1;

    /**
     * 构造函数
     */
    public LRUCache() {
        this(1000,0);
    }

    /**
     * 构造函数
     *
     * @param capacity - 最近访问cache允许的容量
     */
    public LRUCache(int capacity) {
        this(capacity, 0);
    }

    /**
     * 构造函数
     *
     * @param capacity - 最近访问cache允许的容量
     * @param refreshTime - 过去时间
     */
    public LRUCache(int capacity, long refreshTime) {
        initTime = System.currentTimeMillis();

        change(capacity, refreshTime);
    }

    /**
     * 放入一个Cache对象
     *
     * @param key
     * @param value
     * @return
     */
    public Object put(Object key, Object value) {
        writeCount.incrementAndGet();

        // 新的一定是需要放的
        CachedObject co = new CachedObject(value, newMap);
        newMap.put(key, co);

        // 如果到达容量限制
//        if (newMap.size() < newCapacity && oldMap.size() < oldCapacity) {
        if (newMap.size() < newCapacity) {
            oldMap.put(key, co);
        } else {
            Map<Object, CachedObject> temp = new ConcurrentHashMap<Object, CachedObject>();

            oldMap = newMap;
            newMap = temp;

            temp.put(key, co.setParent(newMap));
        }

        return value;
    }

    /**
     * 修改配置
     * @param capacity
     * @param refreshTime
     * @return
     */
    public void change(int capacity, long refreshTime) {
        this.newCapacity = capacity;
        this.oldCapacity = capacity * 2;
        if (refreshTime > 0) this.refreshTime = refreshTime;
    }

    /**
     * 取出一个对象
     *
     * @param key
     * @return
     */
    public Object get(Object key) {
        CachedObject co = oldMap.get(key);

        if (co != null) {
            // 判断是否过期
            if ((System.currentTimeMillis() - co.getCreateTime()) > refreshTime) {
                // 清除
                oldMap.remove(key);

                if (co.getParent() == newMap) {
                    newMap.remove(key);
                }

                readMissCount.incrementAndGet();

                return null;
            }

            // 如果还在旧区,就放入新区
            if (co.getParent() != newMap) {
                newMap.put(key, co.setParent(newMap));
            }

            readHitCount.incrementAndGet();

            return co.getObject();
        } else {
            readMissCount.incrementAndGet();

            return null;
        }
    }

    /**
     * 删除Cache
     * @param key
     */
    public void remove(Object key) {
        if (newMap.remove(key) != null) {
            oldMap.remove(key);
        }
    }

    /**
     * 清除所有数据
     * @return
     */
    public void clear() {
        newMap.clear();
        oldMap.clear();
    }

    /**
     * 返回cache中内容的大小
     */
    public int size() {
        return newMap.size() + oldMap.size();
    }

    public int getWriteCount() {
        return writeCount.intValue();
    }

    public int getReadHitCount() {
        return readHitCount.intValue();
    }

    public int getReadMissCount() {
        return readMissCount.intValue();
    }

    /**
     * 估算cache的大小
     * @param number - 抽样的记录数
     * @return
     */
    public long estimateCacheSize (int number){
        try{
            if(number >= 100){
                number = 100;
            }

            long totalSize = 0;

            int count = 0;
            for(Map.Entry<Object, CachedObject> entry : newMap.entrySet()){
                if(count ++ >= number){
                    break;
                } else {
                    totalSize += SerializationUtils.serialize(entry.getValue()).length;
                }
            }

            return totalSize ;
        } catch (Throwable e){
//            log.error("",e);

            return 0;
        }
    }

    /**
     * 输出关键属性
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Used Time=").append((System.currentTimeMillis() - initTime)).append(',');
        sb.append("newCapacity=").append(newCapacity).append(',');
        sb.append("oldCapacity=").append(oldCapacity).append(',');
        sb.append("newMap.size()=").append(newMap.size()).append(',');
        sb.append("oldMap.size()=").append(oldMap.size()).append(',');
        sb.append("writeCount=").append(writeCount).append(',');
        sb.append("readHitCount=").append(readHitCount).append(',');
        sb.append("readMissCount=").append(readMissCount).append(',');
        sb.append("estimateCacheSize=").append(estimateCacheSize(10));
        return sb.toString();
    }

    /**
     * 打印关键数据
     * @deprecated "instead of Use toString()"
     * @param ps
     */
    public void printInfo(PrintStream ps) {
        ps.println("oldMap.size()" + oldMap.size());
        ps.println("newMap.size()" + newMap.size());
    }

    /**
     * @author bob.panl 被Cache的对象
     */
    private class CachedObject implements Serializable {

        private static final long         serialVersionUID = -7456785409903975940L;

        //被cache的对象
        private Object                    object           = null;

        //创建的时间
        private long                      createTime       = System.currentTimeMillis();

        // 对应的容器
        private Map<Object, CachedObject> parent           = null;

        /**
         * 构造函数
         *
         * @param object
         * @param parent
         */
        public CachedObject(Object object, Map<Object, CachedObject> parent) {
            this.object = object;
            this.parent = parent;
        }

        public Object getObject() {
            return object;
        }

        public Map<Object, CachedObject> getParent() {
            return parent;
        }

        public CachedObject setParent(Map<Object, CachedObject> parent) {
            this.parent = parent;
            return this;
        }

        public long getCreateTime() {
            return createTime;
        }
    }
}

