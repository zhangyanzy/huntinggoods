package com.jinxun.hunting_goods.network.api.shoe.usercase;

import com.jinxun.hunting_goods.network.BaseUseCase;
import com.jinxun.hunting_goods.network.api.shoe.ShoeServiceApi;

import rx.Observable;

/**
 * Created by admin on 2018/12/12.
 */

public class GetShoePart extends BaseUseCase<ShoeServiceApi> {

    private String token;

    public GetShoePart(String token) {
        this.token = token;
    }

    @Override
    protected Observable buildCase() {
        return createConnection().getShoePart(token);
    }
}
