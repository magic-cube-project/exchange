package com.controller;

import com.bean.*;
import com.model.AccessSecret;
import com.model.OpenUser;
import com.model.User;
import com.util.Response;
import com.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * Created by szc on 2018/8/17.
 */
@RestController
@EnableRedisHttpSession
@RequestMapping("/oauth2")
public class Oauth2Controller {
    @Autowired
    private RedisTemplate redisTemplate;

    //通过code换取网页授权access_token,这里测试展示使用的code为用户的user_id
    @RequestMapping(value = "access_token")
    String access_token(@RequestParam(value = "appid", required = true) String appid, @RequestParam(value = "grant_type", required = true) String grant_type, @RequestParam(value = "secret", required = true) String secret,@RequestParam(value = "code", required = true) String code){

        Response response = ResponseUtil.ceateRespone();
        AccessSecretInfo accessSecretInfo = AccessSecret.checkSecret(response,appid,secret);
        if(accessSecretInfo==null){
            return response.toJSON();
        }

        int app_id = accessSecretInfo.getId();  //应用app_id
        int user_id = Integer.parseInt(code);  // 用户user_id

        AppUserLink openidInfo = OpenUser.select(app_id,user_id);
        if(openidInfo==null){
            openidInfo = OpenUser.add(app_id,user_id);
        }
        // 返回传入openid
        HashMap map = new HashMap();
        map.put("openid",openidInfo.getOpenid());

        response.setResult(map);
        //返回数据请求头
        return response.toJSON();
    }

    @RequestMapping(value = "authorization")
    String authorization(HttpSession session,@RequestParam(value = "access_token", required = true) String access_token){
        Response response = ResponseUtil.ceateRespone();
        UserSession userSession = (UserSession) session.getAttribute("user");
        if(userSession==null){
            response.error(-10015,"用户未登录");
            return response.toJSON();
        }
        System.out.println(userSession.getUser_id());
        UserInfo userInfo = User.getUserInfo(userSession.getUser_id());

        int user_id = userInfo.getUser_id();

        // 获取应用access_token
        AccessToken accessTokenBean = (AccessToken) redisTemplate.opsForValue().get("access_token:"+access_token);

        if(accessTokenBean==null){
            response.error(-20002,"应用未授权");
            return response.toJSON();
        }

        int app_id = accessTokenBean.getApp_id();

        AppUserLink openidInfo = OpenUser.select(app_id,user_id);
        if(openidInfo==null){
            openidInfo = OpenUser.add(app_id,user_id);
        }
        // 返回传入openid
        HashMap map = new HashMap();
        map.put("openid",openidInfo.getOpenid());

        response.setResult(map);

        return response.toJSON();
    }

}
