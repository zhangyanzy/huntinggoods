package com.jinxun.hunting_goods.network.api.address.usercase;

import com.jinxun.hunting_goods.network.BaseUseCase;
import com.jinxun.hunting_goods.network.api.address.AddressServiceApi;

import rx.Observable;

/**
 * Created by admin on 2018/12/6.
 */

public class GetAddress extends BaseUseCase<AddressServiceApi> {

    private String token;


    public GetAddress(String token) {
        this.token = token;
    }

    @Override
    protected Observable buildCase() {
        return createConnection().getAddress(token);
    }
}
