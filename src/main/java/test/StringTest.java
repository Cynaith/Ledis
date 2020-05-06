package test;

import com.github.lightredis.jedis.Ledis;
import com.github.lightredis.util.CutTime;
import com.github.lightredis.util.SerializeObjectTool;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @USER: lynn
 * @DATE: 2020/5/3
 **/
public class StringTest {
    /**
     * 测试Ledis操作
     * 100000数据set : 3617ms
     * 100000数据scan: 237ms
     */
//    public static void main(String[] args) {
//        CutTime cutTime = CutTime.getInstance();
//        cutTime.startTime();
//        Ledis ledis = new Ledis("127.0.0.1",6379);
//        for (int i = 0; i < 100000; i++) {
//            ledis.set("ledis:num" + i, String.valueOf(i));
//        }
//        System.out.println(cutTime.endTime());
//    }
    /**
     * 测试Jedis操作
     * 100000数据set : 3426
     * 100000数据set : 3483(pool)
     */
//public static void main(String[] args) {
//    CutTime cutTime = CutTime.getInstance();
//    cutTime.startTime();
//    JedisPoolConfig config = new JedisPoolConfig();
//    config.setMaxIdle(20);
//    config.setMaxTotal(100);
//    config.setMaxWaitMillis(1000*100);
//    config.setTestOnBorrow(true);
//    JedisPool pool = new JedisPool(config,"127.0.0.1",6379,10000);
//
////    Jedis jedis = new Jedis("127.0.0.1",6379);
//    for (int i=0;i<100000;i++){
//        Jedis jedis = pool.getResource();
//        jedis.set("ledis:num"+i,String.valueOf(i));
//        jedis.close();
//    }
//    System.out.println(cutTime.endTime());
//}

    /**
     * SortedSet
     * @param args
     */
//    public static void main(String[] args) {
//        Ledis ledis = new Ledis("127.0.0.1",6379);
//        ledis.zadd("sort",1,"liu");
//        ledis.zadd("sort",2,"li");
//        ledis.zadd("sort",3,"wng");
//        ledis.zadd("sort",4,"zheng");
//        ledis.zadd("sort",5,"wang");
//        Map<String,Double> strings = ledis.zrangeWithScores("sort",2,4);
//        System.out.println(ledis.zcount("sort",2,5));
//
//    }

}

class Car implements Serializable {

    private static final long serialVersionUID = -5927431407988171786L;

    String color;

    public Car(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Car{" +
                "color='" + color + '\'' +
                '}';
    }
}