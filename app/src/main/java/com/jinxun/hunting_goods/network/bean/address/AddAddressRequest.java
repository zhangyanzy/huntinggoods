package com.jinxun.hunting_goods.network.bean.address;

/**
 * Created by zhangyan on 2018/12/6.
 * 新增用户地址 请求参数
 */

public class AddAddressRequest {
    private Long userId;
    private String name;
    private String phone;
    private String province;
    private String provinceCode;
    private String city;
    private String cityCode;
    private String district;
    private String districtCode;
    private String address;
    private Integer isDefault;//是否默认地址 0否 1是


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

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
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

    @Override
    public String toString() {
        return "AddAddressRequest{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", province='" + province + '\'' +
                ", provinceCode='" + provinceCode + '\'' +
                ", city='" + city + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", district='" + district + '\'' +
                ", districtCode='" + districtCode + '\'' +
                ", address='" + address + '\'' +
                ", isDefault=" + isDefault +
                '}';
    }
}
