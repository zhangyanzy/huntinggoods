package com.jinxun.hunting_goods.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.jinxun.hunting_goods.R;

/**
 * Created by zhangyan on 2018/11/14.
 * 图片加载工具类
 */

public class GlideUtil {

    public static void load(Context context, Object imgPath, final ImageView imageView) {
        Glide.with(context).load(imageView).apply(new RequestOptions().placeholder(R.mipmap.img_placeholder)
                .error(R.mipmap.img_load_failed))
                .load(imgPath).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).into(imageView);
    }
}
