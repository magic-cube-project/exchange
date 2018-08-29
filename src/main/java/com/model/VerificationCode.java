package com.model;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.util.MyBatisUtil;
import com.util.Response;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.Date;
import java.util.List;

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
         send(tel,code);
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
            List verificationCodeList = session.selectList("com.bean.VerificationCode.check",verificationCode);

           if(verificationCodeList.isEmpty()){
               response.error(-20001,"验证码不存在");
           }else {
               // 验证码存在的情况处理
               verificationCode  = (com.bean.VerificationCode) verificationCodeList.get(0);
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

    public static void send(String tel,int code) {
        new SendCode(tel,code).run();
    }
    public static int createCode(){
        return (int)((Math.random()*9+1)*100000);
    }
}

class SendCode extends Thread {

    private String tel;
    private int code;

    public SendCode(String tel, int code){
        this.tel = tel;
        this.code = code;
    }

    @Override
    public void run() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://api.sms.cn/sms/?ac=send&uid=magic290673186&pwd=6431c1a5c8e9c18b6c615798080a2bef&template=471175&mobile="+tel+"&content={%22code%22:%22"+code+"%22}")
                .get()
                .addHeader("Cache-Control", "no-cache")
                .build();
        try {
            com.squareup.okhttp.Response response = client.newCall(request).execute();
            String res =  response.body().string();
            System.out.println(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
