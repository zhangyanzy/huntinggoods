package com.jinxun.hunting_goods.network.bean.auth;

import java.io.Serializable;

/**
 * Created by zhangyan on 2018/11/14.
 * 用户信息类
 */

public class User implements Serializable,Cloneable{

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
