package com.jinxun.hunting_goods.presentation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.network.bean.shoe.ShoeInfoEntity;
import com.jinxun.hunting_goods.util.GlideUtil;

import java.util.List;


/**
 * Created by zhangyan on 2018/11/22.
 */

public class MainItemViewAdapter extends RecyclerView.Adapter<MainItemViewAdapter.MainItemViewHolder> {

    private List<ShoeInfoEntity> list;
    private Context mContext;
    private int itemPosition;

    public MainItemViewAdapter(List<ShoeInfoEntity> list, Context mContext,int itemPosition) {
        this.list = list;
        this.mContext = mContext;
        this.itemPosition = itemPosition;
    }

    @Override
    public MainItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_recycler,parent,false);
        return new MainItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainItemViewHolder holder, int position) {
        GlideUtil.load(mContext,list.get(itemPosition).getImagesList().get(position), holder.mShoes);
    }

    @Override
    public int getItemCount() {
        return list.get(itemPosition).getImagesList().size();
    }

    class MainItemViewHolder extends RecyclerView.ViewHolder {

        ImageView mShoes;

        MainItemViewHolder(View itemView) {
            super(itemView);
            mShoes = itemView.findViewById(R.id.item_shoe_image);
        }
    }
}
