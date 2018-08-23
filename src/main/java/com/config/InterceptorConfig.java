package com.config;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by szc on 2018/8/13.
 */
public class InterceptorConfig  implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        HttpSession session = httpServletRequest.getSession();

//        System.out.printf("---------------------开始进入请求地址拦截----------------------------\n");

        try {
            String  yy= (String)session.getAttribute("test");
            if(yy!=null){
               System.out.println(yy);
            }
        } catch (Exception e){

        }
//        System.out.printf("---------------------地址拦截完成----------------------------\n");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
       // System.out.printf("--------------处理请求完成后视图渲染之前的处理操作---------------\n");

    }


    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        //System.out.printf("---------------视图渲染之后的操作-------------------------0\n");
    }
}
