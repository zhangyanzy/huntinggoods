package com.jinxun.hunting_goods.network.api.account.usercase;

import com.jinxun.hunting_goods.network.BaseUseCase;
import com.jinxun.hunting_goods.network.api.account.AccountServiceApi;

import rx.Observable;

/**
 * Created by zhangyan on 2018/11/14.
 */

public class GetAccountCase extends BaseUseCase<AccountServiceApi> {

    private String phone;
    private String code;

    public GetAccountCase(String phone, String code) {
        this.phone = phone;
        this.code = code;
    }

    @Override
    protected Observable buildCase() {
        return createConnection().login(phone, code);
    }
}
