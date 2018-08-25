package com.flkj.bean;

import java.util.Date;

/**
 * Created by szc on 2018/8/21.
 */
public class ThirdPartyToken {
    private String token;
    private long expires_time; // 过期时间

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpires_time() {
        return expires_time;
    }

    /**
     * 设置滚起时间
     * @param expires_time
     */
    public void setExpires_time(long expires_time) {
        this.expires_time = expires_time+new Date().getTime();
    }
}