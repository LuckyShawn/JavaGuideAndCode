package OOM;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO
 * @Author shawn
 * @create 2019/5/17 0017
 */
public class GCOverHeadDemo {
    public static void main(String[] args){
        int i = 0;
        List list = new ArrayList();
        try {
            while (true){
                list.add(String.valueOf(++i).intern());
            }
        }catch (Throwable e){
            System.out.println("***************:"+i);
            e.printStackTrace();
            throw  e;
        }
    }
}
