package com.jinxun.hunting_goods.network.bean.shoe;

import java.util.List;

/**
 * Created by zhanyan on 2018/11/21.
 * 鞋子单品信息
 */

public class ShoeInfoEntity {


    private String name;
    private List<String> imagesList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getImagesList() {
        return imagesList;
    }

    public void setImagesList(List<String> imagesList) {
        this.imagesList = imagesList;
    }

    @Override
    public String toString() {
        return "ShoeInfoEntity{" +
                "name='" + name + '\'' +
                ", images=" + imagesList +
                '}';
    }
}
