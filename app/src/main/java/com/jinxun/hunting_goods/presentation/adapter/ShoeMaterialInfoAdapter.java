package com.jinxun.hunting_goods.presentation.adapter;

import android.support.annotation.Nullable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.base.recyclerview.BaseRecyclerViewAdapter;
import com.jinxun.hunting_goods.manager.MyLinearLayoutManager;
import com.jinxun.hunting_goods.network.bean.shoe.ShoeMaterialEntity;
import com.jinxun.hunting_goods.presentation.adapter.holder.MainMaterialItemViewAdapter;
import com.jinxun.hunting_goods.presentation.adapter.holder.ShoeMaterialInfoHolder;
import com.jinxun.hunting_goods.weight.LinePagerIndicatorDecoration;

import java.util.ArrayList;

/**
 * Created by zhngyan on 2018/12/12.
 */

public class ShoeMaterialInfoAdapter extends BaseRecyclerViewAdapter<ShoeMaterialEntity, ShoeMaterialInfoHolder> {

    private MyLinearLayoutManager manager;

    public ShoeMaterialInfoAdapter() {
        super(R.layout.root_item_main);
    }

    @Override
    protected void convert(ShoeMaterialInfoHolder helper, ShoeMaterialEntity item, int position) {
        if (null != item)
            helper.mName.setText(item.getProductName());
            if (item.getImgList().isEmpty())
                return;
            ArrayList<ShoeMaterialEntity> list = new ArrayList<>();
            list.add(item);

            MainMaterialItemViewAdapter adapter = new MainMaterialItemViewAdapter(list, helper.mItemRv.getContext(), position);
            manager = new MyLinearLayoutManager(helper.mItemRv.getContext(), LinearLayout.HORIZONTAL);
            helper.mItemRv.setLayoutManager(manager);
            helper.mItemRv.addItemDecoration(new LinePagerIndicatorDecoration());
            helper.mItemRv.setItemViewCacheSize(6);
            helper.mItemRv.setAdapter(adapter);
        }


    @Override
    public int getItemType(int position) {
        return 0;
    }
}
