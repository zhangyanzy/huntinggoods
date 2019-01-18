package com.jinxun.hunting_goods.network.bean.location;

/**
 * Created by zhangya on 2019/1/7.
 * 当前经纬度
 */

public class LatLonPoint {

    private String current_latitude;
    private String current_longitude;

    public LatLonPoint(String current_latitude, String current_longitude) {
        this.current_latitude = current_latitude;
        this.current_longitude = current_longitude;
    }

    public String getCurrent_latitude() {
        return current_latitude;
    }

    public void setCurrent_latitude(String current_latitude) {
        this.current_latitude = current_latitude;
    }

    public String getCurrent_longitude() {
        return current_longitude;
    }

    public void setCurrent_longitude(String current_longitude) {
        this.current_longitude = current_longitude;
    }

    @Override
    public String toString() {
        return "LatLonPoint{" +
                "current_latitude='" + current_latitude + '\'' +
                ", current_longitude='" + current_longitude + '\'' +
                '}';
    }
}
