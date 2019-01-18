package com.jinxun.hunting_goods.network.api.shoppingCar.usercase;

import com.jinxun.hunting_goods.network.BaseUseCase;
import com.jinxun.hunting_goods.network.api.shoppingCar.ShoppingService;

import rx.Observable;

/**
 * Created by zhangyan on 2018/12/17.
 */

public class ShoppingCarListCase extends BaseUseCase<ShoppingService> {

    private String token;

    public ShoppingCarListCase(String token) {
        this.token = token;
    }

    @Override
    protected Observable buildCase() {
        return createConnection().shoppingCarList(token);
    }
}
