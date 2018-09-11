package com;
/**
 * Created by szc on 2018/8/13.
 */

import com.config.CorsFilter;
import com.config.WebAppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan(value = {"com.controller","com.flkj","com.model"})
@SpringBootApplication
public class Application extends WebAppConfig
{
    public static void main( String[] args )
    {
        SpringApplication.run(Application.class, args);
    }
    @Bean
    public FilterRegistrationBean testFilterRegistration() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new CorsFilter());
        registration.addUrlPatterns("/*");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("corsFilter");
        registration.setOrder(1);
        return registration;
    }
}