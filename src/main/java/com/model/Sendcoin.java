package com.model;

import com.alibaba.fastjson.JSONObject;
import com.bean.AppSendcoinList;
import com.flkj.service.ThirdParty;
import com.util.MyBatisUtil;
import com.util.Response;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by szc on 2018/8/21.
 */
@Service
public class Sendcoin {
    @Autowired
    private ThirdParty thirdParty;
    /**
     * 发送游戏接模块接口
     * @param app_id
     * @param user_id
     * @param amount
     * @param coin
     * @param description
     * @param tag
     */
    public void add(int app_id, int user_id, int amount, String coin, String description, String tag, Response response){
        AppSendcoinList appSendcoinList = new AppSendcoinList();
        appSendcoinList.setCreate_time(new Date().getTime());
        appSendcoinList.setAmount(amount);
        appSendcoinList.setDescription(description);
        appSendcoinList.setCoin(coin);
        appSendcoinList.setTag(tag);
        appSendcoinList.setApp_id(app_id);
        appSendcoinList.setUser_id(user_id);
        SqlSession session = MyBatisUtil.getSqlSession();
        try {
            // 插入用户数据info表
            session.insert("com.bean.AppSendcoinList.add", appSendcoinList);
            System.out.println("insert"+appSendcoinList.getId());
            JSONObject jsonobj = thirdParty.Deposit(appSendcoinList);
            if(jsonobj==null) {
                response.error(-9,"服务器请求失败");
                throw new Exception();
            }
            if((Boolean) jsonobj.get("success")==false){
                response.error(-20001, (String) jsonobj.get("errorMessage"));
                throw new Exception();
            }

        } catch (Exception e){
            e.printStackTrace();
            session.rollback();
        }
        session.commit();
        session.close();
    }
}
