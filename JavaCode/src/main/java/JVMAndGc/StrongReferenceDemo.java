package JVMAndGc;

/**
 * @Description TODO
 * @Author shawn
 * @create 2019/5/17 0017
 */
public class StrongReferenceDemo {
    public static void main(String[] args){
        Object obj1 = new Object(); //这样定义的默认就是强引用
        Object obj2 = obj1; //obj2引用赋值
        obj1 = null;
        System.gc();
        System.out.println(obj2);
        //java.lang.Object@14ae5a5  并不会回收
    }
}
