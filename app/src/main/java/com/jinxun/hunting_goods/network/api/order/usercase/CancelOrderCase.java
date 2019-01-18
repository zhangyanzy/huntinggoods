package com.jinxun.hunting_goods.network.api.order.usercase;

import com.jinxun.hunting_goods.network.BaseUseCase;
import com.jinxun.hunting_goods.network.api.order.OrderService;

import rx.Observable;

/**
 * Created by admin on 2019/1/15.
 */

public class CancelOrderCase extends BaseUseCase<OrderService> {

    private String token;
    private String orderNO;

    public CancelOrderCase(String token, String orderNO) {
        this.token = token;
        this.orderNO = orderNO;
    }

    @Override
    protected Observable buildCase() {
        return createConnection().cancelOrder(token, orderNO);
    }
}
