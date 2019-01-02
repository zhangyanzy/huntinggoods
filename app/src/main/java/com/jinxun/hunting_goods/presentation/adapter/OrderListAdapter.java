package com.jinxun.hunting_goods.presentation.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.base.recyclerview.BaseRecyclerViewAdapter;
import com.jinxun.hunting_goods.databinding.ItemOrderRootBinding;
import com.jinxun.hunting_goods.databinding.ItemOrderViewBinding;
import com.jinxun.hunting_goods.listener.OnItemListener;
import com.jinxun.hunting_goods.network.bean.order.OrderInfoEntity;
import com.jinxun.hunting_goods.network.bean.order.OrderListEntity;
import com.jinxun.hunting_goods.presentation.adapter.holder.OrderInfoHolder;
import com.jinxun.hunting_goods.util.GlideUtil;

import java.util.ArrayList;

/**
 * Created by zhangyan on 2018/12/24.
 */

public class OrderListAdapter extends BaseRecyclerViewAdapter<OrderListEntity, OrderInfoHolder> {


    public static final int TYPE_TITLE = 0x01;
    public static final int TYPE_CONTENT = 0x02;
    private Context mContext;

    private OnItemListener listener;

    public void setOnItemClickListener(OnItemListener listener){
        this.listener = listener;
    }


    public OrderListAdapter() {
        super(null);
    }

    @Override
    public OrderInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        mContext = parent.getContext();
        ItemOrderViewBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_order_view, parent, false);

        if (viewType == TYPE_TITLE) {
            LinearLayout linearLayout = new LinearLayout(parent.getContext());
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            linearLayout.setLayoutParams(params);

            ItemOrderRootBinding itemOrderRootBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.item_order_root, parent, false);
            linearLayout.addView(itemOrderRootBinding.getRoot());
            linearLayout.addView(binding.getRoot());
            view = linearLayout;
        } else if (viewType == TYPE_CONTENT) {
            view = binding.getRoot();
        }
        return new OrderInfoHolder(view, viewType);
    }

    @Override
    protected void convert(OrderInfoHolder helper, OrderListEntity item, final int position) {
        if (null == helper)
            return;
        if (helper.getItemViewType() == TYPE_TITLE) {
            helper.order_num.setText(list.get(position).getOrderNO());
        }
        OrderListEntity orderListEntity = list.get(position);
        for (int i = 0; i < orderListEntity.getProduct().size(); i++) {
            helper.item_indent_info_name.setText(orderListEntity.getProduct().get(i).getProductName());
            GlideUtil.load(mContext, orderListEntity.getProduct().get(i).getImg(), helper.item_indent_info_image);
            helper.item_indent_info.setText(orderListEntity.getProduct().get(i).getDescribe());
            helper.item_order_money.setText(orderListEntity.getProduct().get(i).getCost() + "");
        }
        if (orderListEntity.getOrderStatus() == 0 && orderListEntity.getOrderStatus() == 1) {
            helper.item_order_state.setText("待付款");
        } else if (orderListEntity.getOrderStatus() == 2) {
            helper.item_order_state.setText("待取件");
        } else if (orderListEntity.getOrderStatus() == 3) {
            helper.item_order_state.setText("待发货");
        } else if (orderListEntity.getOrderStatus() == 4) {
            helper.item_order_state.setText("待收货");
        } else if (orderListEntity.getOrderStatus() == 5) {
            helper.item_order_state.setText("已完成");
        } else if (orderListEntity.getOrderStatus() == 6) {
            helper.item_order_state.setText("售后");
        }
        helper.item_indent_info_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(v, position);
                }
            }
        });
    }

    @Override
    public int getItemType(int position) {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_TITLE;
        OrderListEntity orderListEntity = list.get(position);
        if (orderListEntity.getOrderNO() == null) {
            return TYPE_CONTENT;
        } else {
            return TYPE_TITLE;
        }
    }
}
