package com.jinxun.hunting_goods.presentation.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.base.BaseActivity;
import com.jinxun.hunting_goods.databinding.ActivityBindingNewPhoneBinding;
import com.jinxun.hunting_goods.util.ToastUtil;
import com.jinxun.hunting_goods.weight.NavigationTopBar;

public class BindingNewPhoneActivity extends BaseActivity {

    private ActivityBindingNewPhoneBinding binding;

    @Override
    protected void initComponent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_binding_new_phone);
        binding.setPresenter(new Presenter());
    }

    @Override
    protected void loadData(Bundle savedInstanceState) {

    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        binding.loginTopBar.setViewGone();
        binding.loginTopBar.setLeftImageResource(R.mipmap.back_icon);
        binding.loginTopBar.setNavigationTopBarClickListener(new NavigationTopBar.NavigationTopBarClickListener() {
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

    @Override
    protected void initListeners() {

    }


    public class Presenter {
        public void onClick(View view) {
            String mPhoneNumber = binding.phoneNumEditText.getText().toString();
            switch (view.getId()) {
                case R.id.binding_btn:
                    if (TextUtils.isEmpty(mPhoneNumber) || mPhoneNumber == "") {
                        ToastUtil.showShortToast(getApplicationContext(), "请输入手机号");
                        return;
                    }
                    if (mPhoneNumber != null || judgePhone(mPhoneNumber)) {
                        Intent intent = new Intent(BindingNewPhoneActivity.this, UpDatePhoneActivity.class);
                        intent.putExtra("mPhoneNumber", mPhoneNumber);
                        startActivity(intent);
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
