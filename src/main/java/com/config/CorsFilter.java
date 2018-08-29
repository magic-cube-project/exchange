package com.config;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szc on 2018/8/29.
 */
@WebFilter
public class CorsFilter implements Filter {
    // 日志对象
//    private static Logger logger = LoggerFactory.getLogger(CorsFilter.class);

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        String url = req.getRemoteHost();
        System.out.println(url);
        System.out.println(req.getScheme());
        System.out.println(req.getRemoteAddr());
        response.setHeader("Access-Control-Allow-Origin", req.getScheme()+"://"+req.getRemoteAddr());
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        response.setHeader("Access-Control-Allow-Credentials","true");
        chain.doFilter(req, res);
    }

    public void init(FilterConfig filterConfig) {
        // something init
    }

    public void destroy() {
        // destroy something
    }
}
