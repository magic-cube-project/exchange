package com.model;

import com.util.MyBatisUtil;
import com.util.TokenUitil;
import org.apache.ibatis.session.SqlSession;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by szc on 2018/8/14.
 */
public class AccessToken {
    /**
     * 创建一个access_token
     *
     * @param app_id
     * @return
     */
    public static HashMap createAccessToken(int app_id) {
        HashMap map = new HashMap();
        long creat_time = new Date().getTime();
        long expires_in = 7200000;
        long expires_time = creat_time + expires_in;
        String access_token = TokenUitil.genetateToken();
        map.put("app_id",app_id);
        map.put("creat_time",creat_time);
        map.put("expires_in", expires_in);
        map.put("expires_time", expires_time);
        map.put("access_token", access_token);

        SqlSession session = MyBatisUtil.getSqlSession();
        try {
            session.insert("com.bean.AccessToken.add", map);
        } catch (Exception e){
            e.printStackTrace();
            session.rollback();
        }
        session.commit();
        session.close();
        map.remove("creat_time");

        return map;
    }
}
