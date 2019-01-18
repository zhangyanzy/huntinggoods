package com.jinxun.hunting_goods.network.api.account.usercase;

import com.jinxun.hunting_goods.network.BaseUseCase;
import com.jinxun.hunting_goods.network.api.account.AccountServiceApi;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by zhangyan on 2019/1/14.
 */

public class LoginTypeCase extends BaseUseCase<AccountServiceApi> {


    private Long type;
    private String qq;

    public LoginTypeCase(Long type, String qq) {
        this.type = type;
        this.qq = qq;
    }

    @Override
    protected Observable buildCase() {
        return createConnection().loginType(type,qq);
    }
}
