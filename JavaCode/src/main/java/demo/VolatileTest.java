package demo;

/**
 * @Description
 *          一、volatile关键字：当多个线程进行操作共享数据时，可以保证内存中数据的可见性
 *                              相较于synchronized是一种较为轻量级的同步策略
 *                              注意：1.不具备“互斥性”
 *                                    2.不保证原子性
 * @Author shawn
 * @create 2019/5/22 0022
 */
public class VolatileTest {
    public static void main(String[] args) throws InterruptedException {
        MyThread myThread = new MyThread();
        new Thread(myThread).start();

        //TimeUnit.SECONDS.sleep(1);
        while (true){
            if(myThread.flag){
                System.out.println("==================");
                break;
            }
        }

    }
}

class MyThread implements Runnable{

    boolean  flag = false;

    @Override
    public void run() {
        flag = true;
        System.out.println("Flag:"+flag);

    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
