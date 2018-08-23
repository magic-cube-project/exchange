package com.controller;

import com.bean.AccessSecretInfo;
import com.model.AccessSecret;
import com.model.AccessToken;
import com.util.Response;
import com.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by szc on 2018/8/13.
 */
@RestController
@RequestMapping("/token")
public class TokenController {

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 请求参数说明 token?grant_type=client_credential&appid=APPID&secret=APPSECRET
     * @return
     */
    @RequestMapping("")
    String index(@RequestParam(value = "appid", required = true) String appid, @RequestParam(value = "grant_type", required = true) String grant_type,@RequestParam(value = "secret", required = true) String secret){

        Response response = ResponseUtil.ceateRespone();
        // 处理商户是否存在
        AccessSecretInfo accessSecretInfo = AccessSecret.checkSecret(response,appid,secret);
        if(accessSecretInfo==null){
            return response.toJSON();
        }
        HashMap resMap = AccessToken.createAccessToken(accessSecretInfo.getId());
        com.bean.AccessToken accessToken = new com.bean.AccessToken();
        // 设置redis缓存
        accessToken.setApp_id(accessSecretInfo.getId());
        accessToken.setAccess_token(String.valueOf(resMap.get("access_token")));
        accessToken.setExpires_time(String.valueOf(resMap.get("expires_time")));
        long expires_in = 7200;  // 设置过期时间 单位:秒
        redisTemplate.opsForValue().set("app_id:"+accessSecretInfo.getId(),accessToken);
        redisTemplate.opsForValue().set("access_token:"+resMap.get("access_token"),accessToken);

        redisTemplate.expire("app_id:"+accessSecretInfo.getId(),expires_in, TimeUnit.SECONDS);
        redisTemplate.expire("access_token:"+resMap.get("access_token"),expires_in, TimeUnit.SECONDS);

        response.setResult(resMap);
        return response.toJSON();
    }

    @RequestMapping("test")
    String y(){
        System.out.println(redisTemplate.opsForValue().get("app_id:1"));
        com.bean.AccessToken accessToken = (com.bean.AccessToken) redisTemplate.opsForValue().get("app_id:1");
        return accessToken.getAccess_token();
    }

}
