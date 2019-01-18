package com.jinxun.hunting_goods.presentation.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.base.BaseActivity;
import com.jinxun.hunting_goods.databinding.ActivityFeedBackBinding;
import com.jinxun.hunting_goods.network.HttpSubscriber;
import com.jinxun.hunting_goods.network.api.account.usercase.SendMessage;
import com.jinxun.hunting_goods.network.bean.Response;
import com.jinxun.hunting_goods.util.Constants;
import com.jinxun.hunting_goods.util.OnClickUtil;
import com.jinxun.hunting_goods.util.SpUtils;
import com.jinxun.hunting_goods.util.ToastUtil;
import com.jinxun.hunting_goods.weight.NavigationTopBar;

public class FeedBackActivity extends BaseActivity implements NavigationTopBar.NavigationTopBarClickListener {

    private ActivityFeedBackBinding binding;
    private String token;

    @Override
    protected void initComponent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_feed_back);
        binding.setPresenter(new Presenter());
    }

    @Override
    protected void loadData(Bundle savedInstanceState) {
        token = (String) SpUtils.init(Constants.SPREF.TOKEN).get(Constants.SPREF.TOKEN, "");
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        binding.feedBackTopBar.setLeftImageResource(R.mipmap.back_icon);
        binding.feedBackTopBar.setCenterTitleText("意见反馈");
        binding.feedBackTopBar.setViewGone();
        binding.feedBackTopBar.setNavigationTopBarClickListener(this);
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

    private void postMessage(String message) {
        new SendMessage(message, token).execute(new HttpSubscriber<Response>(FeedBackActivity.this) {
            @Override
            public void onSuccess(Response<Response> response) {
                ToastUtil.showShortToast(getApplicationContext(), "意见反馈已提交，谢谢");
                binding.feedBackEdit.setText("");
            }

            @Override
            public void onFailure(String errorMsg, Response<Response> response) {
                ToastUtil.showShortToast(getApplicationContext(), response.getMessage());
            }
        });

    }

    public class Presenter {
        public void onClick(View view) {
            String feedBack = binding.feedBackEdit.getText().toString();
            switch (view.getId()) {
                case R.id.submit_btn:
                    if (feedBack.equals("")) {
                        ToastUtil.showShortToast(getApplicationContext(), "您没有填写任何意见");
                    } else {
                        if (OnClickUtil.isFastClick()) {
                            postMessage(feedBack);
                        } else {
                            ToastUtil.showShortToast(getApplicationContext(), "意见反馈已提交，谢谢");
                            finish();
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
