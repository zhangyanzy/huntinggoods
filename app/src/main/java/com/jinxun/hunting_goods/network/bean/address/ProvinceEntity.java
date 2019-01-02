package com.jinxun.hunting_goods.network.bean.address;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * Created by zhangyan on 2018/12/5.
 * 获取省份
 */

public class ProvinceEntity  implements IPickerViewData {

    private Long id;//主键
    private Long areaCode;//地区编码
    private String areaName;//地区名称
    private Long parentId;//父级id
    private Long lng;//经度
    private Long lat;//纬度

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(Long areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getLng() {
        return lng;
    }

    public void setLng(Long lng) {
        this.lng = lng;
    }

    public Long getLat() {
        return lat;
    }

    public void setLat(Long lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return "ProvinceEntity{" +
                "id=" + id +
                ", areaCode=" + areaCode +
                ", areaName='" + areaName + '\'' +
                ", parentId=" + parentId +
                ", lng=" + lng +
                ", lat=" + lat +
                '}';
    }

    @Override
    public String getPickerViewText() {
        return areaName;
    }
}
