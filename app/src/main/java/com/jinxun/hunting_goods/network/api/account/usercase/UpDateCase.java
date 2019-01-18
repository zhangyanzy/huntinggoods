package com.jinxun.hunting_goods.network.api.account.usercase;

import com.jinxun.hunting_goods.network.BaseUseCase;
import com.jinxun.hunting_goods.network.api.account.AccountServiceApi;

import rx.Observable;

/**
 * Created by admin on 2019/1/9.
 */

public class UpDateCase extends BaseUseCase<AccountServiceApi>{

    private String phone;
    private String token;
    private String code;

    public UpDateCase(String phone, String token, String code) {
        this.phone = phone;
        this.token = token;
        this.code = code;
    }

    @Override
    protected Observable buildCase() {
        return createConnection().updatePhone(phone,token,code);
    }
}
