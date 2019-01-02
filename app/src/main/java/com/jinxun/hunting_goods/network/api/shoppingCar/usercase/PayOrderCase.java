package com.jinxun.hunting_goods.network.api.shoppingCar.usercase;

import com.jinxun.hunting_goods.network.BaseUseCase;
import com.jinxun.hunting_goods.network.api.shoppingCar.ShoppingService;

import rx.Observable;

/**
 * Created by zhanngyan on 2018/12/20.
 */

public class PayOrderCase extends BaseUseCase<ShoppingService> {

    private Long userId;
    private String orderNo;
    private Long receiveAddId;
    private Long deliveryAddId;
    private Long receiveStartTime;
    private Long receiveEndTime;

    public PayOrderCase(Long userId, String orderNo, Long receiveAddId, Long deliveryAddId, Long receiveStartTime, Long receiveEndTime) {
        this.userId = userId;
        this.orderNo = orderNo;
        this.receiveAddId = receiveAddId;
        this.deliveryAddId = deliveryAddId;
        this.receiveStartTime = receiveStartTime;
        this.receiveEndTime = receiveEndTime;
    }


    @Override
    protected Observable buildCase() {
        return createConnection().payOrder(userId, orderNo, receiveAddId, deliveryAddId, receiveStartTime, receiveEndTime);
    }
}
