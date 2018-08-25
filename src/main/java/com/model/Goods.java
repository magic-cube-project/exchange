package com.model;

import com.bean.MCCGood;
import com.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import java.util.List;
/**
 * Created by szc on 2018/8/22.
 */
public class Goods {
    /**
     * 查询当前Mcc的货物
     * @return
     */
    public static List<MCCGood> getMCCGoods(){
        List<MCCGood> goodArr = null;
        SqlSession session = MyBatisUtil.getSqlSession();
        try {
            goodArr= session.selectList("com.bean.MCCGood.select");
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
        session.commit();
        session.close();
        return goodArr;
    }
}
