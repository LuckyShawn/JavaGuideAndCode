package OOM;

import java.util.concurrent.TimeUnit;

/**
 * @Description 谨慎运行，LInux环境下用非Root用户
 * @Author shawn
 * @create 2019/5/20 0020
 */
public class UnableCreateNativeNewThreadDemo {
    public static void main(String[] args){

        for (int i = 1; ; i++) {
            System.out.println("*********i="+i);
                new Thread(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            ).start();
        }
    }
}
