package com.jinxun.hunting_goods.presentation.adapter.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.base.recyclerview.ClickableViewHolder;
import com.jinxun.hunting_goods.network.bean.shoe.ShoeMaterialEntity;
import com.jinxun.hunting_goods.util.GlideUtil;

/**
 * Created by zhangyan on 2018/12/12.
 */

public class ShoeMaterialHolder extends ClickableViewHolder {

    private ImageView mPartImage;
    private TextView mPartName;
    private LinearLayout mRoot;


    public ShoeMaterialHolder(View itemView) {
        super(itemView);
        mPartImage = $(R.id.item_part_image);
        mPartName = $(R.id.item_part_text);
        mRoot = $(R.id.part_root);
    }

    public void createItem(ShoeMaterialEntity item) {
        if (null == item)
            return;
        GlideUtil.load(itemView.getContext(), item.getMaterialImage(), mPartImage);
        mPartName.setText(item.getMaterialName());
    }
}
