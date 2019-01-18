package com.jinxun.hunting_goods.network.api.order.usercase;

import com.jinxun.hunting_goods.network.BaseUseCase;
import com.jinxun.hunting_goods.network.api.order.OrderService;

import rx.Observable;

/**
 * Created by zhangyan on 2018/12/24.
 */

public class OrderDetailCase extends BaseUseCase<OrderService> {

    private String orderNO;
    private String token;


    public OrderDetailCase(String orderNO, String token) {
        this.orderNO = orderNO;
        this.token = token;
    }

    @Override
    protected Observable buildCase() {
        return createConnection().orderDetail(token, orderNO);
    }
}
