package com.flkj.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.bean.AppSendcoinList;
import com.flkj.bean.ThirdPartyToken;
import com.flkj.config.BaseUrl;
import com.squareup.okhttp.*;
import com.util.TokenUitil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by szc on 2018/8/21.
 */
@Service
public class ThirdParty {

    public static ThirdPartyToken thirdPartyToken;

    @Autowired
    public BaseUrl baseUrl;

    public String test(){
        return baseUrl.getTick();
    }

    /**
     * 获取第三方的秘钥
     */
    public  ThirdPartyToken getToken(){
        if(thirdPartyToken==null || new Date().getTime()>=thirdPartyToken.getExpires_time())
        {
            refreshToken();
        }
        return thirdPartyToken;
    }

    /**
     * 刷新秘钥的token
     */
    public void refreshToken(){
        HashMap resmap = new HashMap();
        resmap.put("appId","leyou");
        resmap.put("appSecret","1eYou@8*93");
        resmap.put("scope","partner");

        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(resmap));
        Request request = new Request.Builder()
                .url(baseUrl.getThirdParty()+"GetAccessToken")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Cache-Control", "no-cache")
                .build();
        try {
            Response response = client.newCall(request).execute();
            String res =  response.body().string();
            String token = JSON.parseObject(res,new TypeReference<HashMap<String,String>>(){}).get("result");
            thirdPartyToken = new ThirdPartyToken();
            thirdPartyToken.setToken(token);
            thirdPartyToken.setExpires_time(20*60*1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 告诉上海第三方交易所, 这里创建了一个用户
     * @param username
     * @param cellphone
     * @return
     */
    public int CreateUsers(String username,String cellphone){
        String token = getToken().getToken();  //当前的秘钥
        OkHttpClient client = new OkHttpClient();
        int user_id = 0;

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "[\n  {\n    \"username\": \""+ TokenUitil.genetateToken()+"\",\n    \"password\": \"S%tring8\",\n    \"cellphone\": \""+TokenUitil.genetateToken()+"\",\n  }\n]");
        Request request = new Request.Builder()
                .url(baseUrl.getThirdParty()+"CreateUsers")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer "+token)
                .addHeader("Cache-Control", "no-cache")
                .build();
        try {
            Response response = client.newCall(request).execute();
            String res =  response.body().string();
            System.out.println(res);
            user_id = (int) JSON.parseObject(String.valueOf(JSON.parseArray(String.valueOf(JSON.parseObject(res).get("result"))).get(0))).get("userId");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user_id;
    }

    public JSONObject Deposit(AppSendcoinList appSendcoinList){
        String token = getToken().getToken();  //当前的秘钥
        boolean _is = false;
        OkHttpClient client = new OkHttpClient();
        String res = "";

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType,
                "[\n{\n\"id\": "+appSendcoinList.getId()+",\n\"coin\": \""+appSendcoinList.getCoin()+"\", \n\"amount\": "+appSendcoinList.getAmount()+", \n\"tag\": \""+appSendcoinList.getTag()+"\", \n\"description\": \""+appSendcoinList.getDescription()+"\", \n\"userId\": "+appSendcoinList.getUser_id()+" \n}\n]");        Request request = new Request.Builder()
                .url(baseUrl.getThirdParty()+"Deposit?appid=leyou")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer "+token)
                .addHeader("Cache-Control", "no-cache")
                .build();
        try {
            Response response = client.newCall(request).execute();
            res =  response.body().string();
            System.out.println(res);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return JSON.parseObject(String.valueOf(JSON.parseArray(String.valueOf(JSON.parseObject(res).get("result"))).get(0)));
    }

    public  JSONObject getLatestMarketDetail(String market){

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(baseUrl.getTick()+"getLatestMarketDetail?market="+market)
                .get()
                .addHeader("Content-Type", "application/json")
                .addHeader("Cache-Control", "no-cache")
                .build();
        try {

            Response response = client.newCall(request).execute();
            String responsesStr = response.body().string();
            System.out.println(responsesStr);

           JSONObject responseData = JSON.parseObject(responsesStr);

            if ((boolean)responseData.get("Success") != true) {
                throw new IOException();
            } else {
                System.out.println("-----------------------------");
                System.out.println(responseData.get("Result"));
                System.out.println("-----------------------------");
                return (JSONObject) responseData.get("Result");
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


    }
}
