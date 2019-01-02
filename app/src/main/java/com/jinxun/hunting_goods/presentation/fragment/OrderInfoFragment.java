package com.jinxun.hunting_goods.presentation.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.base.BaseFragment;
import com.jinxun.hunting_goods.base.recyclerview.BaseRecyclerViewAdapter;
import com.jinxun.hunting_goods.databinding.FragmentOrderInfoBinding;
import com.jinxun.hunting_goods.listener.OnDeleteClickListener;
import com.jinxun.hunting_goods.listener.OnItemListener;
import com.jinxun.hunting_goods.network.HttpSubscriber;
import com.jinxun.hunting_goods.network.api.order.usercase.OrderListCase;
import com.jinxun.hunting_goods.network.bean.Response;
import com.jinxun.hunting_goods.network.bean.order.OrderInfoEntity;
import com.jinxun.hunting_goods.network.bean.order.OrderListEntity;
import com.jinxun.hunting_goods.presentation.activity.MyOrderActivity;
import com.jinxun.hunting_goods.presentation.activity.OrderInfoActivity;
import com.jinxun.hunting_goods.presentation.adapter.OrderInfoAdapter;
import com.jinxun.hunting_goods.presentation.adapter.OrderListAdapter;
import com.jinxun.hunting_goods.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyan on 2018/11/26.
 * order fragment list
 */

public class OrderInfoFragment extends BaseFragment {

    public final static String TAG = "OrderInfoFragment";

    private FragmentOrderInfoBinding binding;
    private OrderListAdapter mAdapter;
    private int type;


    @Override
    protected View initComponent(LayoutInflater inflater, ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_info, container, false);
        return binding.getRoot();
    }

    public OrderInfoFragment() {

    }

    @SuppressLint("ValidFragment")
    public OrderInfoFragment(int type) {
        this.type = type;
    }

    public static OrderInfoFragment newInstance(int type) {
        return new OrderInfoFragment(type);
    }

    @Override
    protected void loadData(Bundle savedInstanceState) {
        long type = this.type;
        getOrderList(88l, type);
    }

    private void initRecycler(final ArrayList<OrderListEntity> lists) {
        mAdapter = new OrderListAdapter();
        binding.orderRv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.orderRv.setAdapter(mAdapter);
        mAdapter.setList(lists);
        mAdapter.setOnItemClickListener(new OnItemListener() {
            @Override
            public void onItemClick(View view, int position) {
                OrderListEntity orderListEntity = lists.get(position);
                String orderNO = orderListEntity.getOrderNO();
                Intent intent = new Intent(getContext(), OrderInfoActivity.class);
                intent.putExtra("orderNO", orderNO);
                startActivity(intent);
            }
        });
    }


    private void getOrderList(Long userId, Long orderStatus) {
        new OrderListCase(userId, orderStatus).execute(new HttpSubscriber<ArrayList<OrderListEntity>>(getContext()) {
            @Override
            public void onSuccess(Response<ArrayList<OrderListEntity>> response) {
                Log.i(TAG, "onSuccess: " + response.getData());
                initRecycler(response.getData());
            }

            @Override
            public void onFailure(String errorMsg, Response<ArrayList<OrderListEntity>> response) {
                ToastUtil.showShortToast(getContext(), response.getMessage());
            }
        });
    }


    public class Presenter {

    }
}
