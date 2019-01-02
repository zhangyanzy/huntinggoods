package com.jinxun.hunting_goods.presentation.adapter;


import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.presentation.adapter.holder.ShoePartHolder;
import com.jinxun.hunting_goods.base.recyclerview.BaseRecyclerViewAdapter;
import com.jinxun.hunting_goods.network.bean.shoe.ShoePartEntity;


/**
 * Created by zhangyan on 2018/11/23.
 * 鞋子部位适配器
 */

public class ShoePartAdapter extends BaseRecyclerViewAdapter<ShoePartEntity, ShoePartHolder> {


    public ShoePartAdapter() {
        super(R.layout.item_main_bottom);
    }

    @Override
    protected void convert(ShoePartHolder helper, ShoePartEntity item, int position) {
        if (null != item)
            helper.createItem(item);
    }

    @Override
    public int getItemType(int position) {
        return 0;
    }
}
