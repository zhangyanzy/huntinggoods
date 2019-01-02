package com.jinxun.hunting_goods.presentation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.base.recyclerview.BaseRecyclerViewAdapter;
import com.jinxun.hunting_goods.network.bean.shoe.ShoeColorEntity;
import com.jinxun.hunting_goods.network.bean.shoe.ShoeMaterialEntity;
import com.jinxun.hunting_goods.util.GlideUtil;

import java.util.List;

/**
 * Created by zhangyan on 2018/12/13.
 */

public class MainColorItemViewAdapter extends RecyclerView.Adapter<MainColorItemViewAdapter.MainColorItemViewHolder> {

    private List<ShoeColorEntity> list;
    private Context mContext;
    private int itemPosition;


    public MainColorItemViewAdapter(List<ShoeColorEntity> list, Context mContext, int itemPosition) {
        this.list = list;
        this.mContext = mContext;
        this.itemPosition = itemPosition;
    }

    @Override
    public MainColorItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_recycler, parent, false);
        return new MainColorItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainColorItemViewHolder holder, int position) {
        GlideUtil.load(mContext,list.get(itemPosition).getImgList().get(position),holder.mShoes);
    }

    @Override
    public int getItemCount() {
        return list.get(itemPosition).getImgList().size();
    }

    class MainColorItemViewHolder extends RecyclerView.ViewHolder {
        ImageView mShoes;

        public MainColorItemViewHolder(View itemView) {
            super(itemView);
            mShoes = itemView.findViewById(R.id.item_shoe_image);
        }
    }
}
