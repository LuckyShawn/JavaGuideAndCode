package MultithreadDemo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description 线程通信生产者消费者传统版本
 * 题目：一个初始值为0的变量，4个线程对其进行交替操作，一个+1一个-1，来5轮
 * 1.   线程  操作  资源类
 * 2.   判断  干活  通知
 * 3.
 * @Author shawn
 * @create 2019/5/15 0015
 */
public class ProductConsumer_TraditionDemo {
    public static void main(String[] args){
        ShareData shareData = new ShareData();
        new Thread(() -> {
            for (int i = 1; i < 5; i++) {
                try {
                    shareData.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"AA").start();

        new Thread(() -> {
            for (int i = 1; i < 5; i++) {
                try {
                    shareData.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"BB").start();

        new Thread(() -> {
            for (int i = 1; i < 5; i++) {
                try {
                    shareData.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"CC").start();

        new Thread(() -> {
            for (int i = 1; i < 5; i++) {
                try {
                    shareData.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"DD").start();
    }




}


class ShareData {    //资源类
    private int number = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    //加1
    public void increment() throws InterruptedException {
       lock.lock();
       try {
           //1.判断(多线程下必须用while判断，不能用if)
           while(number != 0){
               //若不为0，则等待，不能生产
               condition.await();
           }
           //2.操作
           number++;
           System.out.println(Thread.currentThread().getName()+"\t "+number);
           //3.通知唤醒
           condition.signalAll();
       }catch (Exception e){
           e.printStackTrace();
       }finally {
           lock.unlock();
       }
    }

    //加1
    public void decrement() throws InterruptedException {
        lock.lock();
        try {
            //1.判断(多线程下必须用while判断，不能用if)
            while(number == 0){
                //若不为0，则等待，不能生产
                condition.await();
            }
            //2.操作
            number--;
            System.out.println(Thread.currentThread().getName()+"\t "+number);
            //3.通知唤醒
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}
