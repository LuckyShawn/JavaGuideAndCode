package MultithreadDemo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description
 * 队列版生产者消费者
 * volatile/CAS/atomicInteger/BlockingQueue/线程交互/原子引用
 *
 * @Author shawn
 * @create 2019/5/15 0015
 */
public class ProductConsumer_BlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        //传参传接口
        MyResource myResource = new MyResource(new ArrayBlockingQueue<>(10));

        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName()+"\t 生产线程启动");
                myResource.prod();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"prod").start();

        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName()+"\t 消费线程启动");
                System.out.println();
                myResource.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"consume").start();

        TimeUnit.SECONDS.sleep(5);
        System.out.println();
        System.out.println("5秒钟到了，main线程叫停！");
        myResource.stop();
    }
}

class MyResource{

    private volatile boolean  FLAG = true;  //标志位。默认开启，进行生产+消费
    private AtomicInteger atomicInteger = new AtomicInteger();

    BlockingQueue<String> blockingQueue = null;
    public MyResource( BlockingQueue<String> blockingQueue){
        this.blockingQueue = blockingQueue;
        //此处最好记录日志，好排查
        System.out.println("传入队列的类型："+blockingQueue.getClass().getName());
    }

    //生产
    public void prod() throws InterruptedException {
        String data = null;
        boolean returnValue;
        while (FLAG){
            data = atomicInteger.incrementAndGet()+"";
            returnValue = blockingQueue.offer(data,2L,TimeUnit.SECONDS);
            if(returnValue){
                System.out.println(Thread.currentThread().getName()+"\t 插入队列"+data+"成功");
            }else{
                System.out.println(Thread.currentThread().getName()+"\t 插入队列"+data+"失败");
            }
            //为了好看，一秒钟生产一个
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName()+"\t 大老板叫停 停止生产 此时FLAG=false");
    }

    //消费
    public void consume() throws InterruptedException {
        String result = null;
        while (FLAG){
            result = blockingQueue.poll(2L,TimeUnit.SECONDS);
            if("".equalsIgnoreCase(result) || null == result){
                FLAG = false;
                System.out.println(Thread.currentThread().getName()+"\t 超过2秒钟没有取到蛋糕，消费退出！");
                System.out.println();
                return;
            }
            System.out.println(Thread.currentThread().getName()+"\t 消费队列"+ result + "成功");
        }

    }
    //停止！
    public void stop(){
        this.FLAG = false;
    }
}
