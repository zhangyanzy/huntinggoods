package com.jinxun.hunting_goods.presentation.activity;

import android.Manifest;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.bigkoo.pickerview.TimePickerView;
import com.jinxun.hunting_goods.LWApp;
import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.base.BaseActivity;
import com.jinxun.hunting_goods.databinding.ActivityLoginBinding;
import com.jinxun.hunting_goods.databinding.UserAgreementLayoutBinding;
import com.jinxun.hunting_goods.manager.ShortcutMgr;
import com.jinxun.hunting_goods.network.HttpSubscriber;
import com.jinxun.hunting_goods.network.api.account.usercase.GetAccountCase;
import com.jinxun.hunting_goods.network.api.account.usercase.LoginTypeCase;
import com.jinxun.hunting_goods.network.api.account.usercase.WxLoginCase;
import com.jinxun.hunting_goods.network.bean.Response;
import com.jinxun.hunting_goods.network.bean.auth.User;
import com.jinxun.hunting_goods.util.Constants;
import com.jinxun.hunting_goods.util.PermissionUtil;
import com.jinxun.hunting_goods.util.ToastUtil;
import com.jinxun.hunting_goods.weight.NavigationTopBar;
import com.jinxun.hunting_goods.weight.ToastView;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pub.devrel.easypermissions.EasyPermissions;

public class LoginActivity extends BaseActivity implements NavigationTopBar.NavigationTopBarClickListener, TextWatcher
        , EasyPermissions.PermissionCallbacks {

    public static final String TAG = "LoginActivity";

    private ActivityLoginBinding binding;
    private String mPhoneNumber;
    private BottomSheetDialog mSheetDialog;

    private UMShareAPI umShareAPI;
    private int type = Constants.SPREF.TYPE_PHONE; //用户登录方式

    private String avatar;
    private int sex;
    private String uid = "";


    private String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.REQUEST_INSTALL_PACKAGES};

    @Override
    protected void initComponent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setPresenter(new Presenter());
    }

    @Override
    protected void loadData(Bundle savedInstanceState) {
        umShareAPI = LWApp.getUMShareAPI();
        PermissionUtil.checkPermission(this, perms, getString(R.string.allow_permissions));
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


    //检测是否安装三方应用
    private void isPlatformExist(SHARE_MEDIA platform) {
        if (!umShareAPI.isInstall(this, platform))
            ToastUtil.showShortToast(getApplicationContext(), "请先安装应用");
        else doOauth(platform);
    }

    /**
     * 手机号注册并获取验证码跳转界面
     */

    private void login() {
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
//                    ToastView.initToast(getApplicationContext(), R.mipmap.pay_success, "标题", "主题");
                    type = Constants.SPREF.TYPE_WECHAT;
                    isPlatformExist(SHARE_MEDIA.WEIXIN);
                break;
                case R.id.qq_btn:
                    type = Constants.SPREF.TYPE_QQ;
                    isPlatformExist(SHARE_MEDIA.QQ);
                    break;
                case R.id.sina_icon:
                    type = Constants.SPREF.TYPE_SINA;
                    isPlatformExist(SHARE_MEDIA.SINA);
                    break;
                default:
                    break;
            }
        }
    }


    private void doOauth(SHARE_MEDIA platform) {
        umShareAPI.getPlatformInfo(LoginActivity.this, platform, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                Log.i(TAG, "onStart: " + "授权开始的回调");
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                StringBuilder stringBuilder = new StringBuilder();
                for (String key : map.keySet()) {
                    stringBuilder.append(key).append(":").append(map.get(key)).append("\n");
                }
                Log.i(TAG, "onComplete: " + stringBuilder.toString());
                Log.i(TAG, "share_media.getName(): " + share_media.getName());
                switch (share_media.getName()) {
                    case "qq":
                        String uid = map.get("uid");
                        qqLogin(uid);
                        break;
                    case "wx":
                        String unionID = map.get("uid");
                        wxLogin(unionID);
                        break;
                    case "sina":

                    default:
                        break;
                }
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                Log.i(TAG, "onError: " + throwable.getMessage());
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                Log.i(TAG, "onCancel: " + "授权取消的回调");

            }
        });
    }

    private void wxLogin(String uid) {
        new WxLoginCase(1l, uid).execute(new HttpSubscriber<User>() {
            @Override
            public void onSuccess(Response<User> response) {
                switch (response.getCode()) {
                    case "303":
                        openActivity(BindingPhoneActivity.class);
                        finish();
                        break;
                    case "200":
                        ShortcutMgr.login(response.getData());
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(String errorMsg, Response<User> response) {
                switch (response.getCode()) {
                    case "303":
                        openActivity(BindingPhoneActivity.class);
                        finish();
                        break;
                    case "200":
                        ShortcutMgr.login(response.getData());
                        break;
                    default:
                        break;
                }
            }
        });

    }

    private void qqLogin(String uid) {
        new LoginTypeCase(2l, uid).execute(new HttpSubscriber<User>() {
            @Override
            public void onSuccess(Response<User> response) {
                switch (response.getCode()) {
                    case "303":
                        openActivity(BindingPhoneActivity.class);
                        finish();
                        break;
                    case "200":
                        ShortcutMgr.login(response.getData());
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(String errorMsg, Response<User> response) {
                ToastUtil.showShortToast(getApplicationContext(), response.getMessage());
                switch (response.getCode()) {
                    case "303":
                        openActivity(BindingPhoneActivity.class);
                        finish();
                        break;
                    case "200":
                        ShortcutMgr.login(response.getData());
                        break;
                    default:
                        break;
                }
            }
        });

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        umShareAPI.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        umShareAPI.onSaveInstanceState(outState);
    }
}

