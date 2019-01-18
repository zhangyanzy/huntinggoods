package com.jinxun.hunting_goods.manager;

import android.content.Intent;

import com.jinxun.hunting_goods.LWApp;
import com.jinxun.hunting_goods.network.bean.auth.User;
import com.jinxun.hunting_goods.presentation.activity.MainActivity;
import com.jinxun.hunting_goods.presentation.activity.LoginActivity;
import com.jinxun.hunting_goods.util.Constants;
import com.jinxun.hunting_goods.util.SpUtils;

/**
 * Created by zhangyan on 2018/11/14.
 * 用户快捷操作
 */

public class ShortcutMgr {
    /**
     * 登陆成功 进入主界面
     */
    public static void login(User user) {
        SessionMgr.updateUser(user);
        Intent intent = new Intent(LWApp.getInstance(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        LWApp.getInstance().startActivity(intent);
        saveUserData(user);
    }


    private static void saveUserData(User user) {
        SpUtils.init(Constants.SPREF.FILE_USER_NAME).put(Constants.SPREF.TOKEN, user.getToken());
        SpUtils.init(Constants.SPREF.FILE_USER_NAME).put(Constants.SPREF.USER_PHONE, user.getPhone());
        SpUtils.init(Constants.SPREF.FILE_USER_NAME).put(Constants.SPREF.USER_ID, user.getUserId());
    }

    /**
     * 用户退出
     */

    public static void logout() {
        TokenMgr.clear();
        Intent intent = new Intent(LWApp.getInstance(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        LWApp.getInstance().startActivity(intent);
    }
}
