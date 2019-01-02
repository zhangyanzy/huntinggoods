package com.jinxun.hunting_goods.weight;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.databinding.ToastLayoutBinding;

/**
 * Created by zhangyan on 2018/11/29.
 */

public class ToastView extends Toast {

    private static ToastView toastView;


    /**
     * Construct an empty Toast object.  You must call {@link #setView} before you
     * can call {@link #show}.
     *
     * @param context The context to use.  Usually your {@link Application}
     *                or {@link Activity} object.
     */
    private ToastView(Context context) {
        super(context);
    }

    /**
     * 隐藏当前Toast
     */
    public static void cancelToast() {
        if (toastView != null) {
            toastView.cancel();
        }
    }

    public void cancel() {
        super.cancel();
    }

    @Override
    public void show() {
        super.show();
    }

    public static void initToast(Context context, int resId, CharSequence title, CharSequence message) {
        cancelToast();
        toastView = new ToastView(context);
        ToastLayoutBinding binding = DataBindingUtil.inflate((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE),
                R.layout.toast_layout, null, false);
        binding.toastImage.setImageResource(resId);
        binding.toastTitle.setText(title);
        binding.toastMessage.setText(message);
        toastView.setView(binding.getRoot());
        toastView.setGravity(Gravity.CENTER, 0, 0);
        toastView.setDuration(Toast.LENGTH_SHORT);
        ObjectAnimator.ofFloat(binding.toastImage, "rotationY", 0, 360).setDuration(2000).start();
        toastView.show();
    }

}
