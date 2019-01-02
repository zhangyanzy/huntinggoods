package com.jinxun.hunting_goods.presentation.activity;

import android.Manifest;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.base.BaseActivity;
import com.jinxun.hunting_goods.databinding.ActivityWelcomeBinding;
import com.jinxun.hunting_goods.manager.SessionMgr;
import com.jinxun.hunting_goods.manager.ShortcutMgr;
import com.jinxun.hunting_goods.manager.TokenMgr;
import com.jinxun.hunting_goods.util.PermissionUtil;

public class WelcomeActivity extends BaseActivity {


    private ActivityWelcomeBinding binding;

    private String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};


    @Override
    protected void initComponent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome);
        binding.setPresenter(new Presenter());
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 取消标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 全屏


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //token过期或者没有登陆就去登陆，否则直接进入直接面
                if (TokenMgr.isExpired() || SessionMgr.getUser() == null) {
                    ShortcutMgr.logout();
                } else {
                    ShortcutMgr.login(SessionMgr.getUser());
                }
            }
        }, 1000);

    }


    @Override
    protected void loadData(Bundle savedInstanceState) {

    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {

    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        PermissionUtil.check(this,perms,100,getString(R.string.permission_device),getString(R.string.permission_dialog));
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
