package com.jinxun.hunting_goods.network.bean.shopping;

import android.os.Parcel;
import android.os.Parcelable;

import com.jinxun.hunting_goods.network.bean.address.AddressEntity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by zhangyan on 2018/12/18.
 */

public class ConfirmedOrderEntity implements Serializable {

    private String orderNO;
    private String days;
    private String totalCost;
    private ArrayList<ProductEntity> productList;
    private AddressEntity addressEntity;

    public AddressEntity getAddressEntity() {
        return addressEntity;
    }

    public void setAddressEntity(AddressEntity addressEntity) {
        this.addressEntity = addressEntity;
    }

    public String getOrderNO() {
        return orderNO;
    }

    public void setOrderNO(String orderNO) {
        this.orderNO = orderNO;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public ArrayList<ProductEntity> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<ProductEntity> productList) {
        this.productList = productList;
    }

    @Override
    public String toString() {
        return "ConfirmedOrderEntity{" +
                "orderNO='" + orderNO + '\'' +
                ", days='" + days + '\'' +
                ", totalCost='" + totalCost + '\'' +
                ", productList=" + productList +
                '}';
    }
}
