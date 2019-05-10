package MultithreadDemo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description 一、验证volatile的可见性
 *                  1.加入int number = 0；开始没有用volatile修饰，没有可见性，while一直循环，因为主线程不知道number已经修改了
 *                  2.增加volatile后， 修改number值后，主线程马上知道number=60，跳出循环，解决可见性问题
 *               二、验证volatile不保证原子性
 *                  1.原子性：不可分割，完整性，即：
 *                  *:number++在多线程环境下是不安全的，将其转换成汇编语言，
 *                  会发现number++执行了三步操作，从主内存读取值，值+1，写回主内存
 *                  why值小于20000的原因：t1 t2 t3 读取了主内存的值，t1修改完值写入主内存后，
 *                  挂起的t2,t3还没来得及得到t1的通知，就执行++操作并写入主内存，number只加了一次，造成写覆盖
 *                三、如何保证原子性？
 *                   *.加synchronized
 *                   *.使用juc下的AtomicInteger避免写覆盖
 *
 *
 * @Author shawn
 * @create 2019/5/9 0009
 */
public class VolatileDemo {
    public static void main(String[] args){
        seeOkByVolatile();
        NotAtomic();
    }

    private static void NotAtomic() {
        MyData myData = new MyData();
        //20个线程，加1000次，期望值应该是20000，但是结果小于20000
        for (int i = 1; i <= 20; i++) {
            new Thread(() -> {
                for (int j = 1; j <= 1000 ; j++) {
                    myData.addPlusPuls();
                    myData.addAtomic();
                }
            },String.valueOf(i)).start();
        }
        //需要等待20个线程全部计算完成，用main线程取最终结果
        while (Thread.activeCount() > 2){
            Thread.yield();
        }
        System.out.println(Thread.currentThread().getName()+"\t finally number value: "+myData.number);
        System.out.println(Thread.currentThread().getName()+"\t finally number value: "+myData.atomicInteger);
    }


    private  static void seeOkByVolatile() {
        //一切方法的运行入口
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
    //此时的number是有volatile修饰的，不保证原子性
    public void addPlusPuls(){
        number++;
    }

    //juc下的原子类
    AtomicInteger atomicInteger = new AtomicInteger();//默认0
    public void addAtomic(){
        atomicInteger.getAndIncrement();
    }
}
