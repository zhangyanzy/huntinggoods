package com.jinxun.hunting_goods.manager;

import android.content.Intent;

import com.jinxun.hunting_goods.LWApp;
import com.jinxun.hunting_goods.network.bean.auth.User;
import com.jinxun.hunting_goods.presentation.activity.MainActivity;
import com.jinxun.hunting_goods.presentation.activity.LoginActivity;

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
