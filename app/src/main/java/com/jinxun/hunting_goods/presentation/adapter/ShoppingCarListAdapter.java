package com.jinxun.hunting_goods.presentation.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;

import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.base.recyclerview.BaseRecyclerViewAdapter;
import com.jinxun.hunting_goods.network.bean.shopping.ShoppingCarInfo;
import com.jinxun.hunting_goods.presentation.adapter.holder.ShoppingCarListHolder;

import java.util.ArrayList;

/**
 * Created by zhangyan on 2018/12/17.
 */

public class ShoppingCarListAdapter extends BaseRecyclerViewAdapter<ShoppingCarInfo, ShoppingCarListHolder> {

    private onSwipeListener mListener;

    private onSwipeListener getmListener() {
        return mListener;
    }

    public void setListener(onSwipeListener listener) {
        this.mListener = listener;
    }

    public ShoppingCarListAdapter() {
        super(R.layout.item_shopping_car);
    }

    @Override
    protected void convert(final ShoppingCarListHolder helper, final ShoppingCarInfo item, final int position) {
        if (null == item)
            return;
        helper.createItem(item);
        helper.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onDelete(position, item.getUniqueCode());
                }
            }
        });
        helper.mItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onClick(v, position);
                }
            }
        });
        helper.mBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setChoosed(((CheckBox) v).isChecked());
                if (null != mListener) {
                    mListener.onCheckGroup(position, ((CheckBox) v).isChecked());
                }

            }
        });
    }

    @Override
    public int getItemType(int position) {
        return 0;
    }

    public interface onSwipeListener {
        void onDelete(int position, String code);

        void onClick(View view, int position);

        void onCheckGroup(int position, boolean isChecked);
    }
}
