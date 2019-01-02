package com.jinxun.hunting_goods.network.api.address.usercase;

import com.jinxun.hunting_goods.network.BaseUseCase;
import com.jinxun.hunting_goods.network.api.address.AddressServiceApi;

import rx.Observable;

/**
 * Created by zhangyan on 2018/12/5.
 */

public class GetCityCase extends BaseUseCase<AddressServiceApi> {

    private Long parentId;

    public GetCityCase(Long parentId) {
        this.parentId = parentId;
    }

    @Override
    protected Observable buildCase() {
        return createConnection().getCity(parentId);
    }
}
