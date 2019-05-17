package MultithreadDemo;

import java.util.concurrent.TimeUnit;

/**
 * @Description JVM XX参数
 * @Author shawn
 * @create 2019/5/16 0016
 */
public class JVMParam {
    public static void main(String[] args) throws InterruptedException {
        Long totalMemory = Runtime.getRuntime().totalMemory();//返回java虚拟机中的内存总量
        Long MaxMemory = Runtime.getRuntime().maxMemory();  //返回java虚拟机试图使用的最大内存
        System.out.println("-Xms="+totalMemory+"(字节)");
        System.out.println("-Xmx="+MaxMemory+"(字节)");

        System.out.println("Hello JVM");
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);

        //Byte[] bytes = new Byte[50 * 1024 * 1024];
    }
}
