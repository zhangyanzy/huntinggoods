package com.jinxun.hunting_goods.network.api.account.usercase;

import com.jinxun.hunting_goods.network.BaseUseCase;
import com.jinxun.hunting_goods.network.api.account.AccountServiceApi;

import rx.Observable;

/**
 * Created by zhangyan on 2018/12/10.
 */

public class SendMessage extends BaseUseCase<AccountServiceApi> {

    private String content;
    private String token;

    public SendMessage(String content, String token) {
        this.content = content;
        this.token = token;
    }

    @Override
    protected Observable buildCase() {
        return createConnection().sendMessage(content,token);
    }
}
