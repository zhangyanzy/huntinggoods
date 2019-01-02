package com.jinxun.hunting_goods.network.bean;

/**
 * Created by zhangyan on 2018/11/14.
 * 返回外部数据结构
 */

public class Response<T> {

    private String code;
    private String message;
//    private boolean isSuccess;
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
