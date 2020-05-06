package com.github.cynaith.jedis;

import com.github.cynaith.exceptions.LedisDataException;
import com.github.cynaith.exceptions.UnserizlizeException;
import com.github.cynaith.util.ObjectUtil;
import com.github.cynaith.util.SerializeObjectTool;
import com.sun.xml.internal.ws.encoding.soap.SerializationException;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.exceptions.JedisDataException;

import java.util.*;

/**
 * @USER: lynn
 * @DATE: 2020/5/3
 * refer : https://blog.csdn.net/weixin_34152820/article/details/86443458
 **/
public class Ledis implements LedisCommands {

    Jedis jedis;

    public Ledis(String host, int port) {
        jedis = new Jedis(host, port);
    }

    public Ledis(String host, int port, String password) {
        jedis = new Jedis(host, port);
        try {
            jedis.auth(password);
        } catch (JedisDataException e) {
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
     *
     * @param emptyValue
     * @param key
     * @return
     */
    public List<String> mgets(String emptyValue, String... key) {
        List<String> results = new ArrayList<String>();
        for (int i = 0; i < key.length; i++) {
            String result = jedis.get(key[i]);
            try {
                if (ObjectUtil.isEmpty(result)) {
                    results.add(emptyValue);
                } else results.add(result);
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

    public Object getObj(String key) {
        if (!jedis.exists(key.getBytes()))
            throw new NullPointerException("redis doesn't have the object you want");
        else {
            byte[] value = jedis.get(key.getBytes());
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
    public Long lpush(String key, String... strings) {
        return jedis.lpush(key, strings);
    }

    public Long rpush(String key, String... strings) {
        return jedis.rpush(key, strings);
    }

    /**
     * 从左边追加一个Object到list(key)中
     *
     * @param key
     * @param objects
     * @return
     */
    public Long lpush(String key, Object... objects) {
        byte[][] bytes = new byte[objects.length][];
        for (int i = 0; i < objects.length; i++) {
            bytes[i] = SerializeObjectTool.serialize(objects[i]);
        }
        return jedis.lpush(key.getBytes(), bytes);
    }

    /**
     * 从右边追加一个Object到list(key)中
     *
     * @param key
     * @param objects
     * @return
     */
    public Long rpush(String key, Object... objects) {
        byte[][] bytes = new byte[0][];
        for (int i = 0; i < objects.length; i++) {
            bytes[i] = SerializeObjectTool.serialize(objects[i]);
        }
        return jedis.rpush(key.getBytes(), bytes);
    }

    public Long llen(String key) {
        return jedis.llen(key);
    }

    public Object lindex(String key, int index) {
        return jedis.lindex(key, index);
    }

    public Long lrem(String key, long count, Object value) {
        if (value instanceof String) {
            return jedis.lrem(key, count, value.toString());
        } else {
            byte[] bytes = SerializeObjectTool.serialize(value);
            return jedis.lrem(key.getBytes(), count, bytes);
        }
    }

    public String ltrim(String key, int i, int j) {
        return jedis.ltrim(key, i, j);
    }

    public String lpop(String key) {
        return jedis.lpop(key);
    }

    public String rpop(String key) {
        return jedis.rpop(key);
    }

    public String lset(String key, int index, Object value) {
        if (value instanceof String) {
            return jedis.lset(key, index, value.toString());
        } else {
            byte[] bytes = SerializeObjectTool.serialize(value);
            return jedis.lset(key.getBytes(), index, bytes);
        }
    }

    public List<String> sort(String key) {
        if (jedis.llen(key) == 0) {
            throw new LedisDataException("this list has no data");
        } else {
            return jedis.sort(key);
        }

    }

    public List<Object> sortObj(String key) {
        if (jedis.llen(key) == 0) {
            throw new LedisDataException("this list has no data");
        } else {
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

            if (objects[i] instanceof String) {
                bytes[i] = ((String) objects[i]).getBytes();
            } else {
                bytes[i] = SerializeObjectTool.serialize(objects[i]);
            }
        }
        return jedis.sadd(key.getBytes(), bytes);
    }

    public Set<Object> smember(String key) {
        Set<Object> result = new HashSet<Object>();
        Set<byte[]> objects = jedis.smembers(key.getBytes());
        Iterator<byte[]> it = objects.iterator();
        for (byte[] obj : objects) {
            try {
                result.add(SerializeObjectTool.unserizlize(obj));
            } catch (Exception e) {
                result.add(new String(obj));
            }
        }
        return result;
    }


    public Long srem(String key, Object... objects) {
        byte[][] bytes = new byte[objects.length][];
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] instanceof String) {
                bytes[i] = objects[i].toString().getBytes();
            } else {
                bytes[i] = SerializeObjectTool.serialize(objects[i]);
            }
        }
        return jedis.srem(key.getBytes(), bytes);
    }

    public Object spop(String key) {
        byte[] object = jedis.spop(key.getBytes());
        Object value;
        try {
            return value = SerializeObjectTool.unserizlize(object);
        } catch (UnserizlizeException e) {
            return value = new String(object);
        }
    }

    public Long scared(String key) {
        return jedis.scard(key.getBytes());
    }

    public Long smove(String key1, String key2, Object val) {
        if (val instanceof String) {
            return jedis.smove(key1, key2, val.toString());
        } else {
            return jedis.smove(key1.getBytes(), key2.getBytes(), SerializeObjectTool.serialize(val));
        }
    }

    public Set<Object> sinter(String key1, String key2) {
        Set<Object> result = new HashSet<Object>();
        Set<byte[]> objects = jedis.sinter(key1.getBytes(), key2.getBytes());
        for (byte[] obj : objects) {
            try {
                result.add(SerializeObjectTool.unserizlize(obj));
            } catch (Exception e) {
                result.add(new String(obj));
            }
        }
        return result;
    }

    public Set<Object> sunion(String key1, String key2) {
        Set<Object> result = new HashSet<Object>();
        Set<byte[]> objects = jedis.sunion(key1.getBytes(), key2.getBytes());
        for (byte[] obj : objects) {
            try {
                result.add(SerializeObjectTool.unserizlize(obj));
            } catch (Exception e) {
                result.add(new String(obj));
            }
        }
        return result;
    }

    public Set<Object> sdiff(String key1, String key2) {
        Set<Object> result = new HashSet<Object>();
        Set<byte[]> objects = jedis.sdiff(key1.getBytes(), key2.getBytes());
        for (byte[] obj : objects)
            try {
                result.add(SerializeObjectTool.unserizlize(obj));
            } catch (Exception e) {
                result.add(new String(obj));
            }

        return result;
    }

    public Long zadd(String key, Map<String, Double> map) {
        return jedis.zadd(key, map);
    }

    public Long zadd(String key, double score, String member) {
        return jedis.zadd(key, score, member);
    }


    public Set<String> zrange(String key, int i, int j) {
        return jedis.zrange(key, i, j);
    }

    public Map<String, Double> zrangeWithScores(String key, int i, int j) {
        Map<String, Double> result = new HashMap<String, Double>();
        Set<Tuple> tuples = jedis.zrangeWithScores(key, i, j);
        for (Tuple tuple : tuples) {
            result.put(tuple.getElement(), tuple.getScore());
        }
        return result;
    }

    public Set<String> zrangeByScore(String key, double i, double j) {
        return jedis.zrangeByScore(key, i, j);
    }

    public Map<String, Double> zrangByScoreWithScores(String key, double i, double j) {
        Map<String, Double> result = new HashMap<String, Double>();
        Set<Tuple> tuples = jedis.zrangeByScoreWithScores(key, i, j);
        for (Tuple tuple : tuples) {
            result.put(tuple.getElement(), tuple.getScore());
        }
        return result;
    }

    public Double zscore(String key, String value) {
        return jedis.zscore(key, value);
    }

    public Long zrank(String key, String value) {
        return jedis.zrank(key, value);
    }

    public Long zrem(String key, String... value) {
        return jedis.zrem(key, value);
    }

    public Long zcard(String key) {
        return jedis.zcard(key);
    }

    public Long zcount(String key, double i, double j) {
        return jedis.zcount(key, i, j);
    }

    public Double zincrby(String key, double n, String value) {
        return jedis.zincrby(key, n, value);
    }


    public String hmset(String key, Map<String, Object> map) {
        Map<byte[], byte[]> byteMap = new HashMap<byte[], byte[]>();
        for (Map.Entry<String, Object> objectEntry : map.entrySet()) {
            if (objectEntry.getValue() instanceof String) {
                byteMap.put(objectEntry.getKey().getBytes(), objectEntry.getValue().toString().getBytes());
            } else {
                byteMap.put(objectEntry.getKey().getBytes(), SerializeObjectTool.serialize(objectEntry.getValue()));
            }
        }
        return jedis.hmset(key.getBytes(), byteMap);
    }

    public List<Object> hmget(String key, String... mapkeys) {
        byte[][] byteKeys = new byte[mapkeys.length][];
        for (int i = 0; i < mapkeys.length; i++) {
            byteKeys[i] = mapkeys.toString().getBytes();
        }
        List<byte[]> init = jedis.hmget(key.getBytes(), byteKeys);
        List<Object> result = new ArrayList<Object>();
        for (int i = 0; i < init.size(); i++) {
            try{
                result.add(SerializeObjectTool.unserizlize(init.get(i)));
            }catch (UnserizlizeException e){
                result.add(new String(init.get(i)));
            }
        }
        return result;
    }

    public Long hset(String key, String mapkey, Object mapvalue) {
        if (mapvalue instanceof String) {
            return jedis.hset(key, mapkey, mapvalue.toString());
        } else {
            return jedis.hset(key.getBytes(), mapkey.getBytes(), SerializeObjectTool.serialize(mapvalue));
        }

    }

    public Map<String, Object> hgetAll(String key) {
        Map<String, Object> results = new HashMap<String, Object>();
        Map<byte[], byte[]> init = jedis.hgetAll(key.getBytes());
        for (Map.Entry<byte[], byte[]> result : init.entrySet()) {
            try {
                results.put(new String(result.getKey()), SerializeObjectTool.unserizlize(result.getValue()));
            } catch (UnserizlizeException e) {
                results.put(new String(result.getKey()), new String(result.getValue()));
            }

        }
        return results;
    }

    public Set<String> hkeys(String key) {
        Set<String> results = new HashSet<String>();
        Set<byte[]> init = jedis.hkeys(key.getBytes());
        for (byte[] result : init) {
            results.add(new String(result));
        }
        return results;
    }

    public List<Object> hvals(String key) {
        List<Object> results = new ArrayList<Object>();
        List<byte[]> init = jedis.hvals(key.getBytes());
        for (int i = init.size() - 1; i >= 0; i--) {
            try {
                results.add(SerializeObjectTool.unserizlize(init.get(i)));
            } catch (UnserizlizeException e) {
                results.add(new String(init.get(i)));
            }
        }
        return results;
    }

    public Long hdel(String key, String... keys) {
        byte[][] byteKeys = new byte[keys.length][];
        for (int i = 0; i < keys.length; i++) {
            byteKeys[i] = keys.toString().getBytes();
        }
        return jedis.hdel(key.getBytes(), byteKeys);
    }

    public Long hlen(String key) {
        return jedis.hlen(key);
    }

    public boolean hexists(String key, String mapkey) {
        return jedis.hexists(key.getBytes(), mapkey.getBytes());
    }
}
