package OOM;

import java.lang.reflect.Array;
import java.util.Random;

/**
 * @Description Java 8 及以后的版本使用Metaspace来替代永久代
 *          JVM参数：-XX:MetaspaceSize=8m -XX:MaxMetaspaceSize=8m
 *          Metaspace的方法区在HotSpot中的实现，它与持久代最大的区别在于：Metaspace并不在虚拟机内存中，而是使用本地内存
 *          永久代java8后被元空间Metaspace取代，存放了一下信息：
 *              虚拟机加载的类信息
 *              常量池
 *              静态变量
 *              即时编译后的代码
 *          为了模拟Metaspace空间溢出，我们不断生成类放入元空间中，超过Metasapce指定的空间大小，报异常！
 * @Author shawn
 * @create 2019/5/20 0020
 */
public class MetaspaceOOMDemo {

    static class OOMTest{ }

    public static void main(String[] args){
        int i = 0;//模拟计数器，记录多少次发生异常
        String str = "shawn";
        try {
            while(true){
                i++;
                //此处用Enhancer
            }
        }catch (Throwable e){
            System.out.println("******多少次后发生了异常："+i);
            e.printStackTrace();
        }
    }
}
