package com.jinxun.hunting_goods.network.exception;

import java.io.IOException;

/**
 * Created by zhangyan on 2018/11/14.
 */

public class ApiException extends IOException {

    private int code;

    public ApiException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
