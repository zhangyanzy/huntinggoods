package com.jinxun.hunting_goods.presentation.adapter;

import android.support.annotation.Nullable;

import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.base.recyclerview.BaseRecyclerViewAdapter;
import com.jinxun.hunting_goods.network.bean.shoe.ShoeMaterialEntity;
import com.jinxun.hunting_goods.presentation.adapter.holder.ShoeMaterialHolder;

import java.util.ArrayList;

/**
 * Created by zhangyan on 2018/12/12.
 */

public class ShoeMaterialAdapter extends BaseRecyclerViewAdapter<ShoeMaterialEntity, ShoeMaterialHolder> {


    public ShoeMaterialAdapter() {
        super(R.layout.item_main_bottom);
    }

    @Override
    protected void convert(ShoeMaterialHolder helper, ShoeMaterialEntity item, int position) {
        if (item != null) {
            helper.createItem(item);
        }
    }

    @Override
    public int getItemType(int position) {
        return 0;
    }
}
