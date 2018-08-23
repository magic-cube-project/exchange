package com.bean;

import java.io.Serializable;

/**
 * Created by szc on 2018/8/20.
 */
public class UserSession implements Serializable {
    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    int user_id;
}
