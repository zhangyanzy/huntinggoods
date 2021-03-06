package com.jinxun.hunting_goods.network.api.order.usercase;

import com.jinxun.hunting_goods.network.BaseUseCase;
import com.jinxun.hunting_goods.network.api.order.OrderService;

import rx.Observable;

/**
 * Created by zhangyan on 2018/12/24.
 */

public class OrderListCase extends BaseUseCase<OrderService> {


    private String token;
    private Long orderStatus;//全部(100) /0,1代付款 2代取件 3代发货 4待收货 5已完成 6售后/


    public OrderListCase(String token, Long orderStatus) {
        this.token = token;
        this.orderStatus = orderStatus;
    }

    @Override
    protected Observable buildCase() {
        return createConnection().orderListEntity(token,orderStatus);
    }
}
