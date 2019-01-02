package com.jinxun.hunting_goods.network.bean.address;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhangyan on 2018/11/27.
 * 配送地址详情
 */

public class AddressEntity implements Serializable{

    private Long id;
    private Long userId;
    private String name;//昵称
    private String phone;
    private String province;//省
    private Long provinceCode;
    private String city;//市
    private Long cityCode;
    private String district;//区
    private Long districtCode;
    private String address;//详细地址
    private Integer isDefault;//默认地址   0-否 1-是
    private boolean isChoose;
//    private Date createTime;
//    private Date updateTime;


    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Long getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(Long provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getCityCode() {
        return cityCode;
    }

    public void setCityCode(Long cityCode) {
        this.cityCode = cityCode;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Long getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(Long districtCode) {
        this.districtCode = districtCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

//    public Date getCreateTime() {
//        return createTime;
//    }
//
//    public void setCreateTime(Date createTime) {
//        this.createTime = createTime;
//    }
//
//    public Date getUpdateTime() {
//        return updateTime;
//    }
//
//    public void setUpdateTime(Date updateTime) {
//        this.updateTime = updateTime;
//    }

    @Override
    public String toString() {
        return "AddressEntity{" +
                "id=" + id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", province='" + province + '\'' +
                ", provinceCode=" + provinceCode +
                ", city='" + city + '\'' +
                ", cityCode=" + cityCode +
                ", district='" + district + '\'' +
                ", districtCode=" + districtCode +
                ", address='" + address + '\'' +
                ", isDefault=" + isDefault +
//                ", createTime=" + createTime +
//                ", updateTime=" + updateTime +
                '}';
    }
}
