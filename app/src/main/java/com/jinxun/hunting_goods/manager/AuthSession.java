package com.jinxun.hunting_goods.manager;

import com.jinxun.hunting_goods.network.bean.auth.User;

/**
 * Created by zhangyan on 2018/11/14.
 */

public class AuthSession {

    private User user = new User();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
