package com.jinxun.hunting_goods.network;

/**
 * Created by zhangyan on 2018/11/14.
 */

public class AuthEvent {

    public static int TOKEN_EXPIRED = 1;

    public int type;

    public AuthEvent(int type) {
        this.type = type;
    }

}
