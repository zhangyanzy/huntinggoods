package com.jinxun.hunting_goods;

import android.app.Activity;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.jinxun.hunting_goods.config.AppConfig;
import com.jinxun.hunting_goods.listener.ActivityLifecycleListener;
import com.jinxun.hunting_goods.manager.SessionMgr;
import com.jinxun.hunting_goods.manager.StorageMgr;
import com.jinxun.hunting_goods.manager.TokenMgr;
import com.jinxun.hunting_goods.network.ApiClient;
import com.jinxun.hunting_goods.network.bean.address.ProvinceEntity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhangyan on 2018/11/13.
 */

public class LWApp extends MultiDexApplication {

    private static String TAG = "LWApp";

    private static LWApp instance;

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
        // 初始化cookie
        TokenMgr.init();


        listenForForeground();
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
}
