package MultithreadDemo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Description  独占锁（写锁），共享锁（读锁），互斥锁
 *  多个线程同时读一个资源类没有任何问题，所以为了满足并发量，读取共享资源应该可以同时进行。
 *  但是如果一个线程想写共享资源，就不应该再有其他线程可以对该资源进行读或写。
 *  总结： 读--读共存		读--写不共存		写--写不共存
 *
 *  写操作：原子+独占  ,真个过程必须是一个完整的统一体，中间不许被分割，打断
 * @Author shawn
 * @create 2019/5/14 0014
 */
public class ReadWriterLockDemo {

    public static void main(String[] args) throws InterruptedException {
        MyCache myCache = new MyCache();
        //写
        for (int i = 1; i <= 5; i++) {
            final int tempInt = i;
            new Thread(() -> {
                myCache.put(tempInt+"",tempInt+"");
            },String.valueOf(i)).start();
        }

        //读
        for (int i = 1; i <= 5; i++) {
            final int tempInt = i;
            new Thread(() -> {
                myCache.get(tempInt+"");
            },String.valueOf(i)).start();
        }
    }
}

class MyCache{  //资源类
    private volatile Map<String,Object> map = new HashMap();

    private ReentrantReadWriteLock rrwLock = new ReentrantReadWriteLock();
    public void put(String key,Object value){
        rrwLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t 正在写入："+key);
            TimeUnit.MILLISECONDS.sleep(300);
            map.put(key,value);
            System.out.println(Thread.currentThread().getName()+"\t 写入完成");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            rrwLock.writeLock().unlock();
        }

    }


    public void get(String key){
        rrwLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t 正在读取");
            TimeUnit.MILLISECONDS.sleep(300);
            Object result = map.get(key);
            System.out.println(Thread.currentThread().getName()+"\t 读取完成："+result);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            rrwLock.readLock().unlock();
        }
    }
}
