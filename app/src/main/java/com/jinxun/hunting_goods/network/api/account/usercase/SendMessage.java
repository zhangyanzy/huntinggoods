package com.jinxun.hunting_goods.network.api.account.usercase;

import com.jinxun.hunting_goods.network.BaseUseCase;
import com.jinxun.hunting_goods.network.api.account.AccountServiceApi;

import rx.Observable;

/**
 * Created by zhangyan on 2018/12/10.
 */

public class SendMessage extends BaseUseCase<AccountServiceApi> {

    private String content;
    private Long userId;

    public SendMessage(String content, Long userId) {
        this.content = content;
        this.userId = userId;
    }

    @Override
    protected Observable buildCase() {
        return createConnection().sendMessage(content,userId);
    }
}
