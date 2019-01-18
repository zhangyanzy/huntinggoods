package com.jinxun.hunting_goods.presentation.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;

import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.base.recyclerview.BaseRecyclerViewAdapter;
import com.jinxun.hunting_goods.network.bean.address.AddressEntity;
import com.jinxun.hunting_goods.presentation.adapter.holder.DistributionAddressHolder;

/**
 * Created by zhangyan on 2018/11/27.
 */

public class DistributionAddressAdapter extends BaseRecyclerViewAdapter<AddressEntity, DistributionAddressHolder> {

    private onCheckListener mListener;

    public void setListener(onCheckListener listener) {
        this.mListener = listener;
    }

    public DistributionAddressAdapter() {
        super(R.layout.item_distribution_address);
    }

    @Override
    protected void convert(final DistributionAddressHolder helper, final AddressEntity item, final int position) {
        if (null == item)
            return;
        helper.createItem(item);
        helper.mSetAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setChoose(((CheckBox) v).isChecked());
                if (mListener != null)
                    mListener.onCheck(position, ((CheckBox) v).isChecked(), helper.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemType(int position) {
        return 0;
    }


    public interface onCheckListener {

        void onCheck(int position, boolean isChecked, int which);
    }
}
