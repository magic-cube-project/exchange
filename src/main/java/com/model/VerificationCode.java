package com.model;

import com.util.MyBatisUtil;
import com.util.Response;
import org.apache.ibatis.session.SqlSession;

import java.util.Date;

/**
 * Created by szc on 2018/8/14.
 */
public class VerificationCode {
    public static long expires_in = 7200000;
    /**
     * 保存验证码
     */
    public static void saveCode(Response response, String tel, int code){
        com.bean.VerificationCode verificationCode = new com.bean.VerificationCode();

        verificationCode.setTel(tel);
        verificationCode.setCode(code);
        verificationCode.setCreate_time(new Date().getTime());
        verificationCode.setExpires_time(new Date().getTime()+expires_in);
        // 在数据库中保存验证码
        SqlSession session = MyBatisUtil.getSqlSession();
        try {
            session.insert("com.bean.VerificationCode.add", verificationCode);
        } catch (Exception e){
            e.printStackTrace();
            session.rollback();
            response.error(-10000,"数据库出现错误");
        }
        session.commit();
        session.close();
    }

    /**
     * 检查验证码是否有效
     * @param response
     * @param tel
     * @param code
     */
    public static void checkCode(Response response, String tel, int code){
        com.bean.VerificationCode verificationCode = new com.bean.VerificationCode();
        verificationCode.setTel(tel);
        verificationCode.setCode(code);
        verificationCode.setExpires_time(new Date().getTime());
        verificationCode.setState(1);
        // 在数据库中保存验证码
        SqlSession session = MyBatisUtil.getSqlSession();
        try {
           verificationCode =  session.selectOne("com.bean.VerificationCode.check", verificationCode);


           if(verificationCode==null){
               response.error(-20001,"验证码不存在");
           }else {
               verificationCode.setState(0);
               session.update("com.bean.VerificationCode.update",verificationCode);

           }

        } catch (Exception e){
            e.printStackTrace();
            session.rollback();
            response.error(-10000,"数据库出现错误");
        }
        session.commit();
        session.close();
    }
}
