package com.util;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;

/**
 * Created by szc on 2018/8/13.
 */
public class Response {

    private int Code;
   private boolean Success;
   private String ErrorMsg;
   private HashMap Result;

    public Response(int code,boolean success,String errorMsg){
      Code = code;
      Success = success;
      ErrorMsg = errorMsg;
    }
    public Response(int code,boolean success){
        Code = code;
        Success = success;
        ErrorMsg = "success";
        Result = new HashMap();
    }
    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }

    public String getErrorMsg() {
        return ErrorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        ErrorMsg = errorMsg;
    }
    public HashMap getResult() {
        return Result;
    }

    public void setResult(HashMap result) {
        Result = result;
    }

    public void setResult(String key,Object obj){
        Result.put(key,obj);
    }

    public String toJSON(){
        return JSON.toJSONString(this);
    }

    public void error(int code,String msg){
        Code = -1*Math.abs(code);
        ErrorMsg = msg;
        Success = false;
    }

}
