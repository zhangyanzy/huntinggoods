package com.jinxun.hunting_goods.network.bean.auth;

import java.io.Serializable;

/**
 * Created by zhangyan on 2018/11/14.
 * 用户信息类
 */

public class User implements Serializable, Cloneable {

    private String phone;
    private Long userId;
    private String token;
    private String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
