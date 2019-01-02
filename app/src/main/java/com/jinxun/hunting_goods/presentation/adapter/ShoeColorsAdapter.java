package com.jinxun.hunting_goods.presentation.adapter;

import android.support.annotation.Nullable;

import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.base.recyclerview.BaseRecyclerViewAdapter;
import com.jinxun.hunting_goods.network.bean.shoe.ShoeColorEntity;
import com.jinxun.hunting_goods.presentation.adapter.holder.ShoeColorsHolder;

import java.util.ArrayList;

/**
 * Created by admin on 2018/12/13.
 */

public class ShoeColorsAdapter extends BaseRecyclerViewAdapter<ShoeColorEntity, ShoeColorsHolder> {


    public ShoeColorsAdapter() {
        super(R.layout.item_main_bottom);
    }

    @Override
    protected void convert(ShoeColorsHolder helper, ShoeColorEntity item, int position) {
        if (null != item) {
            helper.createItem(item);
        }
    }

    @Override
    public int getItemType(int position) {
        return 0;
    }
}
