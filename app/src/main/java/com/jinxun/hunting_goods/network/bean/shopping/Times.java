package com.jinxun.hunting_goods.network.bean.shopping;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * Created by admin on 2018/12/20.
 */

public class Times implements IPickerViewData {

    private String name;
    private int id;

    public Times() {

    }

    public Times(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Times{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public String getPickerViewText() {
        return name;
    }
}
