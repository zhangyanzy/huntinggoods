package com.jinxun.hunting_goods.network.bean.shoe;

/**
 * Created by zhangyan on 2018/12/14.
 */

public class CompletePartEntity {



    private String userId;//用户ID
    private String productId;//产品ID
    private String positionId;//部位ID
    private String materialId;//材料ID
    private String colorId;//颜色ID
    private String productName;//产品名称
    private String positionName;//部位名称
    private String materialName;//材料名称
    private String colorName;//颜色名称

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getColorId() {
        return colorId;
    }

    public void setColorId(String colorId) {
        this.colorId = colorId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    @Override
    public String toString() {
        return "CompletePartEntity{" +
                "userId='" + userId + '\'' +
                ", productId='" + productId + '\'' +
                ", positionId='" + positionId + '\'' +
                ", materialId='" + materialId + '\'' +
                ", colorId='" + colorId + '\'' +
                ", productName='" + productName + '\'' +
                ", positionName='" + positionName + '\'' +
                ", materialName='" + materialName + '\'' +
                ", colorName='" + colorName + '\'' +
                '}';
    }
}
