package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Created by szc on 2018/8/21.
 */
public class test3 {
    @Autowired
    private static StringRedisTemplate stringRedisTemplate;
    public static void main(String[] args){
        stringRedisTemplate.opsForValue().set("test", "111");

    }
}
