package MultithreadDemo;

/**
 * @Description 带volatile的双重检查锁单例模式
 * @Author shawn
 * @create 2019/5/10 0010
 */
public class DCLSingletonDemo {
    /**
     * 若不增加volatile，在创建对象的时候，由于1.分配对象内存空间，2.初始化对象，3.设置instance指向分配的地址内存instance!=null
     * 这三个步骤是可以进行指令重排序的，重排序后顺序1 3 2，第三步先执行，并未初始化对象，所以导致不可预知的错误
     */
    //为了避免多线程下指令重排序，取到的dclSingletonDemo是空的情况
    protected volatile static DCLSingletonDemo dclSingletonDemo = null;

    private DCLSingletonDemo(){
        System.out.println(Thread.currentThread().getName()+"\t 我是构造方法DCLSingletonDemo()");
    }

    public static DCLSingletonDemo getInstance(){
        if(dclSingletonDemo == null){
            synchronized (DCLSingletonDemo.class){
                if(dclSingletonDemo == null){
                    dclSingletonDemo = new DCLSingletonDemo();
                }
            }
        }
        return dclSingletonDemo;
    }

    public static void main(String[] args){
        for (int i = 0; i < 10; i++) {
            new Thread(() ->{
                DCLSingletonDemo.getInstance();
            },"A").start();
        }
    }

}
