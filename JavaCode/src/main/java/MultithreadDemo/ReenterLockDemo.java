package MultithreadDemo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description 可重入锁demo
 * 值的是同一线程外层函数获得锁之后，内层递归函数任然能获取该锁的代码，
 * 在同一个线程在外城方法获取锁的时候，在进入内层方法会自动获取锁
 *
 * 即：线程可以进入任何一个它已经拥有的锁所同步着的代码块（主人进了家门，进厕所 卧室 厨房 不用在开门）
 *
 * 证明synchronized是一个可重入锁
 * T1	 invoked sendSMS()  T1线程在外层方法获取锁的时候
 * T1	 invoked sendEmail() T1在进入内层方法时会自动获取锁
 * T2	 invoked sendSMS()
 * T2	 invoked sendEmail()
 *
 * 证明ReentrantLock是一个可重入锁 ReentrantLock成对出现可以有多对
 * T3	 invoked get()
 * T3	 invoked set()
 * T4	 invoked get()
 * T4	 invoked set()
 *
 *
 * @Author shawn
 * @create 2019/5/14 0014
 */
public class ReenterLockDemo {
    public static void main(String[] args){
        Phone phone = new Phone();

        new Thread(() -> {
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ,"T1").start();

        new Thread(() -> {
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ,"T2").start();

        //暂停一会儿
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println();
        System.out.println();
        System.out.println();
        Thread t3 = new Thread(phone,"T3");
        Thread t4 = new Thread(phone,"T4");
        t3.start();
        t4.start();
    }

}

class Phone implements Runnable {
    public synchronized void sendSMS() throws Exception{
        System.out.println(Thread.currentThread().getName()+ "\t invoked sendSMS()" );
        sendEmail();
    }

    public synchronized void sendEmail() throws Exception{
        System.out.println(Thread.currentThread().getName()+ "\t invoked sendEmail()" );
    }


     Lock lock = new ReentrantLock();
    @Override
    public void run() {
        get();
    }

    public void get(){
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t invoked get()");
            set();
        }finally {
            lock.unlock();
        }
    }

    public void set(){
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t invoked set()");
        }finally {
            lock.unlock();
        }
    }
}

