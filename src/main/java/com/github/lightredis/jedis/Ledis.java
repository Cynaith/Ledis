package com.github.lightredis.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.ArrayList;
import java.util.List;

/**
 * @USER: lynn
 * @DATE: 2020/5/3
 * refer : https://blog.csdn.net/weixin_34152820/article/details/86443458
 **/
public class Ledis implements LedisCommands{

    Jedis jedis ;

    private Ledis(){

    }

    public Ledis(Jedis jedis) {
        this.jedis = jedis;
    }

    public static Ledis getInstance(){
        LedisInterceptor ledisInterceptor = new LedisInterceptor();
        return ledisInterceptor.getLedis();
    }

    public String set(String key, String value) {
        return jedis.set(key, value);
    }

    public String get(String key) {
        return jedis.get(key);
    }

    public String type(String key) {
        return jedis.type(key);
    }

    public Boolean exists(String key) {
        return jedis.exists(key);
    }

    public String randomKey() {
        return jedis.randomKey();
    }

    public String rename(String oldKey, String newKey) {
        return jedis.rename(oldKey, newKey);
    }

    public Long del(String key) {
        return jedis.del(key);
    }

    public List<String> scan(String prefix,String suffix) {

        String scanCursor= "0";
        ScanParams scanParams = new ScanParams();
        scanParams.count(3000);
        List<String> resultList = new ArrayList<String>();
        do{
            ScanResult result = jedis.scan(scanCursor,scanParams.match(prefix+"*"+suffix));
            scanCursor = result.getStringCursor();
            resultList.addAll(result.getResult());
        }while (!(scanCursor.equals("0")));
        return resultList;
    }

}
