package MultithreadDemo;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Description 集合类不安全问题
 * @Author shawn
 * @create 2019/5/14 0014
 */
public class ContainerNotSafe {
    public static void main(String[] args){
        //listNotSafe();
        //setNotSafe();
        //mapNotSafe();
    }

    private static void mapNotSafe() {
        //java.util.ConcurrentModificationException  并发修改异常
        //Map<String,String> map = new HashMap<>();
        //解决
        //Map<String,String> map = Collections.synchronizedMap(new HashMap<>());
        Map<String,String> map = new ConcurrentHashMap<>();
        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(),UUID.randomUUID().toString().substring(0,8));
                System.out.println(map);
            }).start();
        }
    }

    //验证HashSet不安全和解决办法
    private static void setNotSafe() {
        //java.util.ConcurrentModificationException  并发修改异常
        //Set<String> set = new HashSet<>();
        //解决
        //Set<String> set = Collections.synchronizedSet(new HashSet<>());
        Set<String> set = new CopyOnWriteArraySet<>();//实际是CopyOnWriteArrayList

        for (int i = 0; i <= 100; i++) {
            new Thread(()->{
                set.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(set);
            },String.valueOf(i)).start();
        }
    }

    //验证ArrayList不安全和解决办法
    private static void listNotSafe() {
        //java.util.ConcurrentModificationException  并发修改异常
        //List<String> list = new ArrayList<>();
        //以下三种解决方案
        //List<String> list = new Vector<>();
        //List<String> list = Collections.synchronizedList(new ArrayList<>());

        List<String> list = new CopyOnWriteArrayList<>();

        for (int i = 0; i <= 100; i++) {
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            },String.valueOf(i)).start();
        }

        /**
         * 1.故障现象
         * java.util.ConcurrentModificationException
         *
         * 2.导致原因
         *  并发争抢修改导致，参考签名情况
         *  一个人正在写入，另一个人过来争抢，导致数据不一致异常，抛出：并发修改异常
         *
         * 3.解决方法
         *  3.1 List<String> list = new Vector<>();
         *  3.2 List<String> list = Collections.synchronizedList(new ArrayList<>());
         *  3.3 *** List<String> list = new CopyOnWriteArrayList<>(); 写时复制
         *
         *
         * 4.优化建议（同样的错误不出现第二次）
         */}
}

/**
 * 写时复制：
 *      CopyOnWrite容器即写时复制日本歌曲，往一个容器添加元素的时候，不直接往容器Object[]添加，
 *      而是先讲当前容器Object[]进行Copy，复制到一个新的容器Object[] newElements,
 *      然后向新的容器Object[] newElements里面添加元素，添加完成后，再将原容器的引用指向新的容器（setArray(newElements)）
 *      这样做的好处是可以对CopyOnWrite容器进行并发的读，而不需要加锁，因为当前容器不会添加任何元素，所以CopyOnWrite容器也是一种
 *      读写分离的思想，读和写不同的容器
 *
 *    写时复制源码：
 *    public boolean add(E e) {
 *         final ReentrantLock lock = this.lock;
 *         lock.lock();
 *         try {
 *             Object[] elements = getArray();
 *             int len = elements.length;
 *             Object[] newElements = Arrays.copyOf(elements, len + 1);
 *             newElements[len] = e;
 *             setArray(newElements);
 *             return true;
 *         } finally {
 *             lock.unlock();
 *         }
 *     }
 */

/**
 * HashSet底层是HashMap  初始容量16，负载因子0.75
 * HashSet只需要HashMap的key，value是一个叫PRESENT的常量Object
 * 
 */