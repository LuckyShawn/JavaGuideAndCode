package MultithreadDemo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * 1.队列
 *
 * 2.阻塞队列：
 *      2.1 阻塞队列也有好处
 *
 *      2.2不得不阻塞的情况，如何管理？//火锅店的 候餐区
 *
 * @Author shawn
 * @create 2019/5/15 0015
 */
public class BlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        //throwExceptionMethod();
        //teshuMethod();
        //blockingMethod();
        //timeOutMethod();
        //synchronousQueue();
    }

    //同步队列
    private static void synchronousQueue() {
        BlockingQueue blockingQueue = new SynchronousQueue();

        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName()+"\t put 1");
                blockingQueue.put(1);
                System.out.println(Thread.currentThread().getName()+"\t put 2");
                blockingQueue.put(2);
                System.out.println(Thread.currentThread().getName()+"\t put 3");
                blockingQueue.put(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"AAA").start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName()+"\t take "+blockingQueue.take());
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName()+"\t take "+blockingQueue.take());
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName()+"\t take "+blockingQueue.take());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"BBB").start();
    }

    //超时方法
    private static void timeOutMethod() throws InterruptedException {
        BlockingQueue blockingQueue = new ArrayBlockingQueue(3);
        System.out.println(blockingQueue.offer("a",2L,TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("a",2L,TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("a",2L,TimeUnit.SECONDS));
        //阻塞两秒后解除阻塞插入失败返回false
        System.out.println(blockingQueue.offer("a",2L,TimeUnit.SECONDS));

        System.out.println(blockingQueue.poll(2,TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(2,TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(2,TimeUnit.SECONDS));
        //阻塞两秒后解除阻塞取值失败返回null
        System.out.println(blockingQueue.poll(2,TimeUnit.SECONDS));
    }

    //阻塞方法
    private static void blockingMethod() throws InterruptedException {
        BlockingQueue blockingQueue = new ArrayBlockingQueue(3);
        blockingQueue.put("a");
        blockingQueue.put("a");
        blockingQueue.put("a");
        System.out.println("=============");
        //blockingQueue.put("a");   //多存一个put会被阻塞
        blockingQueue.take();
        blockingQueue.take();
        blockingQueue.take();
        //blockingQueue.take();   //多取一个take会被阻塞
    }

    //特殊组
    private static void teshuMethod() {
        BlockingQueue blockingQueue = new ArrayBlockingQueue(3);
        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));
        System.out.println(blockingQueue.offer("x"));   //超出容量，返回false

        System.out.println(blockingQueue.peek()); //检查队列是否空，队首元素是谁

        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());   //队列只有3个元素，返回null
    }

    //抛出异常组
    private static void throwExceptionMethod() {
        //List list = new ArrayList();
        BlockingQueue blockingQueue = new ArrayBlockingQueue(3);
        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("a"));
        //System.out.println(blockingQueue.add("x")); //超过3个了，抛异常java.lang.IllegalStateException: Queue full

        System.out.println(blockingQueue.element());//检查队列是否空，队首元素是谁

        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        //System.out.println(blockingQueue.remove()); //移除超过了3个，抛异常java.util.NoSuchElementException
    }
}
