package JVMAndGc;

import java.lang.ref.SoftReference;

/**
 * @Description 软引用
 * @Author shawn
 * @create 2019/5/17 0017
 */
public class SoftReferenceDemo {
    public static void main(String[] args){
        //memoryEnough();
        //memoryNotEnough();
    }

    //内存够用的情况
    public static void memoryEnough(){
        Object obj1 = new Object();
        SoftReference softReference = new SoftReference(obj1);
        obj1 = null;
        System.gc();
        System.out.println(obj1);   //null
        System.out.println(softReference.get());    //内存够用，不会回收
    }

    /**
     * 内存不够用的情况
     * JVM配置：故意产生大对象并配置小的内存，让内存不足导致OOM，看软引用的回收情况
     * -Xms5m -Xmx5m -XX:+PrintGCDetails
     */
    public static void memoryNotEnough(){
        Object obj1 = new Object();
        SoftReference softReference = new SoftReference(obj1);
        obj1 = null;
        System.gc();
        System.out.println(obj1);
        System.out.println(softReference.get());
        try {
            Byte[] bytes = new Byte[1024 * 6 *1024];
        }catch (Throwable e){
            e.printStackTrace();
        }finally {
            System.gc();
            System.out.println(obj1);   //null
            System.out.println(softReference.get());    //null 内存不够用，需要回收
        }
    }
}
