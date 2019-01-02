package com.jinxun.hunting_goods.network.bean.shopping;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * Created by admin on 2018/12/20.
 */

public class Day implements IPickerViewData {


    private String name;
    private int code;//0：今天  1：明天  2：后天

    public Day() {
    }

    public Day(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getPickerViewText() {
        return name;
    }
}
