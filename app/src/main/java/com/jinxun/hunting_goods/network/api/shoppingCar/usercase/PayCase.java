package com.jinxun.hunting_goods.network.api.shoppingCar.usercase;

import com.jinxun.hunting_goods.network.BaseUseCase;
import com.jinxun.hunting_goods.network.api.shoppingCar.ShoppingService;

import rx.Observable;

/**
 * Created by zhangyan on 2018/12/27.
 */

public class PayCase extends BaseUseCase<ShoppingService> {

    private Long userId;

    public PayCase(Long userId) {
        this.userId = userId;
    }

    @Override
    protected Observable buildCase() {
        return createConnection().getAlipayCode(userId);
    }
}
