package com.jinxun.hunting_goods.network.bean.shopping;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by zhangyan on 2018/12/18.
 */

public class ProductEntity implements Serializable {

    private String img;
    private BigDecimal cost;
    private String describe;
    private String productName;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "img='" + img + '\'' +
                ", cost=" + cost +
                ", describe='" + describe + '\'' +
                ", productName='" + productName + '\'' +
                '}';
    }
}
