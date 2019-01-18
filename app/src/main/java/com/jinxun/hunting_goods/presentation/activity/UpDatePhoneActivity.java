package com.jinxun.hunting_goods.presentation.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.base.BaseActivity;
import com.jinxun.hunting_goods.databinding.ActivityUpDatePhoneBinding;
import com.jinxun.hunting_goods.network.HttpSubscriber;
import com.jinxun.hunting_goods.network.api.account.usercase.GetCodeCase;
import com.jinxun.hunting_goods.network.api.account.usercase.UpDateCase;
import com.jinxun.hunting_goods.network.bean.Response;
import com.jinxun.hunting_goods.util.Constants;
import com.jinxun.hunting_goods.util.SpUtils;
import com.jinxun.hunting_goods.util.ToastUtil;
import com.jinxun.hunting_goods.weight.EditSmsCodeLayout;
import com.jinxun.hunting_goods.weight.NavigationTopBar;

public class UpDatePhoneActivity extends BaseActivity {

    private ActivityUpDatePhoneBinding binding;
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

    private void getCodeAgain() {
        binding.againGetCode.setEnabled(false);
        timer.start();
        getCode();
    }


    private void getCode() {
        new GetCodeCase(mPhoneNumber).execute(new HttpSubscriber() {
            @Override
            public void onSuccess(Response response) {

            }

            @Override
            public void onFailure(String errorMsg, Response response) {

            }

            @Override
            public void onNext(Object o) {

            }
        });
    }



    @Override
    protected void initComponent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_up_date_phone);
        binding.setPresenter(new Presenter());
    }

    @Override
    protected void loadData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        mPhoneNumber = intent.getStringExtra("mPhoneNumber");
        if (null != mPhoneNumber)
            binding.phoneNumberTv.setText(mPhoneNumber);
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        binding.verificationCodeTopBar.setLeftImageResource(R.mipmap.back_icon);
        binding.verificationCodeTopBar.setViewGone();
        binding.verificationCodeTopBar.setNavigationTopBarClickListener(new NavigationTopBar.NavigationTopBarClickListener() {
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
        binding.editSmsLayout.setInputFinishListener(new EditSmsCodeLayout.InputFinishListener() {
            @Override
            public void onInputFinish(String code) {
                String token = (String) SpUtils.init(Constants.SPREF.TOKEN).get(Constants.SPREF.TOKEN, "");
                new UpDateCase(mPhoneNumber, token, code).execute(new HttpSubscriber(UpDatePhoneActivity.this) {
                    @Override
                    public void onSuccess(Response response) {
                        if (response.getCode().equals("200")) {
                            ToastUtil.showShortToast(getApplicationContext(), "修改成功");
                            openActivity(MainActivity.class);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(String errorMsg, Response response) {

                    }
                });
            }
        });

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
