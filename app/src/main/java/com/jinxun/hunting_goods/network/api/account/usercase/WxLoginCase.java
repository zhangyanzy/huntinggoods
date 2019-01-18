package com.jinxun.hunting_goods.network.api.account.usercase;

import com.jinxun.hunting_goods.network.BaseUseCase;
import com.jinxun.hunting_goods.network.api.account.AccountServiceApi;

import rx.Observable;

/**
 * Created by admin on 2019/1/15.
 */

public class WxLoginCase extends BaseUseCase<AccountServiceApi> {
    private Long type;
    private String wx;


    public WxLoginCase(Long type, String wx) {
        this.type = type;
        this.wx = wx;
    }


    @Override
    protected Observable buildCase() {
        return createConnection().wxLogin(type, wx);
    }
}
