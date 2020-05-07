package com.github.cynaith.jedis;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 在Ledis中 key只允许String类型
 * 除SortSet外，value都可以为Object
 * @author Cynaith
 */
public interface LedisCommands {
    /**
     *
     * @param key 字符串类型的key
     * @param value 字符串类型的value
     * @return 返回value
     */
    String set(String key,String value);

    /**
     *
     * @param key 字符串类型的key
     * @return 返回key对应的value
     */
    String get(String key);

    /**
     *
     * @param key redis中存储的key
     * @return 返回键为key所对应value的数据类型
     */
    String type(String key);

    /**
     *
     * @param key Redis中存储的key
     * @return 返回是否在redis中是否存在key
     */
    Boolean exists(String key);

    /**
     * @return 随机返回一个key
     */
    String randomKey();

    /**
     * @param oldKey 旧key名
     * @param newKey 新key名
     * @return 将键oldkey改为newkey,当命名相同或不存在时返回错误
     */
    String rename(String oldKey,String newKey);

    /**
     *
     * @param key redis中对应的key
     * @return 移除一个key,返回id
     */
    Long del(String key);

    /**
     *
     * @param prefix 检索前缀
     * @param suffix 检索后缀
     * @return 检索列表
     */
    List<String> scan(String prefix,String suffix);

    /**
     *
     * @param key redis中对应的key
     * @param value redis中对应的key
     * @return redis中对应的id
     */
    Long setnx(String key, String value);

    /**
     *
     * @param key redis对应的多个key
     * @return 返回多个key对应的List
     */
    List<String> mget(String... key);

    /**
     * 获取多个key
     * 当key不存在时，返回emptyValue
     * @param emptyValue 默认为空的返回值
     * @param key redis中对应的key
     * @return 多个key对应的List 包含空返回
     */
    List<String> mgets(String emptyValue,String... key);

    /**
     * @param key redis中对应的key
     * @param value redis中对应的value(插入对象)
     * @return 插入成功返回OK
     */
    String setObj(String key,Object value);

    /**
     *
     *  @param key redis中对应的key
     *  @return key对应的对象 (或字符串对象)
     */
    Object getObj(String key);

    /**
     * List操作
     */

    /**
     *
     * @param key redis中list对应的key
     * @param strings 向list左插入多个字符串
     * @return 返回列表长度
     */
    Long lpush(String key, String... strings);

    /**
     *
     * @param key redis中list对应的key
     * @param strings 向list右插入多个字符串
     * @return 返回列表长度
     */
    public Long rpush(String key,String... strings);

    /**
     * 从左边追加一个Object到list(key)中
     * @param key redis中list对应的key
     * @param objects 向list左插入多个对象(包含字符串)
      * @return 返回列表长度
     */
    public Long lpush(String key,Object... objects);

    /**
     * 从右边追加一个Object到list(key)中
     * @param key redis中list对应的key
     * @param objects 向list右插入多个对象(包含字符串)
      * @return 返回列表长度
     */
     Long rpush(String key,Object... objects);

    /**
     *
     * @param key redis中list对应的key
     * @return 返回列表长度
     */
     Long llen(String key);

    /**
     *
     * @param key redis中list对应的key
     * @param index redis中list对应的索引
     * @return list对应索引处的对象
     */
     Object lindex(String key,int index);

    /**
     * 增加对Object的删除
     * @param key redis中list对应的key
     * @param count 删除数量
     * @param value 删除的value
      * @return 返回列表长度
     */
     Long lrem(String key,long count,Object value);

    /**
     *
     * @param key redis中list对应的key
     * @param i 删除区间左边界
     * @param j 删除区间右边界
     * @return 状态码
     */
     String ltrim(String key,int i, int j);

    /**
     *
     * @param key redis中list对应的key
     * @return 左侧弹出队列的元素
     */
     String lpop(String key);

    /**
     *
     * @param key redis中list对应的key
     * @return 右侧弹出队列的元素
     */
     String rpop(String key);

    /**
     * 增加对Object的修改
     * @param key redis中list对应的key
     * @param index 插入处索引
     * @param object 插入新value
      * @return 状态码
     */
     String lset(String key,int index,Object object);

     List<String> sort(String key);

    /**
     * 代优化
     * 对象排序 好像没有用
     * 可以加一个对象参数排序（仅支持int、long）;
     * @param key redis中list对应的key
     * @return 排序后list
     */
     List<Object> sortObj(String key);

    /**
     * 以下为Set操作
     *
     */

    /**
     *
     * Set不允许出现重复元素
     * 对象属性相同也是重复的
     * @param key redis中set对应的key
     * @param objects 向set中插入的多个对象
     * @return 添加成功返回1 失败则0
     */
    Long sadd(String key, Object... objects);

    /**
     * 获取key对应Set的所有元素 （不区分对象字符串）
     *
     * @param key redis中set对应的key
     * @return 返回key对应的全部对象 包含字符串对象
     */
    Set<Object> smember(String key);

    /**
     * 删除key对应Set中的Object （也可以是String）
     * @param key redis中set对应的key
     * @param objects 删除的对象
     * @return 元素被删除返回1，否则0
     */
    Long srem(String key, Object... objects);

    /**
     * 随机删除一个元素 （不区分对象字符串）
     * @param key redis中set对应的key
     * @return key对应的set中随机元素，失败返回nil
     */
    Object spop(String key);


    /**
     * @param key redis中set对应的key
     * @return set中元素个数
     */
    Long scared(String key);


    /**
     * 将元素从 key1(Set) 中移到 key2(Set) 中 （不区分对象字符串）
     * @param key1 redis中set对应的key
     * @param key2 redis中set对应的key
     * @param val 移动的对象
     * @return 移动成功返回1，否则0
     */
    Long smove(String key1, String key2, Object val);

    /**
     * 获取集合key1和key2的交集 (不区分对象字符串)
     * @param key1 redis中set对应的key
     * @param key2 redis中set对应的key
     * @return 返回两个set的交集
     */
    Set<Object> sinter(String key1,String key2);
    /**
     * 获取集合key1和key2的并集 (不区分对象字符串)
     * @param key1 redis中set对应的key
     * @param key2 redis中set对应的key
     * @return 返回两个set的并集
     */
    Set<Object> sunion(String key1,String key2);

    /**
     * 获取集合key1和key2的差集 (不区分对象字符串)
     * @param key1 redis中set对应的key
     * @param key2 redis中set对应的key
     * @return 返回两个set的差集
     */
    Set<Object> sdiff(String key1,String key2);


    /**
     * 以下为 有序集合Zsort
     */

    /**
     *
     * @param key redis中sortedset对应的key
     * @param map 向SortedSet插入多个值
     * @return 成功返回1，否则0
     */
    Long zadd(String key, Map<String,Double> map);

    /**
     *
     * @param key redis中sortedset对应的key
     * @param score sortedset对应的score
     * @param member redis中sortedset对应的member
     * @return 成功返回1，否则0
     */
    Long zadd(String key, double score,String member);

    /**
     *
     * @param key redis中sortedset对应的key
     * @param i 区间左边界
     * @param j 区间右边界
     * @return 区间内的K
     */
    Set<String> zrange(String key, int i , int j);

    /**
     * 修改原返回值 Map(Tuple) 为 Map(String , Double)
     *
     *  @param key redis中sortedset对应的key
     *  @param i 区间左边界
     *  @param j 区间右边界
     *  @return 区间内的K-V
     */
    Map<String , Double> zrangeWithScores(String key, int i , int j);


    /**
     *
     * @param key redis中sortedset对应的key
     * @param i score区间左边界
     * @param j score区间右边界
     * @return 区间内的K
     */
    Set<String> zrangeByScore(String key, double i , double j);

    /**
     * 修改原返回值 Map(Tuple) 为 Map(String , Double)
     *  @param key redis中sortedset对应的key
     *  @param i score区间左边界
     *  @param j score区间右边界
     *  @return 区间内的K-V
     */
    Map<String, Double> zrangByScoreWithScores(String key, double i , double j);

    /**
     *
     * @param key redis中sortedset对应的key
     * @param value sortedset中的K
     * @return sortedset中K对应的score
     */
    Double zscore(String key, String value);

    /**
     *
     * @param key redis中sortedset对应的key
     * @param value sortedset中的K
     * @return 返回sortedset中K的排名
     */
    Long zrank(String key,String value);

    /**
     *
     * @param key redis中sortedset对应的key
     * @param value sortedset中的K
     * @return 成功返回1，否则0
     */
    Long zrem(String key, String... value);

    /**
     *
     * @param key redis中sortedset对应的key
     * @return 当 key 存在且是有序集类型时，返回有序集的基数。 当 key 不存在时，返回 0 。
     */
    Long zcard(String key);

    /**
     * 获取ZSet中score在[i,j]区间的元素个数
     * @param key redis中sortedset对应的key
     * @param i score区间左边界
     * @param j score区间右边界
     * @return 区间内元素个数
     */
    Long zcount(String key , double i ,double j);

    /**
     * 把ZSet中value元素的score+=n
     * @param key redis中sortedset对应的key
     * @param n 增加n
     * @param value sortedset中的K
     * @return 增加后数值
     */
    Double zincrby(String key, double n, String value);

    /**
     * 以下为 Hash
     */

    /**
     * Map(String,String)和Map(byte[],byte[])
     *     转换成 Map(String,Object)
     *     注：Object支持String，会自动转换
     *  @param key redis中hash对应的key
     *  @param map 向hash中插入多个值
     *  @return 状态码
     */
    String hmset(String key, Map<String,Object> map);

    /**
     * 获取Hash中一个或多个元素value 包含Object和String
     *  @param key redis中hash对应的key
     *  @param mapkeys hash中的K
     *  @return 返回K对应的V
     */
    List<Object> hmget(String key,String... mapkeys);

    /**
     * 支持向HashMap插入Object
     *  @param key redis中hash对应的key
     *  @param mapkey hash中的K
     *  @param mapvalue hash中的K
     *  @return 状态码
     */
    Long hset(String key, String mapkey, Object mapvalue);

    /**
     * 获取全部元素k-V 包括Object和String
     *  @param key redis中hash对应的key
     *  @return hash中的K-V
     */
    Map<String,Object> hgetAll(String key);

    /**
     *
     * @param key redis中hash对应的key
     * @return hash中的全部K
     */
    Set<String> hkeys(String key);

    /**
     * 获取全部元素的value 包括Object和String
     *  @param key redis中hash对应的key
     *  @return hash中的全部V
     */
    List<Object> hvals(String key);

    /**
     *
     * @param key redis中hash对应的key
     * @param keys hash中的K
     * @return 状态码
     */
    Long hdel(String key, String... keys);

    /**
     *
     * @param key redis中hash对应的key
     * @return hash长度
     */
    Long hlen(String key);

    /**
     *
     * @param key redis中hash对应的key
     * @param mapkey hash中的K
     * @return 是否存在
     */
    boolean hexists(String key,String mapkey);
}
