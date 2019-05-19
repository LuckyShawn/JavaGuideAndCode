package OOM;

import java.util.Random;

/**
 * @Description TODO
 * @Author shawn
 * @create 2019/5/17 0017
 */
public class JavaHeapSpaceDemo {
        public static void main(String[] args){
            String str = "shawn";
            while (true){
                str += str + new Random(100000).nextInt() + new Random(25555555).nextInt();
            }
        }
}
