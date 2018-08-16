package com.controller;

import com.model.User;
import com.model.VerificationCode;
import com.util.AccountValidatorUtil;
import com.util.Response;
import com.util.ResponseUtil;
import com.util.TokenUitil;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * 用户相关操作
 * Created by szc on 2018/8/13.
 */

@RestController
@RequestMapping("/user")
@EnableRedisHttpSession
public class UserController{
    /**
     * 用户登录接口
     * @return
     */
    @RequestMapping(value = "login",method = RequestMethod.POST)
    String login(@RequestParam(value = "tel", required = true) String tel,
                 @RequestParam(value = "password", required = false) String password){
        Response response = ResponseUtil.ceateRespone();
        password = TokenUitil.mergeToken(password,"MCC");
        if(!User.checkUserExist(tel,password)){
            response.error(-10012,"用户不存在或密码不正确");
        } else {
           // 此处需要建立用户session
        }
        // 返回数据
        return response.toJSON();
    }

    /**
     * 用户注册接口
     * @return
     */
    @RequestMapping(value = "register",method = RequestMethod.POST)
    String register(@RequestParam(value = "tel", required = true) String tel,
                    @RequestParam(value = "code", required = true) int code,
                    @RequestParam(value = "name", required = true) String name,
                    @RequestParam(value = "password", required = false) String password){

        if(password==null){
            password = "1234567890";
        }
        Response response = ResponseUtil.ceateRespone();
        VerificationCode.checkCode(response,tel,code);
        if(!response.isSuccess()){
            return response.toJSON();
        }

        // 判断手机号是否存在
        if(User.checkTelExist(tel)){
            response.error(-10009,"手机号已存在");
            return response.toJSON();
        }
        // 对密码的字符串进行加密
        password = TokenUitil.mergeToken(password,"MCC");
        User.Register(response,name,tel,password);
        return response.toJSON();
    }

    /**
     * 用户获取验证码
     * @return
     */
    @RequestMapping(value = "verification_code",method = RequestMethod.POST)
    String verificationCode(@RequestParam(value = "tel", required = true) String tel){
        Response response = ResponseUtil.ceateRespone();

        if(AccountValidatorUtil.isMobile(tel)){
            // 暂定当前的验证码666666
            VerificationCode.saveCode(response,tel,666666);
        } else {
            response.error(-10014,"手机号不合法");
        }
        return response.toJSON();
    }
    @RequestMapping("test")
    String test(HttpSession session){
        session.setAttribute("test", "123456");
        return "test";
    }
    @RequestMapping("test2")
    String test2(HttpSession session){
        String res = (String) session.getAttribute("test");
        return "test2"+res;
    }
}
