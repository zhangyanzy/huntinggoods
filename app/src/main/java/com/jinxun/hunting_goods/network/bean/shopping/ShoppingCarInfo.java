package com.jinxun.hunting_goods.network.bean.shopping;

import java.math.BigDecimal;

/**
 * Created by zhangyan on 2018/12/17.
 * 购物车列表
 */

public class ShoppingCarInfo {

    private String img;//产品图片
    private BigDecimal cost;//费用
    private String uniqueCode;//code
    private String describe;//描述
    private String productName;//产品名称
    private boolean isChoosed;//

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
    }

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

    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
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
        return "ShoppingCarInfo{" +
                "cost=" + cost +
                ", uniqueCode='" + uniqueCode + '\'' +
                ", describe='" + describe + '\'' +
                ", productName='" + productName + '\'' +
                '}';
    }
}
