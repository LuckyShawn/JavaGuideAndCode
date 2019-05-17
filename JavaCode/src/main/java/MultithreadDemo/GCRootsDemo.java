package MultithreadDemo;

/**
 * @Description TODO
 * 1.虚拟机栈（栈帧中的局部变量区，也叫做局部变量表）中引用的对象
 * 2.方法区中的类静态属性引用的对象
 * 3.方取区中常量引用的对象
 * 4.本地方法栈中JNI（Native方法）引用的对象
 * @Author shawn
 * @create 2019/5/16 0016
 */
public class GCRootsDemo {

    private static GCRootsDemo gcRootsDemo;//静态引用的对象
    private final static GCRootsDemo gcRootsDemo1 = new GCRootsDemo();//常量引用的对象

    public static void main(String[] args){
        m1();
    }

    public static void m1(){    //栈中的方法
        GCRootsDemo gcRootsDemo = new GCRootsDemo();//1.栈中引用的对象
    }
}
