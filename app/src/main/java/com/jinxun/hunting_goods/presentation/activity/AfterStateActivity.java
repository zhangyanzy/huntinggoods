package com.jinxun.hunting_goods.presentation.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.base.BaseActivity;
import com.jinxun.hunting_goods.databinding.ActivityAfterStateBinding;
import com.jinxun.hunting_goods.weight.NavigationTopBar;

public class AfterStateActivity extends BaseActivity {

    private ActivityAfterStateBinding binding;


    @Override
    protected void initComponent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_after_state);
    }

    @Override
    protected void loadData(Bundle savedInstanceState) {

    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        binding.afterStateTopBar.setCenterTitleText("售后状态");
        binding.afterStateTopBar.setLeftImageResource(R.mipmap.back_icon);
        binding.afterStateTopBar.setViewGone();

    }

    @Override
    protected void initListeners() {
        binding.afterStateTopBar.setNavigationTopBarClickListener(new NavigationTopBar.NavigationTopBarClickListener() {
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
        public void onClick(View view) {
            switch (view.getId()) {
                default:
                    break;
            }
        }
    }

}
