package com.model;

import com.bean.AppUserLink;
import com.util.MyBatisUtil;
import com.util.TokenUitil;
import org.apache.ibatis.session.SqlSession;

import java.util.Date;

/**
 * 开放用户联系表
 */
public class OpenUser {
    /**
     * 增加一个用户与应用的关系
     * @param app_id
     * @param user_id
     * @return
     */
    public static AppUserLink add(int app_id,int user_id){
        AppUserLink openUser = new AppUserLink();
        openUser.setApp_id(app_id);
        openUser.setUser_id(user_id);
        openUser.setCreate_time(new Date().getTime());
        // 产生用户和应用之间的唯一标志码
        String openid = TokenUitil.mergeToken(app_id+"",user_id+"");
        openUser.setOpenid(openid);
        SqlSession session = MyBatisUtil.getSqlSession();
        try {
            session.insert("com.bean.AppUserLink.add", openUser);
        } catch (Exception e){
            e.printStackTrace();
            session.rollback();
        }
        finally {
            session.commit();
            session.close();
        }
        return openUser;
    }

    public static AppUserLink selectByopenid(String openid){
        AppUserLink openUser = new AppUserLink();

        openUser.setOpenid(openid);

        SqlSession session = MyBatisUtil.getSqlSession();
        try {
            openUser =  session.selectOne("com.bean.AppUserLink.selectByopenid", openUser);
        } catch (Exception e){
            e.printStackTrace();
            session.rollback();
        }
        finally {
            session.commit();
            session.close();
        }
        return openUser;
    }

    public static AppUserLink select(int app_id,int user_id){
        AppUserLink openUser = new AppUserLink();

        openUser.setApp_id(app_id);
        openUser.setUser_id(user_id);

        SqlSession session = MyBatisUtil.getSqlSession();
        try {
           openUser =  session.selectOne("com.bean.AppUserLink.selectOne", openUser);
        } catch (Exception e){
            e.printStackTrace();
            session.rollback();
        }
        finally {
            session.commit();
            session.close();
        }
        return openUser;
    }
}
