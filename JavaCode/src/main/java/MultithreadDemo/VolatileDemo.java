package MultithreadDemo;

import java.util.concurrent.TimeUnit;

/**
 * @Description 验证volatile的可见性
 *                  1.加入int number = 0；开始没有用volatile修饰，没有可见性，while一直循环，因为主线程不知道number已经修改了
 * @Author shawn
 * @create 2019/5/9 0009
 */
public class VolatileDemo {
    public static void main(String[] args){ //一切方法的运行入口
        MyData myData = new MyData();   //资源类
        //第一个线程
        new Thread(() ->{
            System.out.println(Thread.currentThread().getName()+"\t come in");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myData.addTo60();
            System.out.println(Thread.currentThread().getName()+"\t updated number value:"+myData.number);
        },"AAA").start();

        //第二个线程是主线程！
        while(myData.number == 0){
            //main线程就一直等待循环，直到number不等于0
        }
        System.out.println(Thread.currentThread().getName()+"\t mission over!main get number value:"+ myData.number);
    }

}

class MyData{
    volatile int number = 0;
    public void addTo60(){
        this.number = 60;
    }
}
