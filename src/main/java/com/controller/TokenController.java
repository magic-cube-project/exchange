package com.controller;

import com.bean.AccessSecretInfo;
import com.model.AccessSecret;
import com.model.AccessToken;
import com.util.Response;
import com.util.ResponseUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by szc on 2018/8/13.
 */
@RestController
@RequestMapping("/token")
public class TokenController {
    /**
     * 请求参数说明 token?grant_type=client_credential&appid=APPID&secret=APPSECRET
     * @return
     */
    @RequestMapping("")
    String index(@RequestParam(value = "appid", required = true) String appid, @RequestParam(value = "grant_type", required = true) String grant_type,@RequestParam(value = "secret", required = true) String secret){

        Response response = ResponseUtil.ceateRespone();
        AccessSecretInfo accessSecretInfo = AccessSecret.checkSecret(response,appid,secret);
        if(accessSecretInfo==null){
            return response.toJSON();
        }
        response.setResult(AccessToken.createAccessToken(accessSecretInfo.getId()));
        return response.toJSON();
    }

    @RequestMapping("create")
    String create(){
        return genetateToken();
    }
    @RequestMapping("merge")
    String merge(){

        String a = "596024a5b5fa7b8e0cbcaad481744be8";
        String b = "e8c59220c888f46e12fe8f10a6c5bb6e";

        String res =  mergeToken(a,b);
        System.out.printf(String.valueOf(res.length()));
        return res;
    }
    //随机数发生器
    public static String genetateToken(){
        int randomNum = (int)(1+Math.random()*1000);  //随机 1,1000 之间的随机数
        String token = System.currentTimeMillis()+randomNum+"";//获得毫秒数加随机数
        String tokenMd5=MD5(token);
        return tokenMd5;
    }
    /**
     * 合并token
     * @param tokenA
     * @param tokenB
     * @return
     */
    public static String mergeToken(String tokenA,String tokenB){
        String token  = tokenA+tokenB;
        String tokenMd5=MD5(token);
        return tokenMd5;
    }
    /**
     * 对一个字符串得到MD5
     * @param sourceStr
     * @return
     */
    private static String MD5(String sourceStr) {
        String result = "";

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        return result;
    }

}
