package MultithreadDemo;

import java.util.concurrent.*;

/**
 * @Description 线程池 第四种获得多线程的方式
 * @Author shawn
 * @create 2019/5/16 0016
 */
public class MyThreadPoolDemo {
    public static void main(String[] args){
        //查询CPU数量
        System.out.println("CPU核心数："+Runtime.getRuntime().availableProcessors());
        //threadPoolInit();

        //手写线程池(默认拒绝策略，抛异常)
        ExecutorService threadPool = new ThreadPoolExecutor(2,
                5,
                1L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );

        //手写线程池(回退到main线程)
//        ExecutorService threadPool = new ThreadPoolExecutor(2,
//                5,
//                1L,
//                TimeUnit.SECONDS,
//                new LinkedBlockingQueue<>(3),
//                Executors.defaultThreadFactory(),
//                new ThreadPoolExecutor.CallerRunsPolicy()
//        );
        //DiscardOldestPolicy,DiscardPolicy这两种是抛弃任务，一种抛弃等待最久的，一种直接抛弃



        //模拟10个用户办理业务，每个用户就是一个来自外部的请求线程
        try {
            //java.util.concurrent.RejectedExecutionException 超过最大线程数被拒绝异常
            for (int i = 1; i <= 50; i++) {
                try {
                    TimeUnit.MICROSECONDS.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName()+"\t 办理业务！");

                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            threadPool.shutdown();
        }



    }

    //利用Executors创建线程池，但是生产中不能这么用
    private static void threadPoolInit() {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);//一个池5个线程
        ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();//一个池单个线程
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool(); //一池N个处理线程

        //模拟10个用户办理业务，每个用户就是一个来自外部的请求线程
        try {
            for (int i = 1; i <= 10; i++) {
                fixedThreadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName()+"\t 办理业务！");
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            fixedThreadPool.shutdown();
            singleThreadPool.shutdown();
            cachedThreadPool.shutdown();
        }
    }
}
