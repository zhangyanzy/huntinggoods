package com.jinxun.hunting_goods.presentation.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.base.BaseActivity;
import com.jinxun.hunting_goods.databinding.ActivityPaySuccessBinding;
import com.jinxun.hunting_goods.network.bean.shopping.PayOrderEntity;
import com.jinxun.hunting_goods.weight.NavigationTopBar;

public class PaySuccessActivity extends BaseActivity implements NavigationTopBar.NavigationTopBarClickListener {

    private ActivityPaySuccessBinding binding;
    private PayOrderEntity entity;


    @Override
    protected void initComponent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pay_success);
        binding.setPresenter(new Presenter());
    }

    @Override
    protected void loadData(Bundle savedInstanceState) {
        entity = new PayOrderEntity();
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            entity = (PayOrderEntity) bundle.getSerializable("PayOrderEntity");
            binding.takeCode.setText(entity.getReceiveCode());
            binding.takeTime.setText("快递员预计在" + entity.getReceiveTimeStr() + "之前上门取件");
        }
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        binding.paySuccessTopBar.setLeftImageResource(R.mipmap.back_icon);
        binding.paySuccessTopBar.setViewGone();
        binding.paySuccessTopBar.setCenterTitleText("支付成功");
        binding.paySuccessTopBar.setNavigationTopBarClickListener(this);
    }

    @Override
    protected void initListeners() {

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

    public class Presenter {
        public void onClick(View view) {
            switch (view.getId()) {
                default:
                    break;
            }
        }
    }
}
