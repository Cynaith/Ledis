package com.github.lightredis.jedis;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 在Ledis中 key只允许String类型
 * 除SortSet外，value都可以为Object
 * @USER: lynn
 * @DATE: 2020/5/3
 **/
public interface LedisCommands {
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
     *
     */

    /**
     * Set不允许出现重复元素
     * 对象属性相同也是重复的
     * @return
     */
    Long sadd(String key, Object... objects);

    /**
     * 获取key对应Set的所有元素 （不区分对象字符串）
     *
     * @param key
     * @return
     */
    Set<Object> smember(String key);

    /**
     * 删除key对应Set中的Object （也可以是String）
     * @param key
     * @param objects
     * @return
     */
    Long srem(String key, Object... objects);

    /**
     * 随机弹出一个元素 （不区分对象字符串）
     * @param key
     * @return
     */
    Object spop(String key);


    Long scared(String key);

    /**
     * 将元素从 key1(Set) 中移到 key2(Set) 中 （不区分对象字符串）
     * @param key1
     * @param key2
     * @param val
     * @return
     */
    Long smove(String key1, String key2, Object val);

    /**
     * 获取集合key1和key2的交集 (不区分对象字符串)
     * @param key1
     * @param key2
     * @return
     */
    Set<Object> sinter(String key1,String key2);
    /**
     * 获取集合key1和key2的并集 (不区分对象字符串)
     * @param key1
     * @param key2
     * @return
     */
    Set<Object> sunion(String key1,String key2);

    /**
     * 获取集合key1和key2的差集 (不区分对象字符串)
     * @param key1
     * @param key2
     * @return
     */
    Set<Object> sdiff(String key1,String key2);


    /**
     * 以下为 有序集合Zsort
     *
     * @return
     */

    Long zadd(String key, Map<String,Double> map);

    Long zadd(String key, double score,String member);

    Set<String> zrange(String key, int i , int j);

    /**
     * 修改原返回值 Map<Tuple> -> Map<String , Double>
     *
     * @param key
     * @param i
     * @param j
     * @return
     */
    Map<String , Double> zrangeWithScores(String key, int i , int j);


    Set<String> zrangeByScore(String key, double i , double j);

    /**
     * 修改原返回值 Map<Tuple> -> Map<String , Double>
     * @param key
     * @param i
     * @param j
     * @return
     */
    Map<String, Double> zrangByScoreWithScores(String key, double i , double j);

    Double zscore(String key, String value);

    Long zrank(String key,String value);

    Long zrem(String key, String... value);

    Long zcard(String key);

    Long zcount(String key , double i ,double j);

    Double zincrby(String key, double n, String value);

    /**
     * 以下为 Hash
     * @return
     */

    /**
     * Map<String,String>和Map<byte[],byte[]>
     *     转换成----> Map<String,Object>
     *     注：Object支持String，会自动转换
     * @param key
     * @param map
     * @return
     */
    String hmset(String key, Map<String,Object> map);

    /**
     * 获取Hash中一个或多个元素value 包含Object和String
     * @param key
     * @param mapkeys
     * @return
     */
    List<Object> hmget(String key,String... mapkeys);

    /**
     * 支持向HashMap插入Object
     * @param key
     * @param mapkey
     * @param mapvalue
     * @return
     */
    Long hset(String key, String mapkey, Object mapvalue);

    /**
     * 获取全部元素k-V 包括Object和String
     * @param key
     * @return
     */
    Map<String,Object> hgetAll(String key);

    Set<String> hkeys(String key);

    /**
     * 获取全部元素的value 包括Object和String
     * @param key
     * @return
     */
    List<Object> hvals(String key);

    Long hdel(String key, String... keys);

    Long hlen(String key);

    boolean hexists(String key,String mapkey);
}
