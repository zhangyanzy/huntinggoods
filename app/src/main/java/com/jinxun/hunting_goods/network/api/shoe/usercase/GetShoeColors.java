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
    private String token;

    public GetShoeColors(Long positionId, Long materialId, String token) {
        this.positionId = positionId;
        this.materialId = materialId;
        this.token = token;
    }


    @Override
    protected Observable buildCase() {
        return createConnection().getShoeColors(token,positionId, materialId);
    }
}
