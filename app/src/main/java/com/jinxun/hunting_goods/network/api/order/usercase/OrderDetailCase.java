package com.jinxun.hunting_goods.network.api.order.usercase;

import com.jinxun.hunting_goods.network.BaseUseCase;
import com.jinxun.hunting_goods.network.api.order.OrderService;

import rx.Observable;

/**
 * Created by zhangyan on 2018/12/24.
 */

public class OrderDetailCase extends BaseUseCase<OrderService> {

    private String orderNO;

    public OrderDetailCase(String orderNO) {
        this.orderNO = orderNO;
    }

    @Override
    protected Observable buildCase() {
        return createConnection().orderDetail(orderNO);
    }
}
