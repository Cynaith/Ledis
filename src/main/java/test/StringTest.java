package test;

import com.github.lightredis.jedis.Ledis;

import java.util.List;

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
//        List<String> result ;
//        cutTime.startTime();
//        Ledis ledis = Ledis.getInstance();
//        result = ledis.scan("ledis", "7");
//        System.out.println(cutTime.endTime());
//        Iterator<String> iterator = result.listIterator();
//        while (true){
//            if (iterator.hasNext()){
//                System.out.println(iterator.next());
//            }
//            else break;
//        }
//
//    }
    /**
     * 测试Jedis操作
     * 100000数据set : 3426
     * 100000数据set : 3483(pool)
     */
//public static void main(String[] args) {
//    CutTime cutTime = CutTime.getInstance();
//    cutTime.startTime();
////    JedisPoolConfig config = new JedisPoolConfig();
////    config.setMaxIdle(5);
////    config.setMaxTotal(500);
////    config.setMaxWaitMillis(1000*100);
////    config.setTestOnBorrow(true);
////    JedisPool pool = new JedisPool(config,"127.0.0.1",6379,10000);
////    Jedis jedis = pool.getResource();
//    Jedis jedis = new Jedis("127.0.0.1",6379);
//    for (int i=0;i<100000;i++){
//        jedis.set("ledis:num"+i,String.valueOf(i));
//    }
//    System.out.println(cutTime.endTime());
//}

    public static void main(String[] args) {
       Ledis ledis = Ledis.getInstance();
       List<String> list = ledis.mgets("empty","ledis:num1","ledis:nu2");
        System.out.println(list.get(0)+list.get(1));
    }
}
