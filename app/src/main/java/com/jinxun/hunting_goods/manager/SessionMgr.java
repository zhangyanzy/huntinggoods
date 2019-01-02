package com.jinxun.hunting_goods.manager;

import android.content.Context;

import com.jinxun.hunting_goods.network.bean.auth.User;
import com.jinxun.hunting_goods.util.IsEmpty;

/**
 * Created by zhangyan on 2018/11/14.
 *
 * 用户信息管理类
 */

public class SessionMgr {

    private static String KEY_SESSION = "session";
    private static AuthSession session;// 会话信息
    // private static Timer timer;
    private static Context mContext;


    // 初始化
    public static void init(Context context) {
        mContext = context;
        // timer = new Timer(true);
        if (IsEmpty.object(session)) {
            session = new AuthSession();
        }
        session = StorageMgr.get(KEY_SESSION, AuthSession.class, StorageMgr.LEVEL_GLOBAL);
        if (IsEmpty.object(session)) {
            session = new AuthSession();
        }

//        initDefaultAddress();
        // timer.schedule(new Task(), 100, 1000 * 60 * 60);
    }



    // 更新 Session
    public static void updateSession(AuthSession session2) {
        session = session2;
        StorageMgr.set(KEY_SESSION, session, StorageMgr.LEVEL_GLOBAL);
    }

    // 更新 Session.user
    public static void updateUser(User user) {
        session.setUser(user);
        StorageMgr.set(KEY_SESSION, session, StorageMgr.LEVEL_GLOBAL);
    }

    public static void updateUser() {
        StorageMgr.set(KEY_SESSION, session, StorageMgr.LEVEL_GLOBAL);
    }

    // 获取 Session
    public static AuthSession getSession() {
        return session;
    }

    // 获取 Session.user
    public static User getUser() {
        return session.getUser();
    }


}
