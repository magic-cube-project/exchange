package com.controller;

import com.bean.AccountBalance;
import com.bean.UserInfo;
import com.bean.UserSession;
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
import java.util.HashMap;
import java.util.List;

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
    @RequestMapping(value = "login")
    String login(@RequestParam(value = "tel", required = true) String tel,
                 @RequestParam(value = "password", required = false) String password,HttpSession session){
        Response response = ResponseUtil.ceateRespone();
        password = TokenUitil.mergeToken(password,"MCC");
        UserInfo userInfo = User.checkUserExist(tel,password);
        if(userInfo==null){
            response.error(-10012,"用户不存在或密码不正确");
        } else {
           // 此处需要建立用户session
            UserSession userSession= new UserSession();
            System.out.println(userInfo.getUser_id());
            userSession.setUser_id(userInfo.getUser_id());
            session.setAttribute("user", userSession);
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
     * 修改密码
     * @param tel
     * @param code
     * @param password
     * @return
     */
    @RequestMapping(value = "changePassword",method = RequestMethod.POST)
    String changePassword(@RequestParam(value = "tel", required = true) String tel,
                          @RequestParam(value = "code", required = true) int code,
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
        if(!User.checkTelExist(tel)){
            response.error(-10013,"当前用户不存在");
            return response.toJSON();
        }
        // 对密码的字符串进行加密
        password = TokenUitil.mergeToken(password,"MCC");

        User.changePassword(response,tel,password);
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
            // 获取当前的验证码
            VerificationCode.saveCode(response,tel,VerificationCode.createCode());
        } else {
            response.error(-10014,"手机号不合法");
        }
        return response.toJSON();
    }

    /**
     * 用户登出
     * @param session
     * @return
     */
    @RequestMapping("logout")
    String logout(HttpSession session){
        Response response = ResponseUtil.ceateRespone();

        UserSession userSession = (UserSession) session.getAttribute("user");
        if(userSession==null){
            response.error(-10015,"用户未登录");
            return response.toJSON();
        }

        try{
            session.removeAttribute("user");
        } finally {
            return response.toJSON();
        }
    }

    /**
     * 获取个人的用户中心
     * @param session
     * @return
     */
    @RequestMapping("getInfo")
    String getInfo(HttpSession session){
        Response response = ResponseUtil.ceateRespone();
        UserSession userSession = (UserSession) session.getAttribute("user");
        if(userSession==null){
            response.error(-10015,"用户未登录");
            return response.toJSON();
        }

        UserInfo userInfo = User.getUserInfo(userSession.getUser_id());

        HashMap map = new HashMap();
        // 设置请求返回内容
        map.put("name",userInfo.getName());
        map.put("lv",userInfo.getLv());
        map.put("exp",userInfo.getExp());
        map.put("headimg",userInfo.getHeadimg());
        System.out.println(userInfo.getHeadimg());
        map.put("user_id",userInfo.getUser_id());
        map.put("tel",userInfo.getTel());
        response.setResult(map);

        return response.toJSON();
    }

    @RequestMapping("balance")
    String balance(HttpSession session,@RequestParam(value = "coin", required = false,defaultValue = "0") String coin){
        Response response = ResponseUtil.ceateRespone();
        UserSession userSession = (UserSession) session.getAttribute("user");
        if(userSession==null){
            response.error(-10015,"用户未登录");
            return response.toJSON();
        }
        UserInfo userInfo = User.getUserInfo(userSession.getUser_id());
        int user_id = userInfo.getUser_id();
        System.out.println("用户号"+user_id);
        // 如果coin货币请求all 请求所有的货币

        List<AccountBalance> accountBalances = User.getBalance(user_id,coin);
        HashMap map = new HashMap();
        map.put("list",accountBalances);
        response.setResult(map);
        return response.toJSON();
    }

}
