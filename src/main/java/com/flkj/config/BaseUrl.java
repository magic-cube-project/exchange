package com.flkj.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by szc on 2018/8/21.
 */

@Component
public class BaseUrl {
    @Value("${com.thirdParty}")
    private String thirdParty;
    @Value("${com.tick}")
    private String tick;
    public String getThirdParty() {
        return thirdParty;
    }

    public String getTick() {
        return tick;
    }
}
