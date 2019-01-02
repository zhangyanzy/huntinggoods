package com.jinxun.hunting_goods.presentation.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.SnapHelper;
import android.widget.LinearLayout;

import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.presentation.adapter.holder.MainHolder;
import com.jinxun.hunting_goods.base.recyclerview.BaseRecyclerViewAdapter;
import com.jinxun.hunting_goods.manager.MyLinearLayoutManager;
import com.jinxun.hunting_goods.network.bean.shoe.ShoeInfoEntity;
import com.jinxun.hunting_goods.weight.LinePagerIndicatorDecoration;

import java.util.ArrayList;

/**
 * Created by zhangyan on 2018/11/21.
 * 第一层recyclerView 外部
 */

public class ShoeInfoAdapter extends BaseRecyclerViewAdapter<ShoeInfoEntity, MainHolder> {

    private MyLinearLayoutManager manager;


    public ShoeInfoAdapter() {
        super(R.layout.root_item_main);
    }

    @Override
    protected void convert(MainHolder helper, ShoeInfoEntity item, int position) {
        if (null != item) {
            helper.mName.setText(item.getName());
            if (item.getImagesList().isEmpty())
                return;
            ArrayList<ShoeInfoEntity> list = new ArrayList<>();
            list.add(item);

            MainItemViewAdapter adapter = new MainItemViewAdapter(list, helper.mItemRv.getContext(), position);
            manager = new MyLinearLayoutManager(helper.mItemRv.getContext(),LinearLayout.HORIZONTAL);
            helper.mItemRv.setLayoutManager(manager);
            helper.mItemRv.addItemDecoration(new LinePagerIndicatorDecoration());
            helper.mItemRv.setItemViewCacheSize(6);
            helper.mItemRv.setAdapter(adapter);
        }
    }

    @Override
    public int getItemType(int position) {
        return 0;
    }
}
