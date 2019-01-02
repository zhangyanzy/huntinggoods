package com.jinxun.hunting_goods.presentation.adapter.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.network.bean.shoe.ShoeInfoEntity;
import com.jinxun.hunting_goods.network.bean.shoe.ShoeMaterialEntity;
import com.jinxun.hunting_goods.util.GlideUtil;

import java.util.List;

/**
 * Created by zhangyan on 2018/12/12.
 */

public class MainMaterialItemViewAdapter extends RecyclerView.Adapter<MainMaterialItemViewAdapter.MainMaterialItemViewHolder> {

    private List<ShoeMaterialEntity> list;
    private Context mContext;
    private int itemPosition;



    public MainMaterialItemViewAdapter(List<ShoeMaterialEntity> list, Context mContext,int itemPosition) {
        this.list = list;
        this.mContext = mContext;
        this.itemPosition = itemPosition;
    }

    @Override
    public MainMaterialItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_recycler,parent,false);
        return new MainMaterialItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainMaterialItemViewHolder holder, int position) {
        GlideUtil.load(mContext,list.get(itemPosition).getImgList().get(position), holder.mShoes);
    }

    @Override
    public int getItemCount() {
        return list.get(itemPosition).getImgList().size();
    }

    class MainMaterialItemViewHolder extends RecyclerView.ViewHolder {

        ImageView mShoes;

        MainMaterialItemViewHolder(View itemView) {
            super(itemView);
            mShoes = itemView.findViewById(R.id.item_shoe_image);
        }
    }
}

