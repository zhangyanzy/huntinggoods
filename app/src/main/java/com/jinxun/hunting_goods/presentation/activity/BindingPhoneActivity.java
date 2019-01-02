package com.jinxun.hunting_goods.presentation.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.base.BaseActivity;
import com.jinxun.hunting_goods.databinding.ActivityBindingPhoneBinding;
import com.jinxun.hunting_goods.weight.NavigationTopBar;

public class BindingPhoneActivity extends BaseActivity implements NavigationTopBar.NavigationTopBarClickListener {

    private ActivityBindingPhoneBinding binding;

    @Override
    protected void initComponent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_binding_phone);
        binding.setPresenter(new Presenter());
    }

    @Override
    protected void loadData(Bundle savedInstanceState) {

    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        binding.bindingTopBar.setLeftImageResource(R.mipmap.back_icon);
        binding.bindingTopBar.setNavigationTopBarClickListener(this);
        binding.bindingTopBar.setViewGone();

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
