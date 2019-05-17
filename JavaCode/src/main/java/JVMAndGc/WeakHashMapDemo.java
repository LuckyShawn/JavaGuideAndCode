package JVMAndGc;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @Description WeakHashMap  垃圾回收会回收其中的元素
 *                  可以用于做高速缓存
 * @Author shawn
 * @create 2019/5/17 0017
 */
public class WeakHashMapDemo {
    public static void main(String[] args) throws InterruptedException {
        myHashMap();
        System.out.println("===========");
        myWeakHashMap();
    }


    public static void myHashMap(){
        Map<Integer,String> map = new HashMap();
        Integer key = new Integer(1);
        String value = "HashMap";
        map.put(key,value);
        key = null;
        System.gc();
        System.out.println(map+","+map.size());

    }

    public static void myWeakHashMap() throws InterruptedException {
        WeakHashMap<Integer,String> weakHashMap = new WeakHashMap<>();
        Integer key = new Integer(1);
        String value = "HashMap";
        weakHashMap.put(key,value);
        key = null;
        System.gc();
        System.out.println(weakHashMap+","+weakHashMap.size());
    }
}
