package com.jinxun.hunting_goods.network.api.address.usercase;

import com.jinxun.hunting_goods.network.BaseUseCase;
import com.jinxun.hunting_goods.network.api.account.AccountServiceApi;

import rx.Observable;

/**
 * Created by zhangyan on 2019/1/14.
 */

public class LoginOutCase extends BaseUseCase<AccountServiceApi> {

    private String token;

    public LoginOutCase(String token) {
        this.token = token;
    }

    @Override
    protected Observable buildCase() {
        return createConnection().loginOut(token);
    }
}
