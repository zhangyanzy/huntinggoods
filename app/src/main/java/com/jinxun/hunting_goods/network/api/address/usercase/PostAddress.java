package com.jinxun.hunting_goods.network.api.address.usercase;

import com.jinxun.hunting_goods.network.BaseUseCase;
import com.jinxun.hunting_goods.network.api.address.AddressServiceApi;
import com.jinxun.hunting_goods.network.bean.address.AddAddressRequest;

import rx.Observable;

/**
 * Created by zhangyan on 2018/12/6.
 */

public class PostAddress extends BaseUseCase<AddressServiceApi> {

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

    public PostAddress(Long userId, String name, String phone, String province, String provinceCode, String city,
                       String cityCode, String district, String districtCode, String address, Integer isDefault) {
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.province = province;
        this.provinceCode = provinceCode;
        this.city = city;
        this.cityCode = cityCode;
        this.district = district;
        this.districtCode = districtCode;
        this.address = address;
        this.isDefault = isDefault;
    }

    @Override
    protected Observable buildCase() {
        return createConnection().postAddress(userId, name, phone, province, provinceCode,
                city, cityCode, district, districtCode, address, isDefault);
    }
}
