package com.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by szc on 2018/8/16.
 */
@RestController
@RequestMapping("/")
public class IndexController {

    @RequestMapping("")
    String index() {
        return "<h2>welcome to exchange</h2>";
    }

}
