package com.jinxun.hunting_goods.network.bean.order;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by zhangyan on 2018/12/24.
 */

public class OrderListEntity {

    private ArrayList<ProductInfoEntity> product;
    private String orderNO;//订单编号
    private Long orderStatus;// /0,1代付款 2代取件 3代发货 4待收货 5已完成 6售后/

    public ArrayList<ProductInfoEntity> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<ProductInfoEntity> product) {
        this.product = product;
    }

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

    @Override
    public String toString() {
        return "OrderListEntity{" +
                "product=" + product +
                ", orderNO='" + orderNO + '\'' +
                ", orderStatus=" + orderStatus +
                '}';
    }
}
