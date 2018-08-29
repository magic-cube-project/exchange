package com.config; /**
 * 注册号拦截器
 * Created by szc on 2018/8/13.
 */

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
public class WebAppConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册自定义拦截器，添加拦截路径和排除拦截路径
//        registry.addInterceptor(new InterceptorConfig()).addPathPatterns("api/path/**").excludePathPatterns("api/path/login");
        registry.addInterceptor(new InterceptorConfig());
    }
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        System.out.printf("跨域设置");
//        registry.addMapping("*")
//                .allowedOrigins("*")
//                .allowedMethods("*")
//                .allowCredentials(false).maxAge(3600);
//    }
}
