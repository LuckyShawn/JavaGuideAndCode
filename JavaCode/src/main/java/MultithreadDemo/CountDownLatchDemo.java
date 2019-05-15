package MultithreadDemo;

import java.util.concurrent.CountDownLatch;

/**
 * @Description CountDownLatch
 * @Author shawn
 * @create 2019/5/15 0015
 */
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        //CountDownLatch1();
        //CountDownLatch2();
    }
    //秦国一统六国
    private static void CountDownLatch2() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName()+"\t 国 灭亡！");
                countDownLatch.countDown();
            },CountryEnum.forEachCountryEnum(i).getReturnMsg()).start();
        }
        countDownLatch.await();
        //秦国最后统一
        System.out.println(Thread.currentThread().getName()+"\t 秦国一统六国");

        System.out.println(CountryEnum.ONE);//表
        System.out.println(CountryEnum.ONE.getReturnCode());//id
        System.out.println(CountryEnum.ONE.getReturnMsg());//字段
    }

    //下班demo
    private static void CountDownLatch1() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName()+"\t 下班了，离开公司！");
                countDownLatch.countDown();
                },String.valueOf(i)).start();
        }
        countDownLatch.await();
        //管理员必须是最后一个走的
        System.out.println(Thread.currentThread().getName()+"\t 管理员最后关门！");
    }
}
