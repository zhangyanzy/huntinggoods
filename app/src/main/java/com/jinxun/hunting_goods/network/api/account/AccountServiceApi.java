package com.jinxun.hunting_goods.network.api.account;

import com.jinxun.hunting_goods.network.bean.Response;
import com.jinxun.hunting_goods.network.bean.auth.User;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zhangyan on 2018/11/14.
 * 账户管理api
 */

public interface AccountServiceApi {


    /**
     * 获取验证码
     */
    @POST("user/getCode")
    Observable<Response> getCode(@Query("phone") String phone);

    /**
     * 账号验证码登录
     */
    @POST("user/registerLogin")
    Observable<Response<User>> login(@Query("phone") String phone, @Query("code") String code);

    /**
     * 意见反馈
     */
    @POST("user/insertSuggestion")
    Observable<Response> sendMessage(@Query("content") String content, @Query("token") String token);

    /**
     * 头像上传
     */
    @Multipart
    @POST("user/uploadImg")
    Observable<Response<String>> upLoadImg(@PartMap Map<String, RequestBody> requestBodyMap);

    /**
     * 验证原手机号
     */
    @POST("user/checkOriginPhone")
    Observable<Response> checkPhone(@Query("phone") String phone, @Query("token") String token, @Query("code") String code);

    /**
     * 修改手机号
     */
    @POST("user/updatePhone")
    Observable<Response> updatePhone(@Query("phone") String phone, @Query("token") String token, @Query("code") String code);

    /**
     * 退出登录
     */
    @POST("user/loginOut")
    Observable<Response> loginOut(@Query("token") String token);

    /**
     * 第三方登录 qq
     */
    @POST("user/loginType")
    Observable<Response<User>> loginType(@Query("type") Long type, @Query("qq") String qq);

    /**
     * 第三方登录 wx
     */
    @POST("user/loginType")
    Observable<Response<User>> wxLogin(@Query("type") Long type, @Query("wx") String wx);

}
