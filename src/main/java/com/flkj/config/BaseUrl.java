package com.flkj.config;

/**
 * Created by szc on 2018/8/21.
 */
public class BaseUrl {
//    @Value("${com.flkj.exchange_coin.thirdParty}")
    private String thirdParty;
    private String tick;

    public BaseUrl(){
        thirdParty = "https://leyou.586886.com/api/ThirdParty/";
        tick = "https://leyou.586886.com/api/tick/";
    }
    public String getThirdParty() {
        return thirdParty;
    }
    public String getTick(){
        return tick;
    }
}
