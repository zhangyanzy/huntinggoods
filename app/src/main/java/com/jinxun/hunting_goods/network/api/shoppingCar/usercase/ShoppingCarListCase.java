package com.jinxun.hunting_goods.network.api.shoppingCar.usercase;

import com.jinxun.hunting_goods.network.BaseUseCase;
import com.jinxun.hunting_goods.network.api.shoppingCar.ShoppingService;

import rx.Observable;

/**
 * Created by zhangyan on 2018/12/17.
 */

public class ShoppingCarListCase extends BaseUseCase<ShoppingService> {

    private Long userId;

    public ShoppingCarListCase(Long userId) {
        this.userId = userId;
    }

    @Override
    protected Observable buildCase() {
        return createConnection().shoppingCarList(userId);
    }
}
