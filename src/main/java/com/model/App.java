package com.model;

import com.bean.AppInfo;
import com.bean.UserInfo;
import com.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

public class App {
    public static AppInfo getInfo(int app_id){
        boolean _is = false;
         AppInfo appInfo = new AppInfo();
         appInfo.setApp_id(app_id);
        SqlSession session = MyBatisUtil.getSqlSession();
        try {
            appInfo =  session.selectOne("com.bean.AppInfo.select", appInfo);
            if(appInfo==null){
                _is = false;
            } else {
                _is = true;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            session.commit();
            session.close();
        }
        return appInfo;
    }
}
