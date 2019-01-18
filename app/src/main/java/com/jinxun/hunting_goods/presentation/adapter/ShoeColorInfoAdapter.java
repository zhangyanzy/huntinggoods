package com.jinxun.hunting_goods.presentation.adapter;

import android.support.annotation.Nullable;
import android.widget.LinearLayout;

import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.base.recyclerview.BaseRecyclerViewAdapter;
import com.jinxun.hunting_goods.manager.MyLinearLayoutManager;
import com.jinxun.hunting_goods.network.bean.shoe.ShoeColorEntity;
import com.jinxun.hunting_goods.presentation.adapter.holder.ShoeColorInfoHolder;
import com.jinxun.hunting_goods.weight.LinePagerIndicatorDecoration;

import java.util.ArrayList;

/**
 * Created by zhangyan on 2018/12/13.
 */

public class ShoeColorInfoAdapter extends BaseRecyclerViewAdapter<ShoeColorEntity, ShoeColorInfoHolder> {

    private MyLinearLayoutManager manager;

    public ShoeColorInfoAdapter() {
        super(R.layout.root_item_main);
    }

    @Override
    protected void convert(ShoeColorInfoHolder helper, ShoeColorEntity item, int position) {
        if (null != item) {
            helper.mName.setText("PLUM FOG");
            if (item.getImgList().isEmpty())
                return;
            ArrayList<ShoeColorEntity> list = new ArrayList<>();
            list.add(item);

            MainColorItemViewAdapter adapter = new MainColorItemViewAdapter(list, helper.mItemRv.getContext(), position);
            manager = new MyLinearLayoutManager(helper.mItemRv.getContext(), LinearLayout.HORIZONTAL);
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
