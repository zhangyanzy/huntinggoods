package com.jinxun.hunting_goods.presentation.adapter.holder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.base.recyclerview.ClickableViewHolder;
import com.jinxun.hunting_goods.presentation.adapter.OrderListAdapter;

/**
 * Created by zhangyan on 2018/11/26.
 */

public class OrderInfoHolder extends ClickableViewHolder {

    public Button mDelete;

    public TextView order_num;
    public ImageView item_indent_info_image;
    public TextView item_indent_info_name;
    public TextView item_indent_info;
    public TextView item_order_state;
    public TextView item_order_money;

    public RelativeLayout item_indent_info_root;


    int viewType;


    public OrderInfoHolder(View itemView, int viewType) {
        super(itemView);
        if (itemView == null) {
            return;
        }
        this.viewType = viewType;
        if (viewType == OrderListAdapter.TYPE_TITLE){
            order_num = itemView.findViewById(R.id.order_num);
        }
//        mDelete = itemView.findViewById(R.id.item_delete_btn);
        item_indent_info_image = itemView.findViewById(R.id.item_indent_info_image);
        item_indent_info_name = itemView.findViewById(R.id.item_indent_info_name);
        item_indent_info = itemView.findViewById(R.id.item_indent_info);
        item_order_state = itemView.findViewById(R.id.item_order_state);
        item_order_money = itemView.findViewById(R.id.item_order_money);
        item_indent_info_root = itemView.findViewById(R.id.item_indent_info_root);
    }
}
