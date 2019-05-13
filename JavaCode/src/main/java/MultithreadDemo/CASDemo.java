package MultithreadDemo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description CAS是什么？compareAndSwap， compareAndSet --> 比较并交换
 * @Author shawn
 * @create 2019/5/10 0010
 */
public class CASDemo {

    public static void main(String[] args){
        //主内存值是5
        AtomicInteger atomicInteger = new AtomicInteger(5);
        //业务逻辑......


        //比较期望值和真实值
        //线程t1读取并拷贝主内存中的atomicInteger值是5，并记录快照，然后处理后atomicInteger值为2000，再写入主内存前
        //快照值5与主内存的值5进行比较，5 == 5为true ，值2000写入主内存成功  输出true，当前值2000
        System.out.println(atomicInteger.compareAndSet(5,2000) + "当前值是："+atomicInteger.get());

        //线程t1读取并拷贝主内存中的atomicInteger值是2000，并记录快照，然后处理后atomicInteger值为3000，再写入主内存前
        //快照值5与主内存的值2000进行比较，5 == 2000为false ，值3000写入主内存失败  输出false，当前值2000
        System.out.println(atomicInteger.compareAndSet(5,3000) + "当前值是："+atomicInteger.get());

    }
}
