package com.jinxun.hunting_goods.network.api.address.usercase;

import com.jinxun.hunting_goods.network.BaseUseCase;
import com.jinxun.hunting_goods.network.api.address.AddressServiceApi;

import rx.Observable;

/**
 * Created by admin on 2018/12/10.
 */

public class DeleteAddress extends BaseUseCase<AddressServiceApi> {

    private String id;
    private String token;

    public DeleteAddress(String id, String token) {
        this.id = id;
        this.token = token;
    }

    @Override
    protected Observable buildCase() {
        return createConnection().deleteAddress(id, token);
    }
}
