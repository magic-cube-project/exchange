package com.util;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;

/**
 * 返回数据请求报的数据
 * Created by szc on 2018/8/13.
 */

public class ResponseUtil {
    /**
     * 请求成功的发送,请求成功的标志码始终为10000
     * @return
     */
    public static String success(HashMap map){
        Response response = new Response(0,true);
        response.setResult(map);
        return JSON.toJSONString(response);
    }
    public static Response ceateRespone(){
        return new Response(0,true);
    }
}
