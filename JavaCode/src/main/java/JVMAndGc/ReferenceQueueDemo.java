package JVMAndGc;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * @Description TODO
 * @Author shawn
 * @create 2019/5/17 0017
 */
public class ReferenceQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        Object obj1 = new Object();
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue();
        WeakReference<Object> weakReference = new WeakReference<>(obj1,referenceQueue);
        System.out.println(obj1);
        System.out.println(weakReference.get());
        System.out.println(referenceQueue.poll());

        System.out.println("==============");
        obj1 = null;
        System.gc();
        Thread.sleep(500);
        System.out.println(obj1);
        System.out.println(weakReference.get());
        System.out.println(referenceQueue.poll());  //取到值，说明GC回收前会将弱引用放入引用队列
    }
}
