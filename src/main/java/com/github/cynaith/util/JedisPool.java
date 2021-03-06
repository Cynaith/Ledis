package com.github.cynaith.util;

import com.github.cynaith.exceptions.JedisNullException;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;


public class JedisPool {
    private JedisPool(){
    }
    private static redis.clients.jedis.JedisPool pool;
    static {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(8);
        config.setMaxTotal(30);
        pool = new redis.clients.jedis.JedisPool(config,"127.0.0.1",6379,2000);
    }

    public static Jedis getJedis(){
        Jedis jedisByPool = pool.getResource();
        if (ObjectUtil.isEmpty(jedisByPool)){
            throw new JedisNullException("JedisPool is Full");
        }
        return jedisByPool;
    }

    public static void closeJedis(Jedis jedis){
        if (ObjectUtil.isEmpty(jedis)){
            throw new JedisNullException("Jedis is Null");
        }
        jedis.close();
    }
}
