package com.jinxun.hunting_goods.network.api.shoppingCar.usercase;

import com.jinxun.hunting_goods.network.BaseUseCase;
import com.jinxun.hunting_goods.network.api.shoppingCar.ShoppingService;

import rx.Observable;

/**
 * Created by zhangyan on 2018/12/19.
 */

public class ToPay extends BaseUseCase<ShoppingService> {

    private Long userId;
    private String[] code;

    public ToPay(Long userId, String[] code) {
        this.userId = userId;
        this.code = code;
    }


    @Override
    protected Observable buildCase() {
        return createConnection().confirmedOrder(userId, code);
    }
}
