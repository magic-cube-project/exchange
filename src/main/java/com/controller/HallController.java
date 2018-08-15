package com.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by szc on 2018/8/13.
 */
@RestController
@RequestMapping("/hall")
public class HallController {
    @RequestMapping("")
    String getTime(){
        System.out.printf("this is a hall action\n");
        return "hall";
    }
}
