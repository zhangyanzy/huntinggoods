package com.jinxun.hunting_goods.weight;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by zhangyan on 2018/11/14.
 */

public abstract class TextWatchAdapter implements TextWatcher {

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public abstract void onTextChanged(CharSequence s, int start, int before, int count);

    @Override
    public void afterTextChanged(Editable s) {
    }
}
