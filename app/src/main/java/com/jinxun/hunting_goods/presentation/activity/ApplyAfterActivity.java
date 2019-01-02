package com.jinxun.hunting_goods.presentation.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.base.BaseActivity;
import com.jinxun.hunting_goods.databinding.ActivityApplyAfterBinding;
import com.jinxun.hunting_goods.network.bean.order.OrderInfoEntity;
import com.jinxun.hunting_goods.weight.NavigationTopBar;

/**
 * 申请售后
 */

public class ApplyAfterActivity extends BaseActivity implements NavigationTopBar.NavigationTopBarClickListener {

    private ActivityApplyAfterBinding binding;
    private OrderInfoEntity orderInfoEntit;

    @Override
    protected void initComponent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_apply_after);
        binding.setPresenter(new Presenter());
    }

    @Override
    protected void loadData(Bundle savedInstanceState) {

    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        binding.applyAfterTopBar.setLeftImageResource(R.mipmap.back_icon);
        binding.applyAfterTopBar.setCenterTitleText("申请售后");
        binding.applyAfterTopBar.setViewGone();
        binding.applyAfterTopBar.setNavigationTopBarClickListener(this);
    }

    /**
     * 申请售后成功
     */
    private void applyAfterSuccess() {
        binding.afterMessageRoot.setBackgroundResource(R.color.black_22);
        binding.afterMessageRoot.getBackground().setAlpha(25);
        binding.afterMessage.setText("申请售后审核通过，请预约快递上门取件");
        binding.afterMessage.setTextColor(getResources().getColor(R.color.black_34));
        binding.afterMessageIcon.setImageResource(R.mipmap.pass_close);
        binding.appointmentBtn.setText("预约快递上门取件");
    }

    /**
     * 申请售后失败
     */
    private void applyAfterError() {
        binding.afterMessageRoot.setBackgroundResource(R.color.red_cc);
        binding.afterMessageRoot.getBackground().setAlpha(25);
        binding.afterMessage.setText("申请售后审核未通过，请重新提交");
        binding.afterMessage.setTextColor(getResources().getColor(R.color.red_cc));
        binding.afterMessageIcon.setImageResource(R.mipmap.not_pass_close);
        binding.appointmentBtn.setText("提交申请");
        binding.recipientsInfoRoot.setVisibility(View.GONE);
        binding.appointmentTimeRoot.setVisibility(View.GONE);
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
                case R.id.after_message_icon:
                    binding.afterMessageRoot.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }
    }
}
