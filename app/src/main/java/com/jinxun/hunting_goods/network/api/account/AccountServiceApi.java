package com.jinxun.hunting_goods.network.api.account;

import com.jinxun.hunting_goods.network.bean.Response;
import com.jinxun.hunting_goods.network.bean.auth.User;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zhangyan on 2018/11/14.
 * 账户管理api
 */

public interface AccountServiceApi {

    /**
     * 账号验证码登录
     */
    @POST("user/registerLogin")
    Observable<Response<User>> login(@Query("phone") String phone, @Query("code") String code);

    /**
     * 意见反馈
     */
    @POST("user/insertSuggestion")
    Observable<Response> sendMessage(@Query("content") String content,@Query("userId") Long userId);
}
