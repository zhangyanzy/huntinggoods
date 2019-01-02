package com.jinxun.hunting_goods.presentation.adapter;

import android.support.annotation.Nullable;

import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.base.recyclerview.BaseRecyclerViewAdapter;
import com.jinxun.hunting_goods.network.bean.shopping.ConfirmedOrderEntity;
import com.jinxun.hunting_goods.network.bean.shopping.ProductEntity;
import com.jinxun.hunting_goods.presentation.adapter.holder.ConfirmedOrderHolder;

import java.util.ArrayList;

/**
 * Created by zhangyan on 2018/12/18.
 */

public class ConfirmedOrderAdapter extends BaseRecyclerViewAdapter<ProductEntity, ConfirmedOrderHolder> {


    public ConfirmedOrderAdapter() {
        super(R.layout.item_confirmed_order);
    }

    @Override
    protected void convert(ConfirmedOrderHolder helper, ProductEntity item, int position) {
        if (null == item)
            return;
        helper.createItem(item);

    }

    @Override
    public int getItemType(int position) {
        return 0;
    }
}
