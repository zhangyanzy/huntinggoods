package com.jinxun.hunting_goods.network.api.shoe.usercase;

import com.jinxun.hunting_goods.network.BaseUseCase;
import com.jinxun.hunting_goods.network.api.shoe.ShoeServiceApi;

import rx.Observable;

/**
 * Created by zhangyan on 2018/12/11.
 */

public class GetShoes extends BaseUseCase<ShoeServiceApi> {

    @Override
    protected Observable buildCase() {
        return createConnection().getShoes();
    }
}
