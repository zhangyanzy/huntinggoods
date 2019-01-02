package com.jinxun.hunting_goods.manager;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.jinxun.hunting_goods.listener.OnViewPagerListener;

/**
 * Created by zhangyan on 2018/11/21.
 */

public class MyLinearLayoutManager extends LinearLayoutManager {

    private PagerSnapHelper mPagerSnapHelper;
    private RecyclerView mRecyclerView;
    private OnViewPagerListener mListener;
    private int mDrift;//位移，用来判断移动方向


    public MyLinearLayoutManager(Context context) {
        super(context);
        init();
    }

    public MyLinearLayoutManager(Context context, int orientation) {
        super(context, orientation, false);
        init();
    }

    public MyLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        init();
    }

    private void init() {
        mPagerSnapHelper = new PagerSnapHelper();
    }

    /**
     * 设置一次滑动一个item
     */
    @Override
    public void onAttachedToWindow(RecyclerView view) {
        super.onAttachedToWindow(view);
        mPagerSnapHelper.attachToRecyclerView(view);
        this.mRecyclerView = view;
        mRecyclerView.addOnChildAttachStateChangeListener(mChildAttachStateChangeListener);
    }


    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
    }

    @Override
    public void onScrollStateChanged(int state) {
        switch (state) {
            case RecyclerView.SCROLL_STATE_IDLE://空闲时
                View viewIdle = mPagerSnapHelper.findSnapView(this);
                int positionIdle = getPosition(viewIdle);
                if (null != mListener && getChildCount() == 1) {
                    mListener.onPageSelected(positionIdle, positionIdle == getItemCount() - 1);
                }
                break;
            case RecyclerView.SCROLL_STATE_DRAGGING://拖动
                View viewDrag = mPagerSnapHelper.findSnapView(this);
                int positionDrag = getPosition(viewDrag);
                break;
            case RecyclerView.SCROLL_STATE_SETTLING://要移动到最后位置时
                View viewSettling = mPagerSnapHelper.findSnapView(this);
                int positionSettling = getPosition(viewSettling);
                break;
            default:
                break;

        }
    }

    /**
     * 监听竖直方向的相对偏移量
     */

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        this.mDrift = dy;
        return super.scrollVerticallyBy(dy, recycler, state);
    }

    /**
     * 监听水平方向偏移量
     */
    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        this.mDrift = dx;
        return super.scrollHorizontallyBy(dx, recycler, state);
    }


    /**
     * 设置监听
     */
    public void setOnViewPagerListener(OnViewPagerListener listener) {
        this.mListener = listener;

    }

    private RecyclerView.OnChildAttachStateChangeListener mChildAttachStateChangeListener = new RecyclerView.OnChildAttachStateChangeListener() {
        @Override
        public void onChildViewAttachedToWindow(View view) {
            if (mListener != null && getChildCount() == 1) {
                mListener.onLayoutComplete();
            }
        }

        @Override
        public void onChildViewDetachedFromWindow(View view) {
            if (mDrift >= 0) {
                if (mListener != null) mListener.onPageRelease(true, getPosition(view));
            } else {
                if (mListener != null) mListener.onPageRelease(false, getPosition(view));
            }
        }
    };
}
