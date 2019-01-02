package com.jinxun.hunting_goods.network.bean.order;

import com.jinxun.hunting_goods.network.bean.address.AddressEntity;

import java.io.Serializable;

/**
 * Created by zhangyan on 2018/11/26.
 * 订单详情
 */

public class OrderInfoEntity implements Serializable {


    private String orderNO;
    private Long orderStatus;//0,1代付款 2代取件 3代发货 4待收货 5已完成 6售后
    private Long createTime;//订单创建时间
    private AddressEntity receiveAdd;//收货地址
    private AddressEntity deliveryAdd;//取货地址

    public String getOrderNO() {
        return orderNO;
    }

    public void setOrderNO(String orderNO) {
        this.orderNO = orderNO;
    }

    public Long getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Long orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public AddressEntity getReceiveAdd() {
        return receiveAdd;
    }

    public void setReceiveAdd(AddressEntity receiveAdd) {
        this.receiveAdd = receiveAdd;
    }

    public AddressEntity getDeliveryAdd() {
        return deliveryAdd;
    }

    public void setDeliveryAdd(AddressEntity deliveryAdd) {
        this.deliveryAdd = deliveryAdd;
    }

    @Override
    public String toString() {
        return "OrderInfoEntity{" +
                "orderNO='" + orderNO + '\'' +
                ", orderStatus=" + orderStatus +
                ", createTime=" + createTime +
                ", receiveAdd=" + receiveAdd +
                ", deliveryAdd=" + deliveryAdd +
                '}';
    }
}
