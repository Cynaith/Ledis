package com.github.lightredis.jedis;

import java.util.List;
import java.util.Set;

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
     * 获取多个key
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

    /**
     * getObject
     * @param key
     * @return
     */
    Object getObj(String key);

    /**
     * List操作
     */

    Long lpush(String key, String... strings);

    public Long rpush(String key,String... strings);

    /**
     * 从左边追加一个Object到list(key)中
     * @param key
     * @param objects
     * @return
     */
    public Long lpush(String key,Object... objects);

    /**
     * 从右边追加一个Object到list(key)中
     * @param key
     * @param objects
     * @return
     */
     Long rpush(String key,Object... objects);

     Long llen(String key);

     Object lindex(String key,int index);

    /**
     * 增加对Object的删除
     * @param key
     * @param count
     * @param value
     * @return
     */
     Long lrem(String key,long count,Object value);

     String ltrim(String key,int i, int j);

     String lpop(String key);

     String rpop(String key);

    /**
     * 增加对Object的修改
     * @param key
     * @param index
     * @param object
     * @return
     */
     String lset(String key,int index,Object object);

     List<String> sort(String key);

    /**
     * 代优化
     * 对象排序 好像没有用
     * 可以加一个对象参数排序（仅支持int、long）;
     * @param key
     * @return
     */
     List<Object> sortObj(String key);

    /**
     * 以下为Set操作
     * Set不允许出现重复元素
     * 对象属性相同也是重复的
     * @return
     */
    Long sadd(String key, Object... objects);

    Set<Object> smember(String key);

}
