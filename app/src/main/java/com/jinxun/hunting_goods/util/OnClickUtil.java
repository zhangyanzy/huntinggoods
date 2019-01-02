package com.jinxun.hunting_goods.util;

/**
 * Created by zhangyan on 2018/12/10.
 */

public class OnClickUtil {

    //间隔时间
    private static final int MIN_CLICK_DELAY_TIME = 6000;
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();//当前时间
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }

}
