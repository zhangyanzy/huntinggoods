package com.jinxun.hunting_goods.network.bean.shoe;

import java.util.ArrayList;

/**
 * Created by zhangyan on 2018/12/13.
 */

public class ShoeColorEntity {
    private Long colorId;//颜色ID
    private Long positionId;//部位ID
    private Long materialId;//材料ID
    private String colorName;//形象描述

    private String colorCode;//英文代码
    private String RGB;//RGB
    private String hexadecimal;//十六进制
    private ArrayList<String> imgList;//产品图片


    public Long getColorId() {
        return colorId;
    }

    public void setColorId(Long colorId) {
        this.colorId = colorId;
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

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getRGB() {
        return RGB;
    }

    public void setRGB(String RGB) {
        this.RGB = RGB;
    }

    public String getHexadecimal() {
        return hexadecimal;
    }

    public void setHexadecimal(String hexadecimal) {
        this.hexadecimal = hexadecimal;
    }

    public ArrayList<String> getImgList() {
        return imgList;
    }

    public void setImgList(ArrayList<String> imgList) {
        this.imgList = imgList;
    }

    @Override
    public String toString() {
        return "ShoeColorEntity{" +
                "colorId=" + colorId +
                ", positionId=" + positionId +
                ", materialId=" + materialId +
                ", colorName='" + colorName + '\'' +
                ", colorCode='" + colorCode + '\'' +
                ", RGB='" + RGB + '\'' +
                ", hexadecimal='" + hexadecimal + '\'' +
                ", imgList=" + imgList +
                '}';
    }
}
