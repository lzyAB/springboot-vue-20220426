package com.ilzya.springboot.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate redisTemplate;


    private static RedisUtil redisUtil ;

    @PostConstruct
    public void init() {
        redisUtil = this;
        redisUtil.redisTemplate = this.redisTemplate;
    }

    /**
     *给redis设置数据
     * @param key
     * @param value
     */
    public static void set(String key ,Object value){
        //通过链接获取能操作redis数据的对象
        ValueOperations<String,Object> vo = redisUtil.redisTemplate.opsForValue();
        vo.set(key,value,5, TimeUnit.MINUTES);//设置redis数据
    }

    /**
     * 获取数据
     * @param key
     * @return
     */
    public static Object get(String key){
        //通过链接获取能操作redis数据的对象
        ValueOperations<String,Object> vo = redisUtil.redisTemplate.opsForValue();
        return vo.get(key);//通过key获取value
    }

    public static void update(String key,Object value){
        ValueOperations<String,Object> vo = redisUtil.redisTemplate.opsForValue();
        vo.getAndSet(key,value);//根据key修改value,他会返回修改前的value
    }

    /**
     * 根据key删除数据
     * @param key
     */
    public static boolean delete(String key){
        return redisUtil.redisTemplate.delete(key);
    }

}
