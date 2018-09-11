package com.model;

import com.bean.AccountBalance;
import com.bean.UserInfo;
import com.flkj.service.ThirdParty;
import com.util.MyBatisUtil;
import com.util.Response;
import com.util.TokenUitil;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class User {
    @Autowired
    private ThirdParty thirdParty;
    public String test(){
        return thirdParty.test();
    }
    /**
     * 用户注册
     * @param response
     * @param name 名字
     * @param tel 电话号码
     * @param password 密码
     */
    public void Register(Response response, String name, String tel, String password){

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

            // 告诉第三方平台我们注册的用户
            int num = thirdParty.CreateUsers(tel,tel);
            userInfo.setUser_id(num);
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

    public void changePassword(Response response,String tel, String password){

        UserInfo userInfo = new UserInfo();
        // 设置用户token
        userInfo.setUser_token(TokenUitil.genetateToken());
        userInfo.setTel(tel);
        userInfo.setPassword(password);

        // 查询当前的总条数
        SqlSession session = MyBatisUtil.getSqlSession();
        try {

            // 插入用户数据info表
            session.update("com.bean.UserInfo.changePassword", userInfo);

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
    public boolean checkTelExist(String tel){
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
    public UserInfo checkUserExist(String tel,String password){
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
        return userInfo;
    }
    public UserInfo getUserInfo(int user_id){
        boolean _is = false;
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id(user_id);
        SqlSession session = MyBatisUtil.getSqlSession();
        try {
            userInfo =  session.selectOne("com.bean.UserInfo.getInfoByUserId", userInfo);
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
        return userInfo;
    }

    public List<AccountBalance> getBalance(int user_id,String coin){

        SqlSession session = MyBatisUtil.getSqlSession();
        List<AccountBalance> accountBalances =  null;
        HashMap hashMap = new HashMap();
        hashMap.put("user_id",user_id);
        if(!coin.equals("0")) {
            hashMap.put("coin", coin);
        }
        try {
           accountBalances =  session.selectList("com.bean.User.getBalance", hashMap);

        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            session.commit();
            session.close();
        }
        return accountBalances;
    }
}