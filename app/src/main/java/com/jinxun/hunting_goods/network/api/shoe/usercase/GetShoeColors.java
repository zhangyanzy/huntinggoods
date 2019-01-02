package com.jinxun.hunting_goods.network.api.shoe.usercase;

import com.jinxun.hunting_goods.network.BaseUseCase;
import com.jinxun.hunting_goods.network.api.shoe.ShoeServiceApi;

import rx.Observable;

/**
 * Created by zhangyan on 2018/12/13.
 */

public class GetShoeColors extends BaseUseCase<ShoeServiceApi> {

    private Long positionId;
    private Long materialId;

    public GetShoeColors(Long positionId, Long materialId) {
        this.positionId = positionId;
        this.materialId = materialId;
    }

    @Override
    protected Observable buildCase() {
        return createConnection().getShoeColors(positionId, materialId);
    }
}
