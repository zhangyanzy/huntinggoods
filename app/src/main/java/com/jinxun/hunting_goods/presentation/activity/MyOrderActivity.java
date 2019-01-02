package com.jinxun.hunting_goods.presentation.activity;

import android.databinding.DataBindingUtil;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.base.BaseActivity;
import com.jinxun.hunting_goods.base.BaseFragment;
import com.jinxun.hunting_goods.databinding.ActivityMyOrderBinding;
import com.jinxun.hunting_goods.network.HttpSubscriber;
import com.jinxun.hunting_goods.network.api.order.usercase.OrderListCase;
import com.jinxun.hunting_goods.network.bean.Response;
import com.jinxun.hunting_goods.network.bean.order.OrderListEntity;
import com.jinxun.hunting_goods.presentation.adapter.TabPageAdapter;
import com.jinxun.hunting_goods.presentation.fragment.OrderInfoFragment;
import com.jinxun.hunting_goods.util.ToastUtil;
import com.jinxun.hunting_goods.weight.NavigationTopBar;

import java.util.ArrayList;
import java.util.List;

public class MyOrderActivity extends BaseActivity {

    private final static String TAG = "MyOrderActivity";

    private ActivityMyOrderBinding binding;
    private TabPageAdapter mTabAdapter;
    private List<BaseFragment> fragmentList = new ArrayList<>();
    private List<String> mTitle = new ArrayList<>();
    private ArrayList<OrderListEntity> data;

    @Override
    protected void initComponent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_order);
        binding.setPresenter(new Presenter());
    }

    @Override
    protected void loadData(Bundle savedInstanceState) {
        getOrderList(88l, 100l);
    }

    private void getOrderList(Long userId, Long orderStatus) {
        new OrderListCase(userId, orderStatus).execute(new HttpSubscriber<ArrayList<OrderListEntity>>(MyOrderActivity.this) {
            @Override
            public void onSuccess(Response<ArrayList<OrderListEntity>> response) {
                Log.i(TAG, "onSuccess: " + response.getData());
                data = new ArrayList<>();
                data = response.getData();
                initTab(data);
            }

            @Override
            public void onFailure(String errorMsg, Response<ArrayList<OrderListEntity>> response) {
                ToastUtil.showShortToast(getApplicationContext(), response.getMessage());
            }
        });
    }

    private void initTab(ArrayList<OrderListEntity> data) {
        mTitle.add("全部");
        mTitle.add("待付款");
        mTitle.add("待取件");
        mTitle.add("待发货");
        mTitle.add("待收货");
        mTitle.add("已完成");
        mTitle.add("售后");
        if (mTabAdapter == null) {
            mTabAdapter = new TabPageAdapter(getSupportFragmentManager(), fragmentList, mTitle);
        } else {
            mTabAdapter.setFragments(getSupportFragmentManager(), fragmentList, mTitle);
        }
        if (null != data)
            for (int i = 0; i < mTitle.size(); i++) {
                binding.myOrderTabLayout.addTab(binding.myOrderTabLayout.newTab().setText(mTitle.get(i)));
                fragmentList.add(OrderInfoFragment.newInstance(mTabAdapter.getItem(i).getId()));
            }
        binding.myOrderTabLayout.setTabMode(TabLayout.MODE_FIXED);
        binding.myOrderTabLayout.getTabAt(0);
        binding.myOrderViewPager.setAdapter(mTabAdapter);
        binding.myOrderTabLayout.setupWithViewPager(binding.myOrderViewPager);
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        binding.myOrderTopBar.setLeftImageResource(R.mipmap.back_icon);
        binding.myOrderTopBar.setCenterTitleText("我的订单");
        binding.myOrderTopBar.setViewGone();
    }

    @Override
    protected void initListeners() {
        binding.myOrderTopBar.setNavigationTopBarClickListener(new NavigationTopBar.NavigationTopBarClickListener() {
            @Override
            public void leftImageClick() {
                finish();
            }

            @Override
            public void rightImageClick() {

            }

            @Override
            public void alignRightLeftImageClick() {

            }
        });
    }


    public class Presenter {

    }
}
