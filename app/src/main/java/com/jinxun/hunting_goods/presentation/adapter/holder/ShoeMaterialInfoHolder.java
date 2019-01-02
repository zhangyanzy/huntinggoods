package com.jinxun.hunting_goods.presentation.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.base.recyclerview.ClickableViewHolder;

/**
 * Created by zhangyan on 2018/12/12.
 */

public class ShoeMaterialInfoHolder extends ClickableViewHolder {

    public TextView mName;
    public RecyclerView mItemRv;

    public ShoeMaterialInfoHolder(View itemView) {
        super(itemView);
        if (itemView == null)
            return;
        mName = $(R.id.root_item_shoe_name);
        mItemRv = $(R.id.item_rv);
    }
}
