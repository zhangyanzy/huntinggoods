package com.jinxun.hunting_goods.network.api.address.usercase;

import com.jinxun.hunting_goods.network.BaseUseCase;
import com.jinxun.hunting_goods.network.api.address.AddressServiceApi;

import rx.Observable;

/**
 * Created by zhangyan on 2018/12/6.
 */

public class GetAddressById extends BaseUseCase<AddressServiceApi> {

    private String id;//数据主键
    private String token;//

    public GetAddressById(String id, String token) {
        this.id = id;
        this.token = token;
    }


    @Override
    protected Observable buildCase() {
        return createConnection().getAddressById(id, token);
    }
}
