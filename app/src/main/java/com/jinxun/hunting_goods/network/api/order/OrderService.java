package com.jinxun.hunting_goods.network.api.order;

import com.jinxun.hunting_goods.network.bean.Response;
import com.jinxun.hunting_goods.network.bean.order.OrderInfoEntity;
import com.jinxun.hunting_goods.network.bean.order.OrderListEntity;

import java.util.ArrayList;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zhangyan on 2018/12/21.
 */

public interface OrderService {

    /**
     * 订单详情
     */
    @GET("order/detail")
    Observable<Response<OrderInfoEntity>> orderDetail(@Query("token") String token, @Query("orderNO") String orderNO);


    /**
     * 订单列表
     */
    @GET("order/list")
    Observable<Response<ArrayList<OrderListEntity>>> orderListEntity(@Query("token") String token, @Query("orderStatus") Long orderStatus);


    /**
     * 取消订单
     */
    @POST("order/cancelOrder")
    Observable<Response> cancelOrder(@Query("token") String token, @Query("orderNO") String orderNO);

}
