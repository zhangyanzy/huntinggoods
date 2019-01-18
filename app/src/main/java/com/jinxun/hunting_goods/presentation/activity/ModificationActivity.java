package com.jinxun.hunting_goods.presentation.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.base.BaseActivity;
import com.jinxun.hunting_goods.databinding.ActivityModificationBinding;
import com.jinxun.hunting_goods.network.HttpSubscriber;
import com.jinxun.hunting_goods.network.api.account.usercase.CheckPhoneCase;
import com.jinxun.hunting_goods.network.api.account.usercase.GetCodeCase;
import com.jinxun.hunting_goods.network.bean.Response;
import com.jinxun.hunting_goods.util.Constants;
import com.jinxun.hunting_goods.util.SpUtils;
import com.jinxun.hunting_goods.util.ToastUtil;
import com.jinxun.hunting_goods.weight.EditSmsCodeLayout;
import com.jinxun.hunting_goods.weight.NavigationTopBar;

public class ModificationActivity extends BaseActivity {


    public static final String TAG = "ModificationActivity";


    private ActivityModificationBinding binding;
    private Intent intent;
    private String mPhoneNumber;
    private String mCode;

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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_modification);
        binding.setPresenter(new Presenter());
    }

    @Override
    protected void loadData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        mPhoneNumber = intent.getStringExtra("mPhoneNumber");
        if (null == mPhoneNumber)
            return;
        binding.phoneNumberTv.setText(mPhoneNumber);
        getCode();

    }


    private void getCode() {
        new GetCodeCase(mPhoneNumber).execute(new HttpSubscriber() {
            @Override
            public void onSuccess(Response response) {
                ToastUtil.showShortToast(getApplicationContext(), "验证码获取成功");

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
                new CheckPhoneCase(mPhoneNumber, token, code).execute(new HttpSubscriber(ModificationActivity.this) {
                    @Override
                    public void onSuccess(Response response) {
                        switch (response.getCode()) {
                            case "200":
                                intent = new Intent(ModificationActivity.this, BindingNewPhoneActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                            case "-1":
                                ToastUtil.showShortToast(getApplicationContext(), response.getMessage());
                                intent = new Intent(ModificationActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                            case "301":
                                ToastUtil.showShortToast(getApplicationContext(), response.getMessage());
                                getCode();
                                break;
                            default:
                                break;
                        }
                    }

                    @Override
                    public void onFailure(String errorMsg, Response response) {
                        ToastUtil.showShortToast(getApplicationContext(), response.getMessage());
                    }
                });
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    private void getCodeAgain() {
        binding.againGetCode.setEnabled(false);
        timer.start();
        getCode();
    }


    public class Presenter {
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.again_get_code:
                    getCodeAgain();
                default:
                    break;
            }
        }
    }
}
