package com.jinxun.hunting_goods.network.api.address.usercase;

import com.jinxun.hunting_goods.network.BaseUseCase;
import com.jinxun.hunting_goods.network.api.address.AddressServiceApi;

import rx.Observable;

/**
 * Created by zhangyan on 2018/12/5.
 */

public class GetProvinceCase extends BaseUseCase<AddressServiceApi> {

    public GetProvinceCase() {

    }

    @Override
    protected Observable buildCase() {
        return createConnection().getProvince();
    }
}
