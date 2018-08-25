package com.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 交易所相关接口
 * Created by szc on 2018/8/16.
 */
@RestController
@RequestMapping("/exchange")
public class ExchangeController {
    @RequestMapping("")
    String index(){
        return "hello world";
    }
}
