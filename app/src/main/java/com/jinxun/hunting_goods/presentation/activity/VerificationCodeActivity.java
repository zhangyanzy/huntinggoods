package com.jinxun.hunting_goods.presentation.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jinxun.hunting_goods.LWApp;
import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.base.BaseActivity;
import com.jinxun.hunting_goods.databinding.ActivityVerificationCodeBinding;
import com.jinxun.hunting_goods.manager.ShortcutMgr;
import com.jinxun.hunting_goods.network.HttpSubscriber;
import com.jinxun.hunting_goods.network.api.account.usercase.GetAccountCase;
import com.jinxun.hunting_goods.network.bean.Response;
import com.jinxun.hunting_goods.network.bean.auth.User;
import com.jinxun.hunting_goods.util.ToastUtil;
import com.jinxun.hunting_goods.weight.EditSmsCodeLayout;
import com.jinxun.hunting_goods.weight.NavigationTopBar;


/**
 * 登录验证码
 */
public class VerificationCodeActivity extends BaseActivity implements NavigationTopBar.NavigationTopBarClickListener {

    private ActivityVerificationCodeBinding binding;
    private String mPhoneNumber;

    private CountDownTimer timer = new CountDownTimer(30000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            binding.againGetCode.setText(millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            binding.againGetCode.setEnabled(true);
            binding.againGetCode.setText(R.string.again_get);
        }
    };

    @Override
    protected void initComponent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_verification_code);
        binding.setPresenter(new Presenter());
        Intent intent = getIntent();
        mPhoneNumber = intent.getStringExtra("mPhoneNumber");
        binding.phoneNumberTv.setText(mPhoneNumber);
    }

    @Override
    protected void loadData(Bundle savedInstanceState) {
        getCodeAgain();
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        binding.verificationCodeTopBar.setLeftImageResource(R.mipmap.back_icon);
        binding.verificationCodeTopBar.setNavigationTopBarClickListener(this);
        binding.verificationCodeTopBar.setViewGone();
    }

    @Override
    protected void initListeners() {
        binding.editSmsLayout.setInputFinishListener(new EditSmsCodeLayout.InputFinishListener() {
            @Override
            public void onInputFinish(String code) {
                /**
                 * 获取验证码登录
                 */
                new GetAccountCase(mPhoneNumber, code).execute(new HttpSubscriber<User>(VerificationCodeActivity.this) {
                    @Override
                    public void onFailure(String errorMsg, Response<User> response) {
                        ToastUtil.showShortToast(getApplicationContext(), response.getMessage());
                    }

                    @Override
                    public void onSuccess(Response<User> response) {
                        ToastUtil.showShortToast(getApplicationContext(), response.getMessage());
                        ShortcutMgr.login(response.getData());
                    }
                });
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    private void getCodeAgain() {
        binding.againGetCode.setEnabled(false);
        timer.start();
    }

    public class Presenter {
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.again_get_code:
                    getCodeAgain();
                    break;
                default:
                    break;
            }
        }
    }
}
