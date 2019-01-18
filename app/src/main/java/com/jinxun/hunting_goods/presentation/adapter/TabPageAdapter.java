package com.jinxun.hunting_goods.presentation.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import com.jinxun.hunting_goods.base.BaseFragment;
import com.jinxun.hunting_goods.presentation.fragment.OrderInfoFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyan on 2018/11/26.
 */

public class TabPageAdapter extends FragmentPagerAdapter {


    private List<BaseFragment> fragments = new ArrayList<>();
    private List<String> mTitle;
    private String signId = "";


    public TabPageAdapter(FragmentManager fm, List<BaseFragment> fragments, List<String> mTitle) {
        super(fm);
        this.fragments = fragments;
        this.mTitle = mTitle;
    }

    public List<BaseFragment> getData() {
        if (fragments.size() == 0 && fragments == null) {

        }
        return fragments;
    }

    /**
     * 刷新fragment
     */
    public void setFragments(FragmentManager fm, List<BaseFragment> fragments, List<String> mTitles) {
        this.mTitle = mTitles;
        if (this.fragments != null) {
            FragmentTransaction ft = fm.beginTransaction();
            for (Fragment f : this.fragments) {
                ft.remove(f);
            }
            ft.commitAllowingStateLoss();
            ft = null;
            fm.executePendingTransactions();
        }
        this.fragments = fragments;
        notifyDataSetChanged();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle != null ? mTitle.get(position) : "";
    }

    public void setSignId(String signId) {
        this.signId = signId;
    }

    @Override
    public Fragment getItem(int position) {
        int type;
        switch (position) {
            case 0:
                type = 100;
                break;
            case 1:
                type = 1;
                break;
            case 2:
                type = 2;
                break;
            case 3:
                type = 3;
                break;
            case 4:
                type = 4;
                break;
            case 5:
                type = 5;
                break;
            case 6:
                type = 6;
                break;
            case 7:
                type = 7;
                break;
            default:
                type = 100;
                break;
        }
        return OrderInfoFragment.newInstance(type);
    }


    @Override
    public int getCount() {
        return fragments.size();
    }

    /**
     * 重写不让销毁fragment
     *
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }


}
