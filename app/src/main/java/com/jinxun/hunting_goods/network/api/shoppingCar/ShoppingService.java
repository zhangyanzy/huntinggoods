package com.jinxun.hunting_goods.network.api.shoppingCar;

import com.jinxun.hunting_goods.network.bean.Response;
import com.jinxun.hunting_goods.network.bean.shopping.ConfirmedOrderEntity;
import com.jinxun.hunting_goods.network.bean.shopping.PayOrderEntity;
import com.jinxun.hunting_goods.network.bean.shopping.ShoppingCarInfo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zhangyan on 2018/12/17.
 */

public interface ShoppingService {

    /**
     * 购物车列表
     */
    @GET("product/getShoppingCart")
    Observable<Response<ArrayList<ShoppingCarInfo>>> shoppingCarList(@Query("token") String token);

    /**
     * 加入购物车
     */
    @POST("product/insertShoppingCart")
    Observable<Response> addShoppingCar(@Query("token") String token);

    /**
     * 删除购物车
     */
    @DELETE("product/delShoppingCart")
    Observable<Response> deleteShoppingCar(@Query("uniqueCode") String uniqueCode, @Query("token") String token);

    /**
     * 购物车生成订单
     */
    @POST("order/shopGenOrder")
    Observable<Response<ConfirmedOrderEntity>> confirmedOrder(@Query("token") String token, @Query("uniqueCodeList") String[] uniqueCodeList);

    /**
     * 订单支付
     */
    @POST("order/pay")
    Observable<Response<PayOrderEntity>> payOrder(@Query("token") String token, @Query("orderNO") String orderNO,
                                                  @Query("receiveAddId") Long receiveAddId, @Query("deliveryAddId") Long deliveryAddId
            , @Query("receiveStartTime") Long receiveStartTime, @Query("receiveEndTime") Long receiveEndTime);

    /**
     * 支付宝支付
     */
    @POST("pay/alipay")
    Observable<Response<String>> getAlipayCode(@Query("token") String token);

}
