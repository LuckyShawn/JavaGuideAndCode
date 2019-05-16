package MultithreadDemo;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @Description TODO
 * @Author shawn
 * @create 2019/5/15 0015
 */
public class SemaphoreDemo {

    public static void main(String[] args){
        Semaphore semaphore = new Semaphore(3);//只有三个车位

        //6辆车抢三个车位
        for (int i = 1; i < 6; i++) {
            new Thread(() -> {
                try {
                    //抢占，可复用
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName()+"\t 抢停车位");
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println(Thread.currentThread().getName()+"\t 停车3秒后离开车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    //释放
                    semaphore.release();
                }
            },String.valueOf(i)).start();
        }

    }
}
