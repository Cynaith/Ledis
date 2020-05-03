package com.github.lightredis.jedis;

import com.github.lightredis.util.JedisPool;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Method;

/**
 * @USER: lynn
 * @DATE: 2020/5/3
 **/
public class LedisInterceptor implements MethodInterceptor {

    private Ledis ledis;

    private Jedis jedis;

    public LedisInterceptor(){
        jedis = JedisPool.getJedis();
        ledis = new Ledis(jedis);
    }

    public Ledis getLedis(){
        return ledis;
    }

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        method.invoke(ledis,objects);
        jedis.close();
        return null;
    }
}
