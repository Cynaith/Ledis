package com.github.lightredis.jedis;

import com.github.lightredis.exceptions.LedisDataException;
import com.github.lightredis.util.ObjectUtil;
import com.github.lightredis.util.SerializeObjectTool;
import com.sun.xml.internal.ws.encoding.soap.SerializationException;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.exceptions.JedisDataException;

import java.util.*;

/**
 * @USER: lynn
 * @DATE: 2020/5/3
 * refer : https://blog.csdn.net/weixin_34152820/article/details/86443458
 **/
public class Ledis implements LedisCommands {

    Jedis jedis;

    public Ledis(String host,int port) {
        jedis = new Jedis(host,port);
    }

    public Ledis(String host,int port,String password) {
        jedis = new Jedis(host,port);
        try{
            jedis.auth(password);
        }catch (JedisDataException e){
            throw new LedisDataException("ERR Client sent AUTH, but no password is set");
        }
    }

    public void setJedis(Jedis jedis) {
        this.jedis = jedis;
    }

    public Jedis getJedis() {
        return jedis;
    }

    private Ledis(Jedis jedis) {
        this.jedis = jedis;
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

    public List<String> scan(String prefix, String suffix) {

        String scanCursor = "0";
        ScanParams scanParams = new ScanParams();
        scanParams.count(3000);
        List<String> resultList = new ArrayList<String>();
        do {
            ScanResult result = jedis.scan(scanCursor, scanParams.match(prefix + "*" + suffix));
            scanCursor = result.getStringCursor();
            resultList.addAll(result.getResult());
        } while (!(scanCursor.equals("0")));
        return resultList;
    }

    public Long setnx(String key, String value) {
        return jedis.setnx(key, value);
    }

    public List<String> mget(String... key) {
        return jedis.mget(key);
    }

    /**
     * 当key不存在时，返回emptyValue
     * @param emptyValue
     * @param key
     * @return
     */
    public List<String> mgets(String emptyValue, String... key) {
        List<String> results = new ArrayList<String>();
        for (int i = 0; i < key.length; i++) {
            String result = jedis.get(key[i]);
            try {
                if (ObjectUtil.isEmpty(result)){
                    results.add(emptyValue);
                }
                else results.add(result);
            } catch (NullPointerException e) {

            }
        }
        return results;
    }

    public String setObj(String key, Object value) {
        if (value instanceof java.io.Serializable)
            return jedis.set(key.getBytes(), SerializeObjectTool.serialize(value));

        else {
            throw new SerializationException("object should be serialization");
        }
    }

    public Object getObj(String key){
        if (!jedis.exists(key.getBytes()))
            throw new NullPointerException("redis doesn't have the object you want");
        else {
            byte[] value= jedis.get(key.getBytes());
            Object object = SerializeObjectTool.unserizlize(value);
            return object;
        }
    }


    /**
     * 以上为String操作
     */
    /**
     * 以下为List操作
     */
    public Long lpush(String key, String... strings){
        return jedis.lpush(key, strings);
    }

    public Long rpush(String key,String... strings){
        return jedis.rpush(key, strings);
    }

    /**
     * 从左边追加一个Object到list(key)中
     * @param key
     * @param objects
     * @return
     */
    public Long lpush(String key,Object... objects){
        byte[][] bytes = new byte[objects.length][];
        for(int i=0;i<objects.length;i++){
            bytes[i] = SerializeObjectTool.serialize(objects[i]);
        }
        return jedis.lpush(key.getBytes(),bytes);
    }

    /**
     * 从右边追加一个Object到list(key)中
     * @param key
     * @param objects
     * @return
     */
    public Long rpush(String key,Object... objects){
        byte[][] bytes = new byte[0][];
        for(int i=0;i<objects.length;i++){
            bytes[i] = SerializeObjectTool.serialize(objects[i]);
        }
        return jedis.rpush(key.getBytes(),bytes);
    }

    public Long llen(String key){
        return jedis.llen(key);
    }

    public Object lindex(String key,int index){
        return jedis.lindex(key,index);
    }

    public Long lrem(String key, long count, Object value) {
        if (value instanceof String){
            return jedis.lrem(key,count,value.toString());
        }
        else {
            byte[] bytes = SerializeObjectTool.serialize(value);
            return jedis.lrem(key.getBytes(),count,bytes);
        }
    }

    public String ltrim(String key,int i, int j){
        return jedis.ltrim(key,i,j);
    }

    public String lpop(String key) {
        return jedis.lpop(key);
    }

    public String rpop(String key) {
        return jedis.rpop(key);
    }

    public String lset(String key, int index,Object value) {
        if (value instanceof String){
            return jedis.lset(key,index,value.toString());
        }
        else {
            byte[] bytes = SerializeObjectTool.serialize(value);
            return jedis.lset(key.getBytes(),index,bytes);
        }
    }

    public List<String> sort(String key) {
        if (jedis.llen(key)==0){
            throw new LedisDataException("this list has no data");
        }
        else {
            return jedis.sort(key);
        }

    }

    public List<Object> sortObj(String key) {
        if (jedis.llen(key)==0){
            throw new LedisDataException("this list has no data");
        }
        else {
            List<byte[]> bytes = jedis.sort(key.getBytes());
            List<Object> objects = new ArrayList<Object>();
            for (int i = 0; i < bytes.size(); i++) {
                Object object = SerializeObjectTool.serialize(bytes.get(i));
                objects.add(object);
            }
            return objects;
        }
    }

    public Long sadd(String key, Object... objects) {
        byte[][] bytes = new byte[objects.length][];

        for (int i = 0; i < objects.length; i++) {

            if (objects[i] instanceof String){
                bytes[i] = ((String)objects[i]).getBytes();
            }
            else {
                bytes[i] = SerializeObjectTool.serialize(objects[i]);
            }
        }
        return jedis.sadd(key.getBytes(),bytes);
    }

    public Set<Object> smember(String key) {
        Set<Object> result = new HashSet<Object>();
        Set<byte[]> objects = jedis.smembers(key.getBytes());
        Iterator<byte[]> it = objects.iterator();
        while (it.hasNext()) {
            byte[] n = it.next();
            result.add(SerializeObjectTool.unserizlize(n));
        }
        return result;
    }
}
