# Ledis
![https://img.shields.io/apm/l/vim-mode](https://img.shields.io/apm/l/vim-mode)
[![https://img.shields.io/appveyor/build/gruntjs/grunt?label=1.2.release](https://img.shields.io/appveyor/build/gruntjs/grunt?label=1.2.release)](https://mvnrepository.com/artifact/com.github.cynaith/LightRedis)
[![https://img.shields.io/badge/%E4%B8%AA%E4%BA%BA%E4%B8%BB%E9%A1%B5-Cynaith-green](https://img.shields.io/badge/%E4%B8%AA%E4%BA%BA%E4%B8%BB%E9%A1%B5-Cynaith-green)](https://cynaith.github.io/)
> 可以对Redis直接存储对象

#### 使用方法 

[中文API文档]( https://apidoc.gitee.com/Cynaith/Ledis)

Maven
```xml
<dependency>
  <groupId>com.github.cynaith</groupId>
  <artifactId>LightRedis</artifactId>
  <version>1.2-RELEASE</version>
</dependency>
```

```java
Ledis ledis = new Ledis("127.0.0.1",6379);
```


#### 字符串
- `List<String> scan(String prefix,String suffix);`
    - 查询指定前缀后缀的value
- `List<String> mgets(String emptyValue,String... key);`
    - 获取多个key,当key不存在时，返回emptyValue
- `String setObj(String key,Object value);`
    - 插入对象
- `Object getObj(String key);`
    - 获取对象

#### List
- `Long lpush(String key,Object... objects);`
    - 从左边追加一个Object到list(key)中
- ` Long rpush(String key,Object... objects);`
    - 从右边追加一个Object到list(key)中
- ` Long lrem(String key,long count,Object value);`
    - 删除List(key)中的Object
- `String lset(String key,int index,Object object);`
    - 修改List(key)中的Object
- `List<Object> sortObj(String key);`
    - 对象排序
#### Set
- `Long sadd(String key, Object... objects);`
    - 向Set(key)中插入对象
- `Set<Object> smember(String key);`
    - 获取Set(key)中所有元素 
- `Long srem(String key, Object... objects);`
    - 删除key对应Set中的Object （也可以是String）
- `Object spop(String key);`
    - 随机弹出一个元素 （不区分对象字符串）
- `Long smove(String key1, String key2, Object val);`
    - 将元素从 key1(Set) 中移到 key2(Set) 中 （不区分对象字符串）
- `Set<Object> sinter(String key1,String key2);`
    - 获取集合key1和key2的交集 (不区分对象字符串)
- ` Set<Object> sunion(String key1,String key2);`
    - 获取集合key1和key2的并集 (不区分对象字符串)
- `Set<Object> sdiff(String key1,String key2);`
    - 获取集合key1和key2的差集 (不区分对象字符串)
#### Sorted Set
- `Map<String , Double> zrangeWithScores(String key, int i , int j);`
    - 修改原返回值 Map<Tuple> -> Map<String , Double>
- ` Map<String, Double> zrangByScoreWithScores(String key, double i , double j);`
    - 修改原返回值 Map<Tuple> -> Map<String , Double>
#### Hash
- `String hmset(String key, Map<String,Object> map);`
    - Map<String,String>和Map<byte[],byte[]>----> Map<String,Object>
        > 注：Object支持String，会自动转换
- `List<Object> hmget(String key,String... mapkeys);`
    - 获取Hash中一个或多个元素value 包含Object和String
- `Long hset(String key, String mapkey, Object mapvalue);`
    - 支持向HashMap插入Object
- `Map<String,Object> hgetAll(String key);`
    - 获取全部元素k-V 包括Object和String
- `List<Object> hvals(String key);`
    - 获取全部元素的value 包括Object和String



#### 1.0-RELEASE
- 发布基础版本
#### 1.1-RELEASE
- 修改for -> foreach
#### 1.2-RELEASE
- 修复依赖问题
- 修复scan的bug
#### 1.3-RELEASE
- 整合String类型的对象插入
- Object未序列化时，抛出运行时UnserizlizeException异常