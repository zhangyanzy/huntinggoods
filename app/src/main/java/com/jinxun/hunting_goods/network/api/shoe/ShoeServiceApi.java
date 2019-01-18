package com.jinxun.hunting_goods.network.api.shoe;

import com.jinxun.hunting_goods.ImageViewEntity;
import com.jinxun.hunting_goods.network.bean.Response;
import com.jinxun.hunting_goods.network.bean.shoe.ShoeColorEntity;
import com.jinxun.hunting_goods.network.bean.shoe.ShoeInfoEntity;
import com.jinxun.hunting_goods.network.bean.shoe.ShoeMaterialEntity;
import com.jinxun.hunting_goods.network.bean.shoe.ShoePartEntity;

import java.util.ArrayList;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zhangyan on 2018/12/11.
 */

public interface ShoeServiceApi {

    /**
     * 获取首页基础数据
     */
    @GET("product/index")
    Observable<Response<ShoeInfoEntity>> getShoes();

    /**
     * 获取首页部位基础数据
     */
    @GET("product/position")
    Observable<Response<ArrayList<ShoePartEntity>>> getShoePart(@Query("token") String token);

    /**
     * 获取材质
     */
    @GET("product/material")
    Observable<Response<ArrayList<ShoeMaterialEntity>>> getShoeMaterial(@Query("token") String token,@Query("positionId") String positionId);

    /**
     * 获取颜色
     */
    @GET("product/color")
    Observable<Response<ArrayList<ShoeColorEntity>>> getShoeColors(@Query("token") String token,@Query("positionId") Long positionId, @Query("materialId") Long materialId);

    /**
     * 点击完成部位编辑
     */
    @POST("product/prepared")
    Observable<Response<ImageViewEntity>> completePartEdit(@Query("token") String token,
                                                           @Query("productId") String productId, @Query("positionId") String positionId, @Query("materialId") String materialId,
                                                           @Query("colorId") String colorId, @Query("productName") String productName, @Query("positionName") String positionName,
                                                           @Query("materialName") String materialName, @Query("colorName") String colorName);

}
