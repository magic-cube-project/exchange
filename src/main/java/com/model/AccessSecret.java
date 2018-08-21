package com.model;

import com.bean.AccessSecretInfo;
import com.util.MyBatisUtil;
import com.util.Response;
import org.apache.ibatis.session.SqlSession;

public class AccessSecret {
    /**
     * 检查用户秘钥是否正确
     * @param appid
     * @param secret
     * @return
     */
    public static AccessSecretInfo checkSecret(Response response,String appid, String secret){
        SqlSession session = MyBatisUtil.getSqlSession();
        try {
            AccessSecretInfo accessSecretInfo = (AccessSecretInfo) session.selectOne(
                    "com.bean.AccessSecretInfo.getAccessByAppid", appid);
            if (accessSecretInfo != null) {
                if(accessSecretInfo.getSecret().equals(secret)){
                    return accessSecretInfo;
                } else {
                    response.error(10002,"应用秘钥不正确");
                    return null;
                }
            } else {
                response.error(10001,"应用未注册");
                return null;
            }
        } finally {
            session.close();
        }
    }
}
