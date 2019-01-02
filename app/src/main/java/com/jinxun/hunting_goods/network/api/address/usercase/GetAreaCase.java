package com.jinxun.hunting_goods.network.api.address.usercase;

import com.jinxun.hunting_goods.network.BaseUseCase;
import com.jinxun.hunting_goods.network.api.address.AddressServiceApi;

import rx.Observable;

/**
 * Created by admin on 2018/12/5.
 */

public class GetAreaCase extends BaseUseCase<AddressServiceApi> {

    private Long parentId;

    public GetAreaCase(Long parentId) {
        this.parentId = parentId;
    }

    @Override
    protected Observable buildCase() {
        return createConnection().getArea(parentId);
    }
}
