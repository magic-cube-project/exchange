package com.controller;

import com.alibaba.fastjson.JSONObject;
import com.bean.AccessToken;
import com.bean.AccountBalance;
import com.bean.AppUserLink;
import com.flkj.service.ThirdParty;
import com.model.OpenUser;
import com.model.Sendcoin;
import com.model.User;
import com.util.Response;
import com.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
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

    @Autowired
    private ThirdParty thirdParty;

    @Autowired
    private Sendcoin sendcoin;

    @Autowired
    private User user;



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
        sendcoin.add(appUserLink.getApp_id(),appUserLink.getUser_id(),amount,coin,description,tag,response);
        return response.toJSON();
    }


    /**
     * 内部的发币接口
     * @param jsonParam
     * @return
     */
    @RequestMapping("sendCoinApi")
    String sendCoinAPi(@RequestBody JSONObject jsonParam){
        Response response = ResponseUtil.ceateRespone();
        System.out.println(jsonParam.toJSONString());
        // 插入发放记录
        int app_id = (int) jsonParam.get("app_id");
        int user_id = (int) jsonParam.get("user_id");
        int amount = (int)jsonParam.get("amount");
        String coin = (String) jsonParam.get("coin");
        String description = (String) jsonParam.get("description");
        String tag = (String) jsonParam.get("tag");
        sendcoin.add(app_id,user_id,amount,coin,description,tag,response);
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

        List<AccountBalance> accountBalances = user.getBalance(user_id,coin);
        HashMap map = new HashMap();
        map.put("list",accountBalances);
        response.setResult(map);
        return response.toJSON();
    }

    /**
     * 获取最近交易对的信息
     * @param market
     * @return
     */
    @RequestMapping("getLatestMarket")
    String getLatestMarket(@RequestParam(value = "market", required = true) String market){
        // 创建一个业务请求头
        Response response = ResponseUtil.ceateRespone();

        JSONObject rep = thirdParty.getLatestMarketDetail(market);
        if(rep!=null){
            response.setResult(rep);
        } else {
            response.error(-30001,"交易对不存在");
        }
        return response.toJSON();
    }
}
