package com.jinxun.hunting_goods.network.bean.order;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by zhangyan on 2018/12/24.
 */

public class ProductInfoEntity {

    private BigDecimal cost;//费用
    private String uniqueCode;//code
    private String describe;//描述
    private String productName;//产品名称
    private String img;//产品图片

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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Info{" +
                "cost=" + cost +
                ", uniqueCode='" + uniqueCode + '\'' +
                ", describe='" + describe + '\'' +
                ", productName='" + productName + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
