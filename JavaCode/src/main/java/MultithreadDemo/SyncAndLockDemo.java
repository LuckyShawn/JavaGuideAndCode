package MultithreadDemo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description synchronized和lock区别
 * 题目：多线程之间按顺序调用，实现A->B->C三个线程启动：
 *                  AA打印5次，BB打印10次，CC打印15次
 *                  AA打印5次，BB打印10次，CC打印15次
 *                  AA打印5次，BB打印10次，CC打印15次
 *                  ......
 *                  10次
 * @Author shawn
 * @create 2019/5/15 0015
 */
public class SyncAndLockDemo {

    public static void main(String[] args){
        ShareResource shareResource = new ShareResource();
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                shareResource.print5();
            }
        },"AA").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                shareResource.print10();
            }
        },"BB").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                shareResource.print15();
            }
        },"CC").start();

    }
}

class ShareResource{    //资源类
    private int number = 1; //标记
    private Lock lock = new ReentrantLock();
    //一个锁有三把备用钥匙
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void print5(){
        lock.lock();
        try {
            while (number != 1){
                c1.await();
            }
            for (int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName()+"\t "+i);
            }
            number = 2;
            c2.signal();   //精确唤醒c2
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print10(){
        lock.lock();
        try {
            while (number != 2){
                c2.await();
            }
            for (int i = 1; i <= 10; i++) {
                System.out.println(Thread.currentThread().getName()+"\t "+i);
            }
            number = 3;
            c3.signal();   //精确唤醒c3
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print15(){
        lock.lock();
        try {
            while (number != 3){
                c3.await();
            }
            for (int i = 1; i <= 15; i++) {
                System.out.println(Thread.currentThread().getName()+"\t "+i);
            }
            number = 1;
            c1.signal();   //精确唤醒c1
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


}