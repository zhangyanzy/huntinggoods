package com.jinxun.hunting_goods;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.jinxun.hunting_goods.config.AppConfig;
import com.jinxun.hunting_goods.listener.ActivityLifecycleListener;
import com.jinxun.hunting_goods.manager.SessionMgr;
import com.jinxun.hunting_goods.manager.StorageMgr;
import com.jinxun.hunting_goods.manager.TokenMgr;
import com.jinxun.hunting_goods.network.ApiClient;
import com.jinxun.hunting_goods.network.bean.address.ProvinceEntity;
import com.jinxun.hunting_goods.util.Constants;
import com.jinxun.hunting_goods.util.LocationUtil;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhangyan on 2018/11/13.
 */

public class LWApp extends MultiDexApplication {

    private static String TAG = "LWApp";

    private static LWApp instance;

    private static SharedPreferences spUser;
    private static SharedPreferences spApp;

    private static UMShareAPI umShareAPI;
    private static UMShareConfig config;


    public static LWApp getInstance() {
        return instance;
    }

    private static List<ProvinceEntity> provinces = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ApiClient.init(this, AppConfig.Http.BASE_URL);
        // 初始化缓存组件
        StorageMgr.init(this);
        // 初始化会话组件
        SessionMgr.init(this);
        // 初始化定位服务
//        LocationMgr.init(this);
//        LocationUtil.init(this);
        // 初始化cookie
        TokenMgr.init();

        listenForForeground();

        initUMeng();
//        Config.DEBUG = true;

        //存储用户相关信息
        spUser = getSharedPreferences(Constants.SPREF.FILE_USER_NAME, Context.MODE_PRIVATE);
        //存储应用相关信息
        spApp = getSharedPreferences(Constants.SPREF.FILE_APP_NAME, Context.MODE_PRIVATE);
    }

    private void initUMeng() {
        umShareAPI = UMShareAPI.get(this);
        UMConfigure.init(this, AppConfig.PartyKey.UMENT_KEY, "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        PlatformConfig.setQQZone("101524569","a5dcd4d405a0f6539e07645f0cc75d8e");
        PlatformConfig.setWeixin("wxc4e495d66283f57a","46d17910ce5f52f9bfb287bc159337b0");
        PlatformConfig.setSinaWeibo("2209235024", "8905fbb752e3b97a64bb7c3762e09c2e", "https://api.weibo.com/oauth2/default.html");
        UMConfigure.setLogEnabled(true);
    }

    //获取UMShareAPI初始化对象
    public static UMShareAPI getUMShareAPI() {
        return umShareAPI;
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    private void listenForForeground() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleListener() {
            @Override
            public void onActivityDestroyed(Activity activity) {
                super.onActivityDestroyed(activity);
                Log.i(TAG, "onActivityDestroyed");
            }
        });
    }

    //获取全局SharedPreferences对象
    public static SharedPreferences getUserPreferences() {
        return spUser;
    }

    //获取全局SharedPreferences对象
    public static SharedPreferences getAppPreferencesApp() {
        return spApp;
    }
}
