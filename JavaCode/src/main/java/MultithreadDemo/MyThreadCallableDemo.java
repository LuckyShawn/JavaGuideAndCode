package MultithreadDemo;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @Description Callable接口的使用  适配器模式
 * @Author shawn
 * @create 2019/5/16 0016
 */
public class MyThreadCallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //两个线程  一个main线程  一个AAFutureTask
        FutureTask<Integer> futureTask = new FutureTask(new MyThread());
        //FutureTask<Integer> futureTask1 = new FutureTask(new MyThread()); //两次计算
        Thread thread = new Thread(futureTask,"AA");
       // Thread thread1 = new Thread(futureTask,"BB"); //复用
        thread.start();
        //int result02 = futureTask.get();  //放在这里会堵塞main线程，直到AA线程计算完成
        System.out.println(Thread.currentThread().getName()+"\t ******main");
        int result01 = 100;

        //可以利用自旋，当future计算完成在获取
//        while(!futureTask.isDone()){
//
//        }

        //建议放在最后，因为要求获得Callable线程的计算结果，如果没有计算完成
        //就要去强求，那么会导致堵塞，直到计算完成，放在最后是给多一点时间（AA线程）给计算时间长的。
        int result02 = futureTask.get();


        System.out.println("******result:"+ (result01 + result02));
    }
}

class MyThread implements Callable{


    @Override
    public Integer call() throws Exception {
        System.out.println("*****Come in Callable call");
        TimeUnit.SECONDS.sleep(3);
        return 1024;
    }
}
