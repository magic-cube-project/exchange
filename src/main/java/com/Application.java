package com;
/**
 * Created by szc on 2018/8/13.
 */

import com.config.WebAppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan(value = {"com.controller"})
@SpringBootApplication
public class Application extends WebAppConfig
{
    public static void main( String[] args )
    {
        SpringApplication.run(Application.class, args);
    }
}