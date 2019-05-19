package OOM;

/**
 * @Description TODO
 * @Author shawn
 * @create 2019/5/17 0017
 */
public class StackOverFlowErrorDemo {
    public static void main(String[] args){
        stackOverflowErrorTest();
    }

    private static void stackOverflowErrorTest() {
        stackOverflowErrorTest();
    }
}
