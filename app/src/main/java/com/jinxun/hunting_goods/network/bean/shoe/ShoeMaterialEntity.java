package com.jinxun.hunting_goods.network.bean.shoe;

import java.util.ArrayList;

/**
 * Created by zhangyan on 2018/12/12.
 */

public class ShoeMaterialEntity {

    private String materialName;//材料名称
    private Long positionId;//部位ID
    private Long materialId;//材料ID
    private String materialImage;//材料图片
    private String productName;
    private ArrayList<String> imgList;



    public ArrayList<String> getImgList() {
        return imgList;
    }

    public void setImgList(ArrayList<String> imgList) {
        this.imgList = imgList;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public String getMaterialImage() {
        return materialImage;
    }

    public void setMaterialImage(String materialImage) {
        this.materialImage = materialImage;
    }


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public String toString() {
        return "ShoeMaterialEntity{" +
                "materialName='" + materialName + '\'' +
                ", positionId=" + positionId +
                ", materialId=" + materialId +
                ", materialImage='" + materialImage + '\'' +
                ", imgList=" + imgList +
                '}';
    }
}
