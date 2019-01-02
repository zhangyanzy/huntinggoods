package com.jinxun.hunting_goods.network.bean.shopping;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by admin on 2018/12/19.
 */

public class createOrder implements Serializable {

    private Long userId;
    private String[] code;

    public createOrder() {
    }

    public createOrder(Long userId, String[] code) {
        this.userId = userId;
        this.code = code;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String[] getCode() {
        return code;
    }

    public void setCode(String[] code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "createOrder{" +
                "userId=" + userId +
                ", code=" + Arrays.toString(code) +
                '}';
    }
}
