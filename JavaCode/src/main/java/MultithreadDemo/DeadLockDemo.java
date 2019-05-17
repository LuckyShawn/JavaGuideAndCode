package MultithreadDemo;

import java.util.concurrent.TimeUnit;

/**
 * @Description 死锁现象：吃着碗里的看着锅里的
 * @Author shawn
 * @create 2019/5/16 0016
 */
public class DeadLockDemo {
    public static void main(String[] args){
        /**
         * linux  ps -ef|grep xxx      ls -l
         * window 下的java运行程序，也有类似于ps查看进程的命令，但是目前只是需要查看java的进程
         *        jsp = java ps         jps -l
         */

        String lockA = "lockA";
        String lockB = "lockB";

        new Thread(new HoldLockThread(lockA,lockB),"AAA").start();
        new Thread(new HoldLockThread(lockB,lockA),"BBB").start();
    }
}

class HoldLockThread implements Runnable{
    private String lockA;
    private String lockB;

    public HoldLockThread(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA){
            System.out.println(Thread.currentThread().getName()+"\t 自己持有："+lockA+"试图获取："+lockB);
            //暂停让另外的线程执行，但不释放锁
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lockB){
                System.out.println(Thread.currentThread().getName()+"\t 自己持有："+lockB+"试图获取："+lockA);
            }
        }
    }
}