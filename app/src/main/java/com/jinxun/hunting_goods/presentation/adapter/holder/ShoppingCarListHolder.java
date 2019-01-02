package com.jinxun.hunting_goods.presentation.adapter.holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.base.recyclerview.ClickableViewHolder;
import com.jinxun.hunting_goods.network.bean.shopping.ShoppingCarInfo;
import com.jinxun.hunting_goods.util.GlideUtil;

/**
 * Created by zhangyan on 2018/12/17.
 */

public class ShoppingCarListHolder extends ClickableViewHolder {

    public CheckBox mBox;
    private ImageView mView;
    private TextView mName;
    private TextView mInfo;
    private TextView mPrice;
    public Button mDelete;
    public RelativeLayout mItem;

    public ShoppingCarListHolder(View itemView) {
        super(itemView);
        mBox = $(R.id.item_shopping_car_check);
        mView = $(R.id.item_shopping_car_image);
        mName = $(R.id.item_name);
        mInfo = $(R.id.item_info);
        mPrice = $(R.id.item_price);
        mDelete = $(R.id.item_delete);
        mItem = $(R.id.item_root);
    }

    public void createItem(ShoppingCarInfo info) {
        if (null == info)
            return;
        mName.setText(info.getProductName());
        mInfo.setText(info.getDescribe());
        mPrice.setText(info.getCost() + "");
        GlideUtil.load(itemView.getContext(), info.getImg(), mView);
        if (info.isChoosed()) {
            mBox.setChecked(true);
        } else {
            mBox.setChecked(false);
        }
    }
}
