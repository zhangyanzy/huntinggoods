package com.jinxun.hunting_goods.network.exception;

import java.io.IOException;

/**
 * Created by zhangyan on 2018/11/14.
 */

public class TokenExpiredException extends IOException{

    private int code;
    private String message;

    public TokenExpiredException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
