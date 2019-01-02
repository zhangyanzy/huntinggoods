package com.jinxun.hunting_goods.network.bean;

/**
 * Created by zhangyan on 2018/11/14.
 */

public class SumResponse <S, T> extends Response<T> {
    private S summary;

    public S getSummary() {
        return summary;
    }

    public void setSummary(S summary) {
        this.summary = summary;
    }

}
