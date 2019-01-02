package com.jinxun.hunting_goods.presentation.adapter.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.base.recyclerview.ClickableViewHolder;
import com.jinxun.hunting_goods.network.bean.shopping.ProductEntity;
import com.jinxun.hunting_goods.util.GlideUtil;

/**
 * Created by admin on 2018/12/18.
 */

public class ConfirmedOrderHolder extends ClickableViewHolder {

    private RelativeLayout mRoot;
    private ImageView mImageView;
    private TextView mName;
    private TextView mTexture;
    private TextView mPrice;

    public ConfirmedOrderHolder(View itemView) {
        super(itemView);
        mRoot = $(R.id.indent_info_root);
        mImageView = $(R.id.indent_info_image);
        mName = $(R.id.indent_info_name);
        mTexture = $(R.id.indent_info_texture);
        mPrice = $(R.id.indent_info_price);
    }

    public void createItem(ProductEntity item) {
        if (item != null) {
            GlideUtil.load(itemView.getContext(), item.getImg(), mImageView);
            mName.setText(item.getProductName());
            mTexture.setText(item.getDescribe());
            mPrice.setText(item.getCost() + "");
        }
    }
}
