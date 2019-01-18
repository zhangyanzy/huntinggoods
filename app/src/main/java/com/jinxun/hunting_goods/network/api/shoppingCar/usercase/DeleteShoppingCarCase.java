package com.jinxun.hunting_goods.network.api.shoppingCar.usercase;

import com.jinxun.hunting_goods.network.BaseUseCase;
import com.jinxun.hunting_goods.network.api.shoppingCar.ShoppingService;

import rx.Observable;

/**
 * Created by zhangyan on 2018/12/17.
 */

public class DeleteShoppingCarCase extends BaseUseCase<ShoppingService> {

    private String uniqueCode;
    private String token;

    public DeleteShoppingCarCase(String uniqueCode, String token) {
        this.uniqueCode = uniqueCode;
        this.token = token;
    }

    @Override
    protected Observable buildCase() {
        return createConnection().deleteShoppingCar(uniqueCode,token);
    }
}
