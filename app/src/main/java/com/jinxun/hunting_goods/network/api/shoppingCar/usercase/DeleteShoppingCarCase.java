package com.jinxun.hunting_goods.network.api.shoppingCar.usercase;

import com.jinxun.hunting_goods.network.BaseUseCase;
import com.jinxun.hunting_goods.network.api.shoppingCar.ShoppingService;

import rx.Observable;

/**
 * Created by zhangyan on 2018/12/17.
 */

public class DeleteShoppingCarCase extends BaseUseCase<ShoppingService> {

    private String uniqueCode;

    public DeleteShoppingCarCase(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    @Override
    protected Observable buildCase() {
        return createConnection().deleteShoppingCar(uniqueCode);
    }
}
