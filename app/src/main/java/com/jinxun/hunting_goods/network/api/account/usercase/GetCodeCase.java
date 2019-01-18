package com.jinxun.hunting_goods.network.api.account.usercase;

import com.jinxun.hunting_goods.network.BaseUseCase;
import com.jinxun.hunting_goods.network.api.account.AccountServiceApi;

import rx.Observable;

/**
 * Created by zhangyan on 2019/1/8.
 */

public class GetCodeCase extends BaseUseCase<AccountServiceApi> {

    private String code;

    public GetCodeCase(String code) {
        this.code = code;
    }

    @Override
    protected Observable buildCase() {
        return createConnection().getCode(code);
    }
}
