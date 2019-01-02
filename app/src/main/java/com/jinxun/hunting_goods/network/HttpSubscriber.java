package com.jinxun.hunting_goods.network;

import android.content.Context;

import com.jinxun.hunting_goods.network.bean.Response;
import com.jinxun.hunting_goods.network.exception.ApiException;
import com.jinxun.hunting_goods.network.exception.TokenExpiredException;
import com.jinxun.hunting_goods.util.DialogUtil;
import com.jinxun.hunting_goods.weight.sweetAlert.SweetAlertDialog;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

/**
 * Created by zhangyan on 2018/11/14.
 */

public abstract class HttpSubscriber<T> extends rx.Subscriber<Response<T>> {

    private Context context;
    private SweetAlertDialog dialog;

    public HttpSubscriber() {

    }

    public HttpSubscriber(Context context) {
        this.context = context;
//        if (context != null)
//            dialog = DialogUtil.getProgressDialog(context);
    }

    @Override
    public void onCompleted() {
        if (dialog != null && context != null)
            dialog.dismiss();
        if (isUnsubscribed()) {
            unsubscribe();
        }
    }

    @Override
    public void onError(Throwable throwable) {
        if (dialog != null && context != null)
            dialog.dismiss();
        if (throwable instanceof TokenExpiredException) {
            EventBus.getDefault().post(new AuthEvent(AuthEvent.TOKEN_EXPIRED));
            return;
        }
        onFailure(parseException(throwable), null);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (dialog != null && context != null)
            dialog.show();
    }

    @Override
    public void onNext(Response<T> t) {
        if (dialog != null && context != null)
            dialog.dismiss();
        if (t.getCode().equals("200")) {
            onSuccess(t);
        } else {
            if (t.getMessage() != null) {
                onFailure(t.getMessage(), t);
            } else {
                onFailure(null, t);
            }
        }

    }
    public abstract void onSuccess(Response<T> response);

    public abstract void onFailure(String errorMsg, Response<T> response);


    private String parseException(Throwable throwable) {
        String errorMessage;
        throwable.printStackTrace();
        if (throwable instanceof SocketTimeoutException) {
            errorMessage = "网络链接超时,请稍后重试!";
        } else if (throwable instanceof ConnectException) {
            errorMessage = "网络链接失败,请检查网络设置!";
        } else if (throwable instanceof JSONException) {
            errorMessage = "数据解析失败,请稍候重试!";
        } else if (throwable instanceof ApiException) {
            errorMessage = throwable.getMessage();
        } else if (throwable instanceof TokenExpiredException) {
            errorMessage = throwable.getMessage();
            EventBus.getDefault().post(new AuthEvent(AuthEvent.TOKEN_EXPIRED));
        } else {
            errorMessage = "未知错误!";
        }
        return errorMessage;
    }

}
