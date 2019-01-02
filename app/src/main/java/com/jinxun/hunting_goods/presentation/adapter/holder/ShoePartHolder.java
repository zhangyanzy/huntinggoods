package com.jinxun.hunting_goods.presentation.adapter.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.base.recyclerview.ClickableViewHolder;
import com.jinxun.hunting_goods.network.bean.shoe.ShoePartEntity;
import com.jinxun.hunting_goods.util.GlideUtil;
import com.jinxun.hunting_goods.util.ToastUtil;

/**
 * Created by zhangyan on 2018/11/23.
 */

public class ShoePartHolder extends ClickableViewHolder {

    private ImageView mPartImage;
    private TextView mPartName;
    private LinearLayout mRoot;


    public ShoePartHolder(View itemView) {
        super(itemView);
        mPartImage = $(R.id.item_part_image);
        mPartName = $(R.id.item_part_text);
        mRoot = $(R.id.part_root);
    }

    public void createItem(final ShoePartEntity entity) {
        if (null == entity)
            return;
        GlideUtil.load(itemView.getContext(), entity.getImg(), mPartImage);
        mPartName.setText(entity.getName());
    }
}
