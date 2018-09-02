package com.controller;

import com.bean.UserVo;
import com.bean.VerificationCode;
import com.util.MyBatisUtil;
import com.util.Response;
import com.util.ResponseUtil;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

/**
 * Created by szc on 2018/8/20.
 */
@RestController
@EnableRedisHttpSession
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

    @RequestMapping("r3")
    public String r3(HttpSession session){
        session.setAttribute("test_session", "test_session");
        return "r3";
    }

    @RequestMapping("r4")
    public String r4(HttpSession session){
        return (String) session.getAttribute("test_session");
    }
    @RequestMapping("r5")
    public String r5(){
        Response response = ResponseUtil.ceateRespone();
        SqlSession session = MyBatisUtil.getSqlSession();
        List<VerificationCode> list = null;

        try {
            list =  session.selectList("com.bean.VerificationCode.all");
        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            session.commit();
            session.close();
        }

        response.setResult("list",list);

        return response.toJSON();
    }

}
