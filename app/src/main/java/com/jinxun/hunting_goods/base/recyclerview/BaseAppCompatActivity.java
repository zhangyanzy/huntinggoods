package com.jinxun.hunting_goods.base.recyclerview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.databinding.ActivityBaseBinding;
import com.jinxun.hunting_goods.util.KeyBoardUtils;
import com.jinxun.hunting_goods.util.ToastUtil;
import com.jinxun.hunting_goods.weight.GeneralUtils;
import com.jph.takephoto.app.TakePhotoActivity;
import com.ns.yc.ycstatelib.StateLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2019/1/8.
 */

public abstract class BaseAppCompatActivity extends TakePhotoActivity {

    private List<View> interceptViews = new ArrayList<>();

    private StateLayoutManager stateLayoutManager;
    private ActivityBaseBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_base);
//        binding.baseView.addView(stateLayoutManager.getRootLayout());
        initComponent();
        loadData(savedInstanceState);
        createEventHandlers();
        this.initToolbar(savedInstanceState);
        initListeners();
        initStatusLayout();
    }

    protected void initStatusLayout() {

    }

    /**
     * 初始化界面控件
     */
    protected abstract void initComponent();

    /**
     * 初次加载数据
     */
    protected abstract void loadData(Bundle savedInstanceState);


    /**
     * Initialize the toolbar in the layout
     *
     * @param savedInstanceState
     */
    protected abstract void initToolbar(Bundle savedInstanceState);

    /**
     * Initalize the view Of the listener
     */
    protected abstract void initListeners();

    /**
     * 判断界面表单数据填写是否有效
     */
    protected boolean isValid() {
        return true;
    }

    /**
     * 界面事件响应
     */
    protected void createEventHandlers() {

    }

    /**
     * 是否自动隐藏软键盘{@link this#dispatchTouchEvent(MotionEvent)}
     *
     * @return
     */
    protected boolean hideInputOut() {
        return true;
    }

    /**
     * 点击空白处隐藏软键盘
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (!hideInputOut())
                return super.dispatchTouchEvent(ev);
            View v = getCurrentFocus();
            if (closeSoft(v, ev)) {
                InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
            return super.dispatchTouchEvent(ev);
        }
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }


    /**
     * 监听触摸空白处，隐藏软键盘
     */
//    public View.OnTouchListener onTouchListener = new ViewShoe.OnTouchListener() {
//        @Override
//        public boolean onTouch(View v, MotionEvent event) {
//            InputMethodManager manager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//            if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                if (getActivity().getCurrentFocus() != null && getActivity().getCurrentFocus().getWindowToken() != null) {
//                    manager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//                }
//            }
//            return false;
//        }
//    };


    /**
     * 管理软键盘的显示
     */
    public void manageKeyBord(View view, Activity mActivity) {
        if (KeyBoardUtils.isKeyBordVisiable(mActivity))
            KeyBoardUtils.closeKeybord(view, mActivity);
    }


    /**
     * 验证手机号是否符合规则
     */
    protected boolean judgePhone(String phone) {
        if (GeneralUtils.isNullOrZeroLenght(phone)) {
            ToastUtil.showShortToast(this, getString(R.string.phone_not_empty));
            return false;
        }
        if (!GeneralUtils.isTel(phone)) {
            ToastUtil.showShortToast(this, getString(R.string.isNot_phone));
            return false;
        }
        return true;
    }

    /**
     * 验证密码是否符合规则
     */
    protected boolean judgePass(String pass) {
        if (GeneralUtils.isNullOrZeroLenght(pass)) {
            ToastUtil.showShortToast(this, getString(R.string.pass_not_empty));
            return false;
        }
        if (!GeneralUtils.IsPassword(pass)) {
            ToastUtil.showShortToast(this, getString(R.string.pass_length));
            return false;
        }
        return true;
    }

    protected void openActivity(Class<?> mClass) {
        Intent intent = new Intent(this, mClass);
        startActivity(intent);
    }


    /**
     * 是否隐藏软键盘
     *
     * @param v
     * @param event
     * @return
     */
    public boolean closeSoft(View v, MotionEvent event) {
        for (View view : interceptViews) {
            int[] leftTop = {
                    0, 0};
            view.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + view.getHeight();
            int right = left + view.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top
                    && event.getY() < bottom) {
                return false;
            }
        }
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {
                    0, 0};
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top
                    && event.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public void addInterceptView(View view) {
        interceptViews.add(view);
    }

    protected void showEmptyData() {
        stateLayoutManager.showEmptyData();
    }


    protected void showNetWorkError() {
        stateLayoutManager.showNetWorkError();
    }

    //正常展示数据状态
    protected void showContent() {
        stateLayoutManager.showContent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
