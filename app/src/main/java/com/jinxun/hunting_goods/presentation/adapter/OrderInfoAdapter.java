package com.jinxun.hunting_goods.presentation.adapter;

import android.view.View;

import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.base.recyclerview.BaseRecyclerViewAdapter;
import com.jinxun.hunting_goods.listener.OnDeleteClickListener;
import com.jinxun.hunting_goods.network.bean.order.OrderInfoEntity;
import com.jinxun.hunting_goods.presentation.adapter.holder.OrderInfoHolder;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

/**
 * Created by zhangyan on 2018/11/26.
 */

public class OrderInfoAdapter extends BaseRecyclerViewAdapter<OrderInfoEntity, OrderInfoHolder> {

    private OnDeleteClickListener mDeleteListener;


    public OrderInfoAdapter() {
        super(R.layout.item_order);
    }

    @Override
    protected void convert(final OrderInfoHolder helper, final OrderInfoEntity item, final int position) {
        ((SwipeMenuLayout) helper.itemView).setIos(false).setLeftSwipe(position % 2 == 0 ? true : false);//这句话关掉IOS阻塞式交互效果 并依次打开左滑右滑
        helper.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (null != mDeleteListener) {
//                    mDeleteListener.onDeleteClick(helper.getAdapterPosition());
//                }

                int pos = helper.getLayoutPosition();
                notifyItemRemoved(pos);
            }
        });

    }

    @Override
    public int getItemType(int position) {
        return 0;
    }

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.mDeleteListener = listener;
    }
}
