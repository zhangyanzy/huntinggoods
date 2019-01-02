package com.jinxun.hunting_goods.network.bean.shopping;

import java.io.Serializable;

/**
 * Created by zhangyan on 2018/12/20.
 */

public class PayOrderEntity implements Serializable{

    private String receiveCode;
    private String receiveTimeStr;

    public String getReceiveCode() {
        return receiveCode;
    }

    public void setReceiveCode(String receiveCode) {
        this.receiveCode = receiveCode;
    }

    public String getReceiveTimeStr() {
        return receiveTimeStr;
    }

    public void setReceiveTimeStr(String receiveTimeStr) {
        this.receiveTimeStr = receiveTimeStr;
    }

    @Override
    public String toString() {
        return "PayOrderEntity{" +
                "receiveCode='" + receiveCode + '\'' +
                ", receiveTimeStr='" + receiveTimeStr + '\'' +
                '}';
    }
}
