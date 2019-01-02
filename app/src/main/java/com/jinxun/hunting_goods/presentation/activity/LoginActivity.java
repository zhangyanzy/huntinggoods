package com.jinxun.hunting_goods.presentation.activity;

import android.Manifest;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;

import com.bigkoo.pickerview.TimePickerView;
import com.jinxun.hunting_goods.LWApp;
import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.base.BaseActivity;
import com.jinxun.hunting_goods.databinding.ActivityLoginBinding;
import com.jinxun.hunting_goods.databinding.UserAgreementLayoutBinding;
import com.jinxun.hunting_goods.network.HttpSubscriber;
import com.jinxun.hunting_goods.network.api.account.usercase.GetAccountCase;
import com.jinxun.hunting_goods.network.bean.Response;
import com.jinxun.hunting_goods.util.PermissionUtil;
import com.jinxun.hunting_goods.util.ToastUtil;
import com.jinxun.hunting_goods.weight.NavigationTopBar;
import com.jinxun.hunting_goods.weight.ToastView;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class LoginActivity extends BaseActivity implements NavigationTopBar.NavigationTopBarClickListener, TextWatcher
        , EasyPermissions.PermissionCallbacks {


    public static final String TAG = "LoginActivity";

    private ActivityLoginBinding binding;
    private String mPhoneNumber;
    private BottomSheetDialog mSheetDialog;

    private String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected void initComponent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setPresenter(new Presenter());
    }

    @Override
    protected void loadData(Bundle savedInstanceState) {

    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        binding.loginTopBar.setLeftImageResource(R.mipmap.back_icon);
        binding.loginTopBar.setViewGone();
        binding.loginTopBar.setNavigationTopBarClickListener(this);
    }

    @Override
    protected void initListeners() {
        binding.phoneNumEditText.addTextChangedListener(this);
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

    /**
     * 手机号注册并获取验证码跳转界面
     */

    private void login() {
//        if (mPhoneNumber != null || judgePhone(mPhoneNumber)) {
//            Intent intent = new Intent(this, VerificationCodeActivity.class);
//            intent.putExtra("mPhoneNumber", mPhoneNumber);
//            startActivity(intent);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        PermissionUtil.check(this, perms, 100, getString(R.string.permission_device), getString(R.string.permission_dialog));
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    public class Presenter {
        public void onClick(View view) {
            mPhoneNumber = binding.phoneNumEditText.getText().toString();
            switch (view.getId()) {
                case R.id.user_agreement_root:
                    showBottomSheetDialog();
                    break;
                case R.id.login_btn:
                    login();
                    break;
                case R.id.wechat_btn:
                    ToastView.initToast(getApplicationContext(), R.mipmap.pay_success, "标题", "主题");
                    break;
                case R.id.qq_btn:
                    break;
                case R.id.sina_icon:
                    break;
                default:
                    break;
            }
        }
    }


    private void showBottomSheetDialog() {
        mSheetDialog = new BottomSheetDialog(this);
        UserAgreementLayoutBinding userBinding = DataBindingUtil.inflate(LayoutInflater.from(this),
                R.layout.user_agreement_layout, null, false);
        mSheetDialog.setContentView(userBinding.getRoot());
        if (!mSheetDialog.isShowing())
            mSheetDialog.show();
        userBinding.closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSheetDialog.isShowing())
                    mSheetDialog.dismiss();
            }
        });

    }

}

