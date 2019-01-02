package com.jinxun.hunting_goods.network;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.manager.TokenMgr;
import com.jinxun.hunting_goods.network.exception.ApiException;
import com.jinxun.hunting_goods.network.exception.TokenExpiredException;
import com.jinxun.hunting_goods.util.GsonUtil;
import com.jinxun.hunting_goods.util.IsEmpty;
import com.jinxun.hunting_goods.util.NetworkUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zhangyan on 2018/11/14.
 */

public class ApiClient {

    private static Context context;
    private static String baseUrl;

    private static Gson gson = GsonUtil.getGsonInstance(false);

    public static void init(Context context, String baseUrl) {
        ApiClient.baseUrl = baseUrl;
        ApiClient.context = context;
    }

    public static Interceptor setUserCookie = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request;
            String token = TokenMgr.getToken();
            if (!TextUtils.isEmpty(token)) {
                request = chain.request().newBuilder().addHeader("Cookie", token).build();
            } else {
                request = chain.request();
            }
            return chain.proceed(request);
        }
    };

    /**
     * 请求获取token
     */
    private static Interceptor getUserCookie = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            String cookie = response.headers().get("Set-Cookie");
            if (!IsEmpty.string(cookie) && cookie.contains("JSESSIONID=")) {
                TokenMgr.updateToken(cookie);
            }
            return response;
        }
    };

    private static Interceptor requestErrorInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            if (!NetworkUtil.isConnected(context)) {
                throw new ApiException(0, context.getString(R.string.newwork_err));
            }
            Request request = chain.request();
            Response response = chain.proceed(request);
            ApiException e = null;
            if (401 == response.code()) {
                throw new TokenExpiredException(401, context.getString(R.string.newwork_request_err_401));
            } else if (403 == response.code()) {
                e = new ApiException(403, context.getString(R.string.newwork_request_err_403));
            } else if (503 == response.code()) {
                e = new ApiException(503, context.getString(R.string.newwork_request_err_503));
            } else if (500 == response.code()) {
                e = new ApiException(500, context.getString(R.string.newwork_request_err_500));
            } else if (404 == response.code()) {
                e = new ApiException(404, context.getString(R.string.newwork_request_err_404));
            }
            if (e != null) {
                throw e;
            }
            return response;
        }
    };


    public static Retrofit instance(boolean useMock) {//boolean useMock
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okClient = new OkHttpClient.Builder().retryOnConnectionFailure(true)
                .addInterceptor(logging).addInterceptor(getUserCookie).addInterceptor(setUserCookie)
                .addInterceptor(requestErrorInterceptor)
                .addInterceptor(new MockInterceptor(context, useMock)).connectTimeout(60, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true).writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS).build();

        Retrofit retrofit = new Retrofit.Builder().client(okClient).baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        return retrofit;
    }

}
