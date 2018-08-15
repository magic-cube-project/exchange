package com.model;

import com.bean.UserInfo;
import com.util.MyBatisUtil;
import com.util.Response;
import com.util.TokenUitil;
import org.apache.ibatis.session.SqlSession;

import java.util.Date;

public class User {
    /**
     * 用户注册
     * @param response
     * @param name 名字
     * @param tel 电话号码
     * @param password 密码
     */
    public static void Register(Response response, String name, String tel, String password){

        UserInfo userInfo = new UserInfo();
        // 设置用户token
        userInfo.setUser_token(TokenUitil.genetateToken());
        userInfo.setName(name);
        userInfo.setTel(tel);
        userInfo.setPassword(password);
        // 设置用户创建时间
        userInfo.setCreate_time(new Date().getTime());

        // 查询当前的总条数
        SqlSession session = MyBatisUtil.getSqlSession();
        try {

            int num = (int) session.selectList("com.bean.UserInfo.getCount").get(0);
            userInfo.setUser_id(num+1);
            // 插入用户数据info表
            session.insert("com.bean.UserInfo.add", userInfo);

        } catch (Exception e){
            e.printStackTrace();
            session.rollback();
            response.error(-10000,"数据库出现错误");
        }
        session.commit();
        session.close();
    }

    /**
     * 检查电话号码是否存在
     * @param tel
     * @return
     */
    public static boolean checkTelExist(String tel){
        UserInfo userInfo = new UserInfo();
        userInfo.setTel(tel);
        SqlSession session = MyBatisUtil.getSqlSession();
        boolean _is = new Boolean(false);
        try {
          userInfo =  session.selectOne("com.bean.UserInfo.checkTelExist", userInfo);
          if(userInfo==null){
              _is = false;
          } else {
              _is = true;
          }
        } catch (Exception e){
            e.printStackTrace();
            session.rollback();
        }
        finally {
            session.commit();
            session.close();
        }
        return _is;

    }

    /**
     * 判断用户是否存在
     * @return
     */
    public static boolean checkUserExist(String tel,String password){
        boolean _is = false;
        UserInfo userInfo = new UserInfo();
        userInfo.setTel(tel);
        userInfo.setPassword(password);
        SqlSession session = MyBatisUtil.getSqlSession();
        try {
            userInfo =  session.selectOne("com.bean.UserInfo.checkExist", userInfo);
            if(userInfo==null){
                _is = false;
            } else {
                _is = true;
            }
        } catch (Exception e){
            e.printStackTrace();
            session.rollback();
        }
        finally {
            session.commit();
            session.close();
        }

        return _is;
    }
}