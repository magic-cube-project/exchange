package com.controller;

import com.bean.AccessSecretInfo;
import com.bean.User;
import com.util.MyBatisUtil;
import com.util.ResponseUtil;
import org.apache.ibatis.session.SqlSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * Created by szc on 2018/8/13.
 */
@RestController
@RequestMapping("/db")
public class DbController {
    @RequestMapping("")
    String getTime() {
        System.out.printf("this is a db action\n");
        return "db";
    }

    @RequestMapping(value= "selectone")
    String selectone() {
        int id = 1;
        SqlSession session = MyBatisUtil.getSqlSession();
        try {
            User user = (User) session.selectOne(
                    "com.bean.User.GetUserByID", id);
            if (user != null) {
                String userInfo = "名字：" + user.getName() + ", 所属部门：" + user.getDept() + ", 主页：" + user.getWebsite();
                // 设置用户uid字段
                user.setUid();
                HashMap map = new HashMap();
                map.put("user",user);

                return ResponseUtil.success(map);

            }
        } finally {
            session.close();
        }
        return "用户不存在";
    }
    @RequestMapping("test")
    String test(){
        int [] num=new int[10];
        num[0]=1;
        num[1]=2;
        num[2]=3;
        HashMap map = new HashMap();
        map.put("numArr",num);
        map.put("ppp","123456");
        return ResponseUtil.success(map);
    }
    @RequestMapping("test1")
    String test1(){
        String appid = "MCC_12322";
        SqlSession session = MyBatisUtil.getSqlSession();
        try {
            AccessSecretInfo accessSecretInfo = (AccessSecretInfo) session.selectOne(
                    "com.bean.AccessSecretInfo.getAccessByAppid", appid);
            if (accessSecretInfo != null) {

                HashMap map = new HashMap();
                map.put("acc",accessSecretInfo);

                return ResponseUtil.success(map);

            }
        } finally {
            session.close();
        }
        return "用户不存在";
    }
}
