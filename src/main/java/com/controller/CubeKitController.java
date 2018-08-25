package com.controller;

import com.bean.AccessToken;
import com.bean.AccountBalance;
import com.bean.AppUserLink;
import com.model.OpenUser;
import com.model.Sendcoin;
import com.model.User;
import com.util.Response;
import com.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * Created by szc on 2018/8/21.
 */
@RestController
@RequestMapping("/cubekit")
/**
 * 交易所接口
 */
public class CubeKitController {
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("sendCoin")
    String sendCoin(@RequestParam(value = "access_token", required = true) String access_token,
                    @RequestParam(value = "openid", required = true) String openid,
                    @RequestParam(value = "coin", required = true) String coin,
                    @RequestParam(value = "amount", required = true) int amount,
                    @RequestParam(value = "tag", required = true) String tag,
                    @RequestParam(value = "description", required = true) String description
                  ){
        Response response = ResponseUtil.ceateRespone();

        AccessToken accessTokenBean = (AccessToken) redisTemplate.opsForValue().get("access_token:"+access_token);

        if(accessTokenBean==null){
            response.error(-20002,"应用未授权");
            return response.toJSON();
        }
        AppUserLink appUserLink = OpenUser.selectByopenid(openid);

        if(appUserLink==null){
            response.error(-20003,"授权用户不存在");
            return response.toJSON();
        }
        // 插入发放记录
        Sendcoin.add(appUserLink.getApp_id(),appUserLink.getUser_id(),amount,coin,description,tag,response);
        return response.toJSON();
    }

    @RequestMapping("balance")
    String balance(@RequestParam(value = "access_token", required = true) String access_token,
                   @RequestParam(value = "openid", required = true) String openid,
                   @RequestParam(value = "coin", required = true) String coin){
        Response response = ResponseUtil.ceateRespone();

        AccessToken accessTokenBean = (AccessToken) redisTemplate.opsForValue().get("access_token:"+access_token);

        if(accessTokenBean==null){
            response.error(-20002,"应用未授权");
            return response.toJSON();
        }
        AppUserLink appUserLink = OpenUser.selectByopenid(openid);

        if(appUserLink==null){
            response.error(-20003,"授权用户不存在");
            return response.toJSON();
        }
        // 需要查询用户的user_id
        int user_id = appUserLink.getUser_id();


        List<AccountBalance> accountBalances = User.getBalance(user_id,coin);
        HashMap map = new HashMap();
        map.put("list",accountBalances);
        response.setResult(map);
        return response.toJSON();
    }

}