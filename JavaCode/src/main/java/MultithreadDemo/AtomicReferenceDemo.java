package MultithreadDemo;

;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @Description TODO
 * @Author shawn
 * @create 2019/5/13 0013
 */

public class AtomicReferenceDemo {
    public static void main(String[] args){
        User zs = new User("张三",22);
        User lisi = new User("李四",25);

        AtomicReference<User> atomicReference = new AtomicReference<>();
        atomicReference.set(zs);

        System.out.println(atomicReference.compareAndSet(zs,lisi)+"\t "+ atomicReference.get().toString());
        System.out.println(atomicReference.compareAndSet(zs,lisi)+"\t "+ atomicReference.get().toString());
    }
}

@Getter
@ToString
@AllArgsConstructor
class User {
    String userName;
    int age;
}
