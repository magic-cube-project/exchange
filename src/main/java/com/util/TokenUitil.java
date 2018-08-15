package com.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by szc on 2018/8/14.
 */
public class TokenUitil {
    //随机数发生器
    public static String genetateToken(){
        int randomNum = (int)(1+Math.random()*1000);  //随机 1,1000 之间的随机数
        String token = System.currentTimeMillis()+randomNum+"";//获得毫秒数加随机数
        String tokenMd5=MD5(token);
        return tokenMd5;
    }
    /**
     * 合并token
     * @param tokenA
     * @param tokenB
     * @return
     */
    public static String mergeToken(String tokenA,String tokenB){
        String token  = tokenA+tokenB;
        String tokenMd5=MD5(token);
        return tokenMd5;
    }
    /**
     * 取值MD5发生器
     * @param sourceStr
     * @return
     */
    private static String MD5(String sourceStr) {
        String result = "";

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        return result;
    }
}
