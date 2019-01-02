package com.jinxun.hunting_goods.weight.pullrefresh.extras;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.jinxun.hunting_goods.weight.pullrefresh.support.impl.Pullable;


@SuppressLint("AppCompatCustomView")
public class PullableImageView extends ImageView implements Pullable {

    public PullableImageView(Context context) {
        super(context);
    }

    public PullableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullableImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    @Override
    public boolean isGetTop() {
        return true;
    }

    @Override
    public boolean isGetBottom() {
        return true;
    }

}
