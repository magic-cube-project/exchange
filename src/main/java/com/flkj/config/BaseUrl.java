package com.flkj.config;

/**
 * Created by szc on 2018/8/21.
 */
public class BaseUrl {
//    @Value("${com.flkj.exchange_coin.thirdParty}")
    private String thirdParty;

    public BaseUrl(){
        thirdParty = "https://leyou.586886.com/api/ThirdParty/";
    }
    public String getThirdParty() {
        return thirdParty;
    }
}
