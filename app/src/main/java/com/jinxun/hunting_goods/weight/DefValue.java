package com.jinxun.hunting_goods.weight;

import com.jinxun.hunting_goods.R;

/**
 * Created by zhangyan on 2018/11/20.
 */

enum DefValue {
    TXT_SIZE(16),
    TXT_COLOR(R.color.black),
    SIZE(40),
    MAX_LEN(6),
    DIVIDER_WIDTH(20);

    int value;

    DefValue(int value) {
        this.value = value;
    }
}
