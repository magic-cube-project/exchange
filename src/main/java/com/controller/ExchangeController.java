package com.controller;

import com.bean.AccessToken;
import com.bean.AppInfo;
import com.bean.AppUserLink;
import com.model.App;
import com.model.OpenUser;
import com.util.Response;
import com.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 交易所相关接口
 * Created by szc on 2018/8/16.
 */
@RestController
@RequestMapping("/exchange")
public class ExchangeController {
    @Autowired
    private RedisTemplate redisTemplate;
    @RequestMapping("")
    String index(){
        return "hello world";
    }

    /**
     * 获取第三方应用信息
     * @param access_token
     * @return
     */
    @RequestMapping("getAppInfo")
    String getAppInfo(@RequestParam(value = "access_token", required = true) String access_token){
        // 创建一个业务请求头
        Response response = ResponseUtil.ceateRespone();
        // 检测access_token 是否是否合法
        AccessToken accessTokenBean = (AccessToken) redisTemplate.opsForValue().get("access_token:"+access_token);

        if(accessTokenBean==null){
            response.error(-20003,"当前令牌不存在");
            return response.toJSON();
        }
        int app_id = accessTokenBean.getApp_id(); // 获取应用的app_id

        AppInfo appInfo = App.getInfo(app_id);
        response.setResult("info",appInfo);

        return response.toJSON();
    }
}
