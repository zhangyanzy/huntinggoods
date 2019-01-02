package com.jinxun.hunting_goods.network.api.shoe.usercase;

import com.jinxun.hunting_goods.network.BaseUseCase;
import com.jinxun.hunting_goods.network.api.shoe.ShoeServiceApi;

import rx.Observable;

/**
 * Created by zhangyan on 2018/12/12.
 */

public class GetShoeMaterial extends BaseUseCase<ShoeServiceApi> {

    private String positionId;

    public GetShoeMaterial(String positionId) {
        this.positionId = positionId;
    }

    @Override
    protected Observable buildCase() {
        return createConnection().getShoeMaterial(positionId);
    }
}
