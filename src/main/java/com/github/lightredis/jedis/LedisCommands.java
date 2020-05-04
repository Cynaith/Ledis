package com.github.lightredis.jedis;

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

    /**
     * 查询指定前缀后缀的value
     * @param prefix
     * @param suffix
     * @return
     */
    List<String> scan(String prefix,String suffix);

    Long setnx(String key, String value);

    List<String> mget(String... key);

    /**
     * 当key不存在时，返回emptyValue
     * @param emptyValue
     * @param key
     * @return
     */
    List<String> mgets(String emptyValue,String... key);

    /**
     * setObject
     * @param key
     * @param value
     * @return
     */
    String setObj(String key,Object value);

}
