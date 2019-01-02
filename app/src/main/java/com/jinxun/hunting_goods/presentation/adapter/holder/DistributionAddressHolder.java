package com.jinxun.hunting_goods.presentation.adapter.holder;

import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.base.recyclerview.ClickableViewHolder;
import com.jinxun.hunting_goods.network.bean.address.AddressEntity;
import com.jinxun.hunting_goods.presentation.activity.EditAddressActivity;

import org.w3c.dom.Text;

/**
 * Created by zhangyan on 2018/11/27.
 */

public class DistributionAddressHolder extends ClickableViewHolder {

    public CheckBox mSetAddress;
    public TextView mName;
    public TextView mNum;
    public TextView mIsDefault;
    public TextView mAddressInfo;
    public TextView mItemCompile;

    public DistributionAddressHolder(View itemView) {
        super(itemView);
        mSetAddress = $(R.id.item_set_address);
        mName = $(R.id.item_name);
        mNum = $(R.id.item_num);
        mIsDefault = $(R.id.item_isDefault);
        mAddressInfo = $(R.id.item_address_info);
        mItemCompile = $(R.id.item_compile);
    }

    public void createItem(final AddressEntity entity) {
        if (null == entity)
            return;
        mName.setText(entity.getName());
        mNum.setText(entity.getPhone());
        String address = entity.getProvince() + entity.getCity() + entity.getDistrict()
                + entity.getAddress();
        mAddressInfo.setText(address);
        if (entity.getIsDefault() == 1) {
            mIsDefault.setVisibility(View.VISIBLE);
        } else {
            mIsDefault.setVisibility(View.GONE);
        }
        mItemCompile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(itemView.getContext(), EditAddressActivity.class);
                String id = String.valueOf(entity.getId());
                intent.putExtra("id", id);
                itemView.getContext().startActivity(intent);
            }
        });
        mSetAddress.setChecked(entity.isChoose());

    }

}
