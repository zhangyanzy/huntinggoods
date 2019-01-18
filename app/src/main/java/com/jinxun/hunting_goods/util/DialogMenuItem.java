package com.jinxun.hunting_goods.util;

/**
 * Created by admin on 2019/1/8.
 */

public class DialogMenuItem {
    public String operName;
    public int resId;

    public DialogMenuItem(String operName, int resId) {
        this.operName = operName;
        this.resId = resId;
    }

    public String getOperName() {
        return operName;
    }

    public void setOperName(String operName) {
        this.operName = operName;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}
