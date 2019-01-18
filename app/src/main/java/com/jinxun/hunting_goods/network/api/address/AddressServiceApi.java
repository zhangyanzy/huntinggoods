package com.jinxun.hunting_goods.network.api.address;

import com.jinxun.hunting_goods.network.bean.Response;
import com.jinxun.hunting_goods.network.bean.address.AddAddressRequest;
import com.jinxun.hunting_goods.network.bean.address.AddressEntity;
import com.jinxun.hunting_goods.network.bean.address.ProvinceEntity;

import java.util.ArrayList;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zhangyan on 2018/12/5.
 */

public interface AddressServiceApi {

    /**
     * 省份获取
     */
    @GET("commonData/getProvince")
    Observable<Response<ArrayList<ProvinceEntity>>> getProvince();

    /**
     * 市 获取
     */
    @GET("commonData/getCity")
    Observable<Response<ArrayList<ProvinceEntity>>> getCity(@Query("parentId") Long parentId);

    /**
     * 区域获取
     */
    @GET("commonData/getDistrict")
    Observable<Response<ArrayList<ProvinceEntity>>> getArea(@Query("parentId") Long parentId);

    /**
     * 获取收货地址list
     */
    @GET("user/getAddress")
    Observable<Response<ArrayList<AddressEntity>>> getAddress(@Query("token") String token);

    /**
     * 获取单条地址
     */
    @GET("user/getAddressById")
    Observable<Response<AddressEntity>> getAddressById(@Query("id") String id,@Query("token") String token);

    /**
     * 新增用户地址
     */
    @POST("user/insertAddress")
    Observable<Response> postAddress(@Query("token") String token, @Query("name") String name, @Query("phone") String phone,
                                     @Query("province") String province, @Query("provinceCode") String provinceCode, @Query("city") String city,
                                     @Query("cityCode") String cityCode,@Query("district") String district,@Query("districtCode") String districtCode,
                                     @Query("address") String address,@Query("isDefault") Integer isDefault);

    /**
     * 删除用户地址
     */
    @DELETE("user/delAddress")
    Observable<Response> deleteAddress(@Query("id") String id,@Query("token") String token);

    /**
     * 修改用户地址
     */
    @PUT("user/updateAddress")
    Observable<Response> upDateAddress(@Query("id") Long id,@Query("token") String token, @Query("name") String name, @Query("phone") String phone,
                                       @Query("province") String province, @Query("provinceCode") String provinceCode, @Query("city") String city,
                                       @Query("cityCode") String cityCode,@Query("district") String district,@Query("districtCode") String districtCode,
                                       @Query("address") String address,@Query("isDefault") Integer isDefault);

}
