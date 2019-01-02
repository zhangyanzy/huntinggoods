package com.jinxun.hunting_goods.network.api.address.usercase;

import com.jinxun.hunting_goods.network.BaseUseCase;
import com.jinxun.hunting_goods.network.api.address.AddressServiceApi;

import rx.Observable;

/**
 * Created by admin on 2018/12/6.
 */

public class GetAddress extends BaseUseCase<AddressServiceApi> {

    private Long userId;


    public GetAddress(Long userId) {
        this.userId = userId;
    }

    @Override
    protected Observable buildCase() {
        return createConnection().getAddress(userId);
    }
}
