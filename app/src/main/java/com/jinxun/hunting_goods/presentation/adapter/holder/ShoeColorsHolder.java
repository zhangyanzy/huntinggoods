package com.jinxun.hunting_goods.presentation.adapter.holder;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.base.recyclerview.ClickableViewHolder;
import com.jinxun.hunting_goods.network.bean.shoe.ShoeColorEntity;
import com.jinxun.hunting_goods.util.GlideUtil;

/**
 * Created by zhangyan on 2018/12/13.
 */

public class ShoeColorsHolder extends ClickableViewHolder {


    private ImageView mPartImage;
    private TextView mPartName;
    private LinearLayout mRoot;


    public ShoeColorsHolder(View itemView) {
        super(itemView);
        mPartImage = $(R.id.item_part_image);
        mPartName = $(R.id.item_part_text);
        mRoot = $(R.id.part_root);
    }

    public void createItem(ShoeColorEntity entity) {
        mPartImage.setBackgroundColor(Color.parseColor(entity.getHexadecimal()));
        mPartName.setText(entity.getColorName());
    }
}
