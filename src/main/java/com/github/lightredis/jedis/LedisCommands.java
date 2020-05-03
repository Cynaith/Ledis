package com.github.lightredis.jedis;

import redis.clients.jedis.JedisCommands;

import java.util.List;

/**
 * @USER: lynn
 * @DATE: 2020/5/3
 **/
public interface LedisCommands{
    String set(String key,String value);

    String get(String key);

    String type(String key);

    Boolean exists(String key);

    String randomKey();

    String rename(String oldKey,String newKey);

    Long del(String key);

    List<String> scan(String prefix,String suffix);
}
