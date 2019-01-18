package com.jinxun.hunting_goods.presentation.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.base.BaseActivity;
import com.jinxun.hunting_goods.databinding.ActivitySplashBinding;
import com.jinxun.hunting_goods.util.Constants;
import com.jinxun.hunting_goods.util.SpUtils;
import com.umeng.commonsdk.debug.E;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends BaseActivity {

    private static final String TAG = "SplashActivity";

    private ActivitySplashBinding binding;
    private List<ImageView> views = new ArrayList<>();

    @Override
    protected void initComponent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        binding.setPresenter(new Presenter());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        boolean isFirstStart = (boolean) SpUtils.init(Constants.SPREF.FILE_APP_NAME).get("is_first_start", true);
        if (!isFirstStart) {
            launchHomeScreen();
        } else {
            //首次
            SpUtils.init(Constants.SPREF.FILE_APP_NAME).put("is_first_start", false);
        }
    }

    @Override
    protected void loadData(Bundle savedInstanceState) {
        init();

    }

    private void init() {
        //设置状态栏透明
        changeStatusBarColor();
        int[] ids = new int[]{R.mipmap.new_welcome_slide1, R.mipmap.new_welcome_slide2, R.mipmap.new_welcome_slide3};
        for (int i = 0; i < ids.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(ids[i]);
            views.add(imageView);
        }
        binding.viewPager.setAdapter(new MyViewPagerAdapter());
        binding.viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

    }


    //viewpager的滑动监听
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            Log.i(TAG, "onPageScrolled: " + position);
        }

        @Override
        public void onPageSelected(int position) {
            Log.i(TAG, "onPageSelected: " + position);
            if (position == views.size() - 1) {
                binding.btnStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        launchHomeScreen();
                        Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                        startActivity(intent);
                    }
                });
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            Log.i(TAG, "onPageScrollStateChanged: " + state);
        }
    };


    public class MyViewPagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = views.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }


    /**
     * 设置状态栏变透明
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }


    /**
     * 跳过引导页
     */
    private void launchHomeScreen() {
        if ((boolean) SpUtils.init(Constants.SPREF.FILE_USER_NAME).get((Constants.SPREF.IS_LOGIN), false)) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            startActivity(new Intent(this, WelcomeActivity.class));
        }
        finish();
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {

    }

    @Override
    protected void initListeners() {

    }

    public class Presenter {
        public void onClick(View view) {
            switch (view.getId()) {

            }
        }
    }
}
