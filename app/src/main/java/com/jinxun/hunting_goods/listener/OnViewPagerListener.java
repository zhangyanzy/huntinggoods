package com.jinxun.hunting_goods.listener;

/**
 * Created by zhanyan on 2018/11/21.
 */

public interface OnViewPagerListener {

    /***
     * 释放监听
     */
    void onPageRelease(boolean isNext, int position);

    /**
     * 选中监听以及判断是否滑动到底部
     */
    void onPageSelected(int position, boolean isBottom);

    /**
     * 布局完成的监听
     */
    void onLayoutComplete();


}
