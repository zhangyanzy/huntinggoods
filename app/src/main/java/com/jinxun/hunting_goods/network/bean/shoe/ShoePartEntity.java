package com.jinxun.hunting_goods.network.bean.shoe;

/**
 * Created by zhangyan on 2018/11/23.
 */

public class ShoePartEntity {

    private String img;//部位缩略图地址
    private String name;//部位名称
    private String positionId;//部位id

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }


    @Override
    public String toString() {
        return "ShoePartEntity{" +
                "img='" + img + '\'' +
                ", name='" + name + '\'' +
                ", positionId='" + positionId + '\'' +
                '}';
    }
}
