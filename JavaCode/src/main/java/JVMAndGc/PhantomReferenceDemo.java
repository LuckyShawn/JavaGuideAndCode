package JVMAndGc;

import javax.annotation.processing.SupportedSourceVersion;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * @Description TODO
 * @Author shawn
 * @create 2019/5/17 0017
 */
public class PhantomReferenceDemo {
    public static void main(String[] args) throws InterruptedException {
        Object obj1 = new Object();
        ReferenceQueue referenceQueue = new ReferenceQueue();
        PhantomReference phantomReference = new PhantomReference(obj1,referenceQueue);
        System.out.println(obj1);
        System.out.println(phantomReference.get());
        System.out.println(referenceQueue.poll());
        System.out.println("==================GC后");
        obj1 = null;
        System.gc();
        Thread.sleep(500);
        System.out.println(obj1);
        System.out.println(phantomReference.get());
        System.out.println(referenceQueue.poll());  //做后置通知

    }
}
