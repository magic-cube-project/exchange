package com.controller;

import com.bean.MCCGood;
import com.model.Goods;
import com.util.Response;
import com.util.ResponseUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * MCC 商品的接口
 * Created by szc on 2018/8/22.
 */
@RestController
@RequestMapping("/MCCGood")
public class MCCGoodController {
    @RequestMapping("get")
    String get(){
        Response response = ResponseUtil.ceateRespone();
        List<MCCGood> list = Goods.getMCCGoods();
        response.setResult("list",list);
        return response.toJSON();
    }
}
