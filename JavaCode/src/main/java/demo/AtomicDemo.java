package demo;


/**
 * @Description TODO
 * @Author shawn
 * @create 2019/5/22 0022
 */
public class AtomicDemo {
    public static void main(String[] args){
        ResourceTest test = new ResourceTest();
        for (int i = 1; i <= 20; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                test.add();
                System.out.print(ResourceTest.i+",");
            }).start();
        }
    }
}

class ResourceTest{
    static volatile int i = 1;
    public void add(){
         i++;
    }

    public static int getI() {
        return i;
    }

    public static void setI(int i) {
        ResourceTest.i = i;
    }
}
