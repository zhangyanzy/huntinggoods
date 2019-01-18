package com.jinxun.hunting_goods.presentation.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.base.BaseActivity;
import com.jinxun.hunting_goods.databinding.ActivityBindingPhoneBinding;
import com.jinxun.hunting_goods.databinding.UserAgreementLayoutBinding;
import com.jinxun.hunting_goods.util.ToastUtil;
import com.jinxun.hunting_goods.weight.NavigationTopBar;

public class BindingPhoneActivity extends BaseActivity implements NavigationTopBar.NavigationTopBarClickListener {

    private ActivityBindingPhoneBinding binding;
    private BottomSheetDialog mDialog;

    @Override
    protected void initComponent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_binding_phone);
        binding.setPresenter(new Presenter());
    }

    @Override
    protected void loadData(Bundle savedInstanceState) {

    }


    private void showBottomSheetDialog() {
        mDialog = new BottomSheetDialog(this);
        UserAgreementLayoutBinding userBinding = DataBindingUtil.inflate(LayoutInflater.from(this),
                R.layout.user_agreement_layout, null, false);
        mDialog.setContentView(userBinding.getRoot());
        if (!mDialog.isShowing())
            mDialog.show();
        userBinding.closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDialog.isShowing())
                    mDialog.dismiss();
            }
        });
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


    private void login(String mPhoneNumber) {
        if (TextUtils.isEmpty(mPhoneNumber) || mPhoneNumber == "") {
            ToastUtil.showShortToast(getApplicationContext(), "请输入手机号");
            return;
        }
        if (mPhoneNumber != null || judgePhone(mPhoneNumber)) {
            Intent intent = new Intent(this, VerificationCodeActivity.class);
            intent.putExtra("mPhoneNumber", mPhoneNumber);
            startActivity(intent);
        }
    }

    public class Presenter {
        public void onClick(View view) {
            String mPhoneNum = binding.phoneNumEditText.getText().toString();
            switch (view.getId()) {
                case R.id.login_btn:
                    login(mPhoneNum);
                    break;
                case R.id.user_agreement_root:
                    showBottomSheetDialog();
                    break;
                default:
                    break;
            }
        }
    }
}
