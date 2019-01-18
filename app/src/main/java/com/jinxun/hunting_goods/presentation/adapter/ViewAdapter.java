package com.jinxun.hunting_goods.presentation.adapter;

import com.jinxun.hunting_goods.ImageViewEntity;
import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.base.recyclerview.BaseRecyclerViewAdapter;

import com.jinxun.hunting_goods.presentation.adapter.holder.ViewHolder;


/**
 * Created by admin on 2019/1/17.
 */

public class ViewAdapter extends BaseRecyclerViewAdapter<String, ViewHolder> {


    public ViewAdapter() {
        super(R.layout.item_main_bottom);
    }

    @Override
    protected void convert(ViewHolder helper, String item, int position) {
        helper.createItem(item);

    }

    @Override
    public int getItemType(int position) {
        return 0;
    }
}
