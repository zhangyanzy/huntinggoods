package com.jinxun.hunting_goods.presentation.adapter.holder;

import android.view.View;
import android.widget.ImageView;

import com.jinxun.hunting_goods.ImageViewEntity;
import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.base.recyclerview.ClickableViewHolder;
import com.jinxun.hunting_goods.util.GlideUtil;


/**
 * Created by admin on 2019/1/17.
 */

public class ViewHolder extends ClickableViewHolder {


    private ImageView showViews;

    public ViewHolder(View itemView) {
        super(itemView);
        showViews = $(R.id.item_part_image);
    }

    public void createItem(String shoe) {
        GlideUtil.load(itemView.getContext(), shoe, showViews);
    }

}
