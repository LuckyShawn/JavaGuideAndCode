package OOM;

import java.nio.ByteBuffer;

/**
 * @Description 配置参数：-Xms10m -Xmx10m -XX:+PrintGCDetails -XX:MaxDirectMemorySize=5m
 *                  故障现象：java.lang.OutOfMemoryError: Direct buffer memory
 *                  导致原因：
 *                  写NIO程序经常使用ByteBuffer来读取或者写入数据。这是一种给予通道（channel）与缓冲区（Buffer）的I/O方式，它可以使用Native函数库直接分配堆外内存，
 *                  然后听过一个存储在Java堆里面的DirectByteBuffer对象作为这块内存的引用进行操作。
 *                  这样能在一些场景中显著提高性能，因为避免了在Java堆和Navive对中来回复制数据。
 *
 *                   ByteBuffer.allocate(capability)第一种方式是分配JVM堆内存，属于GC管辖范围，由于需要拷贝所以速度相对较慢
 *                  ByteBuffer.allocteDirect(capability)第二种方式是分配OS本地内存，不属于GC管辖范围，由于不需要内存拷贝所以速度相对较快
 *
 *                 但是如果不断分配本地内存，对内存很少使用，那么JVM就不需要执行GC，DirectByteBuffer对象们就不会被回收。
 *                  这时候堆内存充足，但本地内存可能已经使用光了。再次尝试分配本地内存就会出现OOM错误，程序奔溃。
 * @Author shawn
 * @create 2019/5/20 0020
 */
public class DirectBufferMemoryDemo {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("配置的MaxDirectMemory:"+sun.misc.VM.maxDirectMemory()/(double)1024/1024+"MB");
        Thread.sleep(3000);
        //-XX:MaxDirectMemorySize=5m  制造异常
        ByteBuffer buffer = ByteBuffer.allocateDirect(6*1024*1024);
        //java.lang.OutOfMemoryError: Direct buffer memory
    }
}
