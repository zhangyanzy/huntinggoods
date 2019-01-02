package com.jinxun.hunting_goods.presentation.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.base.BaseActivity;
import com.jinxun.hunting_goods.databinding.ActivityDistributionAddressBinding;
import com.jinxun.hunting_goods.network.HttpSubscriber;
import com.jinxun.hunting_goods.network.api.address.usercase.GetAddress;
import com.jinxun.hunting_goods.network.bean.Response;
import com.jinxun.hunting_goods.network.bean.address.AddAddressRequest;
import com.jinxun.hunting_goods.network.bean.address.AddressEntity;
import com.jinxun.hunting_goods.presentation.adapter.DistributionAddressAdapter;
import com.jinxun.hunting_goods.util.ToastUtil;
import com.jinxun.hunting_goods.weight.NavigationTopBar;
import com.jinxun.hunting_goods.weight.pullrefresh.layout.BaseFooterView;
import com.jinxun.hunting_goods.weight.pullrefresh.layout.BaseHeaderView;
import com.ns.yc.ycstatelib.StateLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class DistributionAddressActivity extends BaseActivity implements NavigationTopBar.NavigationTopBarClickListener {

    private ActivityDistributionAddressBinding binding;
    private DistributionAddressAdapter mAdapter;
    private ArrayList<AddressEntity> addresslists;


    @Override
    protected void initComponent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_distribution_address);
        binding.setPresenter(new Presenter());
    }

    @Override
    protected void loadData(Bundle savedInstanceState) {
        getAddress();
    }

    /**
     * 获取收货地址
     */
    private void getAddress() {
        new GetAddress(88l).execute(new HttpSubscriber<ArrayList<AddressEntity>>(DistributionAddressActivity.this) {
            @Override
            public void onSuccess(Response<ArrayList<AddressEntity>> response) {
                initRecycler(response.getData());
            }

            @Override
            public void onFailure(String errorMsg, Response<ArrayList<AddressEntity>> response) {
                ToastUtil.showShortToast(getApplicationContext(), response.getMessage());
            }
        });
    }

    @Override
    public void initStatusLayout() {
        super.initStatusLayout();
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        binding.distributionAddressTopBar.setLeftImageResource(R.mipmap.back_icon);
        binding.distributionAddressTopBar.setCenterTitleText("配送地址");
        binding.distributionAddressTopBar.setViewGone();
        binding.distributionAddressTopBar.setNavigationTopBarClickListener(this);
    }

    @Override
    protected void initListeners() {
        binding.distributionAddressRefresh.setOnRefreshListener(new BaseHeaderView.OnRefreshListener() {
            @Override
            public void onRefresh(BaseHeaderView baseHeaderView) {

            }
        });
        binding.distributionAddressRefresh.setOnLoadListener(new BaseFooterView.OnLoadListener() {
            @Override
            public void onLoad(BaseFooterView baseFooterView) {

            }
        });
    }

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


    private void initRecycler(final ArrayList<AddressEntity> list) {
        mAdapter = new DistributionAddressAdapter();
        binding.distributionAddressRv.setLayoutManager(new LinearLayoutManager(this));
        binding.distributionAddressRv.setAdapter(mAdapter);
        mAdapter.setList(list);
        mAdapter.setListener(new DistributionAddressAdapter.onCheckListener() {
            @Override
            public void onCheck(int position, boolean isChecked, int which) {
                if (isChecked) {
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setChoose(i == which);
                    }
                    AddressEntity entity = list.get(position);
                    Intent intent = new Intent();
                    intent.putExtra("addressReturn",entity);
                    setResult(RESULT_OK,intent);
                    finish();
                } else {
                    ToastUtil.showShortToast(getApplicationContext(), "取消选中" + position);
                }
                mAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getAddress();
    }

    public class Presenter {
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.add_address_btn:
                    openActivity(EditAddressActivity.class);
                    break;
                default:
                    break;
            }
        }
    }
}
