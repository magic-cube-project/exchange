package com.controller;

import com.bean.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by szc on 2018/8/20.
 */
@RestController
@RequestMapping("/test")
public class testController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${com.dudu.name}")
    private  String name;
    @Value("${com.dudu.want}")
    private  String want;
    @RequestMapping("")
    public String hexo(){
        return name+","+want;
    }
    @RequestMapping("r1")
    public String r1(){
//        UserVo userVo = new UserVo();
//        userVo.setAddress("上海");
//        userVo.setName("jantent");
//        userVo.setAge(23);
//        valueOperations.set("test",userVo);
        stringRedisTemplate.opsForValue().set("aaa", "123");
        System.out.println(stringRedisTemplate.opsForValue().get("aaa"));
        return "r1";
    }

    @RequestMapping("r2")
    public String r2(){
        UserVo userVo = new UserVo();
        userVo.setAddress("上海");
        userVo.setName("jantent");
        userVo.setAge(23);
        redisTemplate.opsForValue().set("user1",userVo);
        System.out.println(redisTemplate.opsForValue().get("user1"));
        return "r2";
    }

}
