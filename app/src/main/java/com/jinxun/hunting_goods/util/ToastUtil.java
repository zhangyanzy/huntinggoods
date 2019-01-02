package com.jinxun.hunting_goods.util;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.jinxun.hunting_goods.weight.ToastView;

/**
 * Created by zhngyan on 2018/11/19.
 */

public class ToastUtil {

    private static Toast toast;
    private static View view;

    private ToastUtil() {
    }

    private static void getToast(Context context) {
        if (toast == null) {
            toast = new Toast(context);
        }
        if (view == null) {
            view = Toast.makeText(context, "", Toast.LENGTH_SHORT).getView();
        }
        toast.setView(view);
    }

    public static void showShortToast(Context context, CharSequence msg) {
        showToast(context.getApplicationContext(), msg, Toast.LENGTH_SHORT);
    }

    public static void showShortToast(Context context, int resId) {
        showToast(context.getApplicationContext(), resId, Toast.LENGTH_SHORT);
    }

    public static void showLongToast(Context context, CharSequence msg) {
        showToast(context.getApplicationContext(), msg, Toast.LENGTH_SHORT);
    }

    public static void showLongToast(Context context, int resId) {
        showToast(context.getApplicationContext(), resId, Toast.LENGTH_LONG);
    }

    public static void showToastView(Context context, int resId, CharSequence title, CharSequence message) {
        ToastView.initToast(context, resId, title, message);
    }

    private static void showToast(Context context, CharSequence msg, int duration) {
        try {
            getToast(context);
            toast.setText(msg);
            toast.setDuration(duration);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private static void showToast(Context context, int resId, int duration) {
        try {
            if (resId == 0) {
                return;
            }
            getToast(context);
            toast.setText(resId);
            toast.setDuration(duration);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void cancelToast() {
        if (toast != null) {
            toast.cancel();
        }
    }

}
