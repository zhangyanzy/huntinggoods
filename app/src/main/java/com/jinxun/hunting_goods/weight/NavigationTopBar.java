package com.jinxun.hunting_goods.weight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinxun.hunting_goods.R;


/**
 * Created by zhangyan on 2018/8/31.
 * 顶部布局
 */

public class NavigationTopBar extends RelativeLayout implements View.OnClickListener {


    private LayoutInflater mLayoutInflater;
    private View mRootView;
    private ImageView mLeftImage, mRightImage, mAlignRightLeftImage;
    private TextView mTitleText;
    private NavigationTopBarClickListener mNavigationTopBarClickListener;
    private View mView;


    public NavigationTopBar(Context context) {
        super(context);
        initLayout(context);
    }

    public NavigationTopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout(context);
    }

    public NavigationTopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout(context);
    }

    public NavigationTopBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initLayout(context);
    }

    private void initLayout(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mRootView = mLayoutInflater.inflate(R.layout.navigation_top_bar_layout, this, true);
        mTitleText = mRootView.findViewById(R.id.center_text);
        mLeftImage = mRootView.findViewById(R.id.left_image);
        mRightImage = mRootView.findViewById(R.id.right_image);
        mAlignRightLeftImage = mRootView.findViewById(R.id.align_right_image_left_image);
        mView = mRootView.findViewById(R.id.view);

        mLeftImage.setOnClickListener(this);
        mRightImage.setOnClickListener(this);
        mAlignRightLeftImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_image:
                mNavigationTopBarClickListener.leftImageClick();
                break;
            case R.id.right_image:
                mNavigationTopBarClickListener.rightImageClick();
                break;
            case R.id.align_right_image_left_image:
                mNavigationTopBarClickListener.alignRightLeftImageClick();
                break;
            default:
                break;
        }
    }

    public void setNavigationTopBarClickListener(
            @NonNull NavigationTopBarClickListener navigationTopBarClickListener) {
        mNavigationTopBarClickListener = navigationTopBarClickListener;
    }

    public void setLeftImageResource(int resId) {
        mLeftImage.setImageResource(resId);
    }

    public void setLeftImageDrawable(Drawable drawable) {
        mLeftImage.setImageDrawable(drawable);
    }

    public void setLeftImageBitmap(Bitmap bitmap) {
        mLeftImage.setImageBitmap(bitmap);
    }

    public void setLeftImageVisiable(int visiable) {
        mLeftImage.setVisibility(visiable);
    }

    public void setRightImageResource(int resId) {
        mRightImage.setImageResource(resId);
    }

    public void setRightImageDrawable(Drawable drawable) {
        mRightImage.setImageDrawable(drawable);
    }

    public void setRightImageBitmap(Bitmap bitmap) {
        mRightImage.setImageBitmap(bitmap);
    }

    public void setRightImageVisiable(int visiable) {
        mRightImage.setVisibility(visiable);
    }

    public void setCenterTitleText(String title) {
        mTitleText.setText(title);
    }

    public void setCenterTitleText(int resId) {
        mTitleText.setBackgroundResource(resId);
    }

    public void setCenterTitleText(CharSequence charSequence) {
        mTitleText.setText(charSequence);
    }

    public void setCenterTitleVisiable(int visiable) {
        mTitleText.setVisibility(visiable);
    }

    public void setAlignRightLeftImageResource(int resId) {
        mAlignRightLeftImage.setImageResource(resId);
    }

    public void setAlignRightLeftImageDrawable(Drawable drawable) {
        mAlignRightLeftImage.setImageDrawable(drawable);
    }

    public void setAlignRightLeftImageBitmap(Bitmap bitmap) {
        mAlignRightLeftImage.setImageBitmap(bitmap);
    }

    public void setAlignRightLeftImageVisiable(int visiable) {
        mAlignRightLeftImage.setVisibility(visiable);
    }

    public void setCenterTitleTextColor(int colorId) {
        mTitleText.setTextColor(getResources().getColor(colorId));
    }

    public void setViewGone() {
        mView.setVisibility(GONE);
    }


    public interface NavigationTopBarClickListener {
        void leftImageClick();

        void rightImageClick();

        void alignRightLeftImageClick();
    }
}
