package com.github.lightredis.jedis;

import com.github.lightredis.util.JedisPool;
import net.sf.cglib.proxy.Enhancer;
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

    public LedisInterceptor(Ledis ledis){

        this.ledis = ledis;
    }

    public Ledis getLedis(){
        return ledis;
    }

    public Object getObject() {
        Enhancer enhancer = new Enhancer();
        Class<?> clazz = this.ledis.getClass();
        enhancer.setCallback(this);
        enhancer.setClassLoader(clazz.getClassLoader());
        enhancer.setInterfaces(clazz.getInterfaces());
        enhancer.setSuperclass(clazz);
        return enhancer.create();
    }

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        jedis = JedisPool.getJedis();
        ledis.setJedis(jedis);
        method.invoke(ledis,objects);
        jedis.close();
        return null;
    }
}
