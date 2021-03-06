package com.jinxun.hunting_goods.weight.pullrefresh;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.weight.pullrefresh.layout.BaseFooterView;
import com.jinxun.hunting_goods.weight.pullrefresh.layout.BaseHeaderView;
import com.jinxun.hunting_goods.weight.pullrefresh.layout.PullRefreshLayout;
import com.jinxun.hunting_goods.weight.pullrefresh.support.impl.Loadable;
import com.jinxun.hunting_goods.weight.pullrefresh.support.impl.Refreshable;
import com.jinxun.hunting_goods.weight.pullrefresh.support.utils.CanPullUtil;


/**
 * 定义了下拉刷新和上推加载
 */
public class DefaultRefreshLayout extends PullRefreshLayout {

  private DefaultPullLayout mCenterViewContainer;
  private View mEmptyView;
  private View mErrorView;
  private View contentView;
  private int headerIndex = -1;
  private int footerIndex = -1;
  private boolean immediately = true;
  private LinearLayout emptyLayout;
  private ImageView emptyImg;
  private TextView emptyTxt;
  private TextView emptyTxtRed;
  private ProgressBar progressBarRefresh, progressBarMore;
  private Drawable headerProgressBar, footProgressBar;
  private Drawable headerDrawableBg, footDrawableBg;
  private int headerTextColor, footTextColor;
  private TextView loadingRefreshText, loadingMoreText;
  private int headerBg = ContextCompat.getColor(getContext(), R.color.white);
  private int footerBg = ContextCompat.getColor(getContext(), R.color.white);
  private boolean isRefresh, isLoadMore;

  public DefaultRefreshLayout(Context context) {
    this(context, null);
  }

  public DefaultRefreshLayout(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public DefaultRefreshLayout(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);

    setChildrenDrawingOrderEnabled(true);

    mCenterViewContainer = new DefaultPullLayout(context);
    mEmptyView = LayoutInflater.from(getContext()).inflate(R.layout.widget_pullrefresh_empty,
        mCenterViewContainer, false);
    emptyImg = (ImageView) mEmptyView.findViewById(R.id.img);
    emptyTxt = (TextView) mEmptyView.findViewById(R.id.txt);
    emptyTxtRed = (TextView) mEmptyView.findViewById(R.id.txt_red);
    mErrorView = LayoutInflater.from(getContext()).inflate(R.layout.widget_pullrefresh_error,
        mCenterViewContainer, false);
    mCenterViewContainer.addView(mEmptyView);
    addView(mCenterViewContainer);

    if (attrs != null) {
      TypedArray array = getContext().obtainStyledAttributes(attrs,
          R.styleable.DefaultRefreshLayout);
      this.hasFooter = array.getBoolean(R.styleable.DefaultRefreshLayout_footer, true);
      this.hasHeader = array.getBoolean(R.styleable.DefaultRefreshLayout_header, true);
      headerBg = array.getColor(R.styleable.DefaultRefreshLayout_header_bg, Color.WHITE);
      footerBg = array.getColor(R.styleable.DefaultRefreshLayout_foot_bg, Color.WHITE);
      headerProgressBar = array.getDrawable(R.styleable.DefaultRefreshLayout_header_progressBar);
      footProgressBar = array.getDrawable(R.styleable.DefaultRefreshLayout_foot_progressBar);
      headerDrawableBg = array.getDrawable(R.styleable.DefaultRefreshLayout_header_drawable_bg);
      footDrawableBg = array.getDrawable(R.styleable.DefaultRefreshLayout_foot_drawable_bg);
      headerTextColor = array.getColor(R.styleable.DefaultRefreshLayout_header_text_color,
          ContextCompat.getColor(context, R.color.light_black));
      footTextColor = array.getColor(R.styleable.DefaultRefreshLayout_foot_text_color,
          ContextCompat.getColor(context, R.color.light_black));
      array.recycle();
    }
    mHeader = (DefaultHeaderView) LayoutInflater.from(context).inflate(R.layout.widget_pullrefresh_header, this, false);
    addView((DefaultHeaderView) mHeader);
    mFooter = (DefaultFooterView) LayoutInflater.from(context)
        .inflate(R.layout.widget_pullrefresh_footer, this, false);
    progressBarRefresh = (ProgressBar) ((DefaultHeaderView) mHeader)
        .findViewById(R.id.refresh_pb_view);
    progressBarMore = (ProgressBar) ((DefaultFooterView) mFooter)
        .findViewById(R.id.footer_pb_view1);
    loadingRefreshText = (TextView) ((DefaultHeaderView) mHeader)
        .findViewById(R.id.refresh_text_view);
    loadingMoreText = (TextView) ((DefaultFooterView) mFooter).findViewById(R.id.footer_text_view1);
    addView((DefaultFooterView) mFooter);
    initAttrs();
  }

  /**
   * 初始化xml参数属性
   */
  private void initAttrs() {
    loadingRefreshText.setTextColor(headerTextColor);
    loadingMoreText.setTextColor(footTextColor);
    setHeaderBg(headerBg);
    setFooterBg(footerBg);
    setBackgroundColor(headerBg);
    if (null != headerProgressBar)
      progressBarRefresh.setIndeterminateDrawable(headerProgressBar);
    if (null != footProgressBar)
      progressBarMore.setIndeterminateDrawable(footProgressBar);
    if (null != headerDrawableBg) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        setBackground(headerDrawableBg);
      } else {
        setBackgroundDrawable(headerDrawableBg);
      }
    }
  }

  @Override
  public void addView(View child, int index, ViewGroup.LayoutParams params) {
    if (child instanceof Refreshable) {
      mHeader.setPullRefreshLayout(this);
    } else if (child instanceof Loadable) {
      mFooter.setPullRefreshLayout(this);
    } else if (child instanceof DefaultPullLayout || CanPullUtil.getPullAble(child) == null) {
      // do nothing
    } else {
      pullable = CanPullUtil.getPullAble(child);
      mPullView = child;
      contentView = child;
    }
    super.addView(child, index, params);
  }

  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    super.onLayout(changed, l, t, r, b);
    if (mCenterViewContainer != null && contentView != null) {
      // mCenterViewContainer.layout(contentView.getLeft(),
      // contentView.getTop(),
      // contentView.getRight(), contentView.getBottom());
    }
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    mEmptyView.measure(
        View.MeasureSpec.makeMeasureSpec(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(),
            View.MeasureSpec.EXACTLY),
        View.MeasureSpec.makeMeasureSpec(getMeasuredHeight() - getPaddingTop() - getPaddingBottom(),
            View.MeasureSpec.EXACTLY));
    mErrorView.measure(
        View.MeasureSpec.makeMeasureSpec(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(),
            View.MeasureSpec.EXACTLY),
        View.MeasureSpec.makeMeasureSpec(getMeasuredHeight() - getPaddingTop() - getPaddingBottom(),
            View.MeasureSpec.EXACTLY));

    // 记录头尾位置
    headerIndex = -1;
    for (int index = 0; index < getChildCount(); index++) {
      if (getChildAt(index) == mHeader) {
        headerIndex = index;
        break;
      }
    }
    footerIndex = -1;
    for (int index = 0; index < getChildCount(); index++) {
      if (getChildAt(index) == mFooter) {
        footerIndex = index;
        break;
      }
    }
  }

  public boolean isRefreshing() {
    return ((DefaultHeaderView) mHeader).isRefreshing();
  }

  public boolean isLoading() {
    return ((DefaultFooterView) mFooter).isLoading();
  }

  public void setHeaderTextColour(int colour) {
    headerTextColor = colour;
    loadingRefreshText.setTextColor(colour);
  }

  public void setFootTextColour(int colour) {
    footTextColor = colour;
    loadingMoreText.setTextColor(colour);
  }

  public void setHeaderPreDrawable(Drawable drawable) {
    headerProgressBar = drawable;
    progressBarRefresh.setIndeterminateDrawable(drawable);
  }

  public void setFootPreDrawable(Drawable drawable) {
    footProgressBar = drawable;
    progressBarMore.setIndeterminateDrawable(drawable);
  }

  public void setHeaderBg(int headerBg) {
    this.headerBg = headerBg;
  }

  public void setFooterBg(int footerBg) {
    this.footerBg = footerBg;
  }

  public void setHeaderBg(Drawable headerDrawableBg) {
    this.headerDrawableBg = headerDrawableBg;
  }

  public void setFooterBg(Drawable footDrawableBg) {
    this.footDrawableBg = footDrawableBg;
  }

  /**
   * 确保头尾最后画
   *
   * @param childCount
   * @param i
   * @return
   */
  @Override
  protected int getChildDrawingOrder(int childCount, int i) {
    if (headerIndex < 0 && footerIndex < 0) {
      return i;
    }
    if (i == childCount - 1) {
      return headerIndex;
    }
    if (i == childCount - 2) {
      return footerIndex;
    }
    int bigIndex = footerIndex > headerIndex ? footerIndex : headerIndex;
    int smallIndex = footerIndex < headerIndex ? footerIndex : headerIndex;
    if (i >= smallIndex && i < bigIndex - 1) {
      return i + 1;
    }
    if (i >= bigIndex - 1) {
      return i + 2;
    }
    return i;
  }

  @Override
  protected boolean onScroll(float y) {
    if (mHeader != null && y >= 0) {
      boolean intercept = mHeader.onScroll(y);
      if (y != 0) {
        if (!isRefresh) {
          setBackgroundColor(headerBg);
          if (null != headerDrawableBg) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
              setBackground(headerDrawableBg);
            } else {
              setBackgroundDrawable(headerDrawableBg);
            }
          }

          isLoadMore = false;
        }
        isRefresh = true;
        return intercept;
      }
    }
    if (mFooter != null && y <= 0) {
      boolean intercept = mFooter.onScroll(y);
      if (y != 0) {
        if (!isLoadMore) {
          setBackgroundColor(footerBg);
          if (null != footDrawableBg) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
              setBackground(footDrawableBg);
            } else {
              setBackgroundDrawable(footDrawableBg);
            }
          }
          isRefresh = false;
        }
        isLoadMore = true;
        return intercept;
      }
    }
    return false;
  }

  @Override
  protected void onScrollChange(int stateType) {
    if (mHeader != null) {
      mHeader.onScrollChange(stateType);
    }
    if (mFooter != null) {
      mFooter.onScrollChange(stateType);
    }
  }

  /**
   * 增加控制：没有设置头部或尾部时，不能滚动
   *
   * @param target
   * @param dxConsumed
   * @param dyConsumed
   * @param dxUnconsumed
   * @param dyUnconsumed
   */
  @Override
  public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed,
                             int dyUnconsumed) {
    if (dyUnconsumed > 0 && (!hasFooter || isRefreshing()) && isChildScrollToBottom()) {
      return;
    } else if (dyUnconsumed < 0 && (!hasHeader || isLoading()) && isChildScrollToTop()) {
      return;
    }
    super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
  }

  public boolean isChildScrollToTop() {
    if (Build.VERSION.SDK_INT < 14) {
      if (mPullView instanceof AbsListView) {
        final AbsListView absListView = (AbsListView) mPullView;
        return !(absListView.getChildCount() > 0 && (absListView.getFirstVisiblePosition() > 0
            || absListView.getChildAt(0).getTop() < absListView.getPaddingTop()));
      } else {
        return !(contentView.getScrollY() > 0);
      }
    } else {
      return !ViewCompat.canScrollVertically(mPullView, -1);
    }
  }

  public boolean isChildScrollToBottom() {
    if (mPullView instanceof RecyclerView) {
      RecyclerView recyclerView = (RecyclerView) mPullView;
      RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
      int count = recyclerView.getAdapter().getItemCount();
      if (layoutManager instanceof LinearLayoutManager && count > 0) {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
        if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == count - 1) {
          return true;
        }
      } else if (layoutManager instanceof StaggeredGridLayoutManager) {
        StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
        int[] lastItems = new int[2];
        staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(lastItems);
        int lastItem = Math.max(lastItems[0], lastItems[1]);
        if (lastItem == count - 1) {
          return true;
        }
      } else if (layoutManager instanceof LinearLayoutManager) {
        LinearLayoutManager slimLayoutManager = (LinearLayoutManager) layoutManager;
        if (slimLayoutManager.getChildCount() > 0) {
          int lastItem = slimLayoutManager.findLastCompletelyVisibleItemPosition();
          if (lastItem == count - 1) {
            return true;
          }
        } else {
          return true;
        }
      }
      return false;
    } else if (mPullView instanceof AbsListView) {
      final AbsListView absListView = (AbsListView) mPullView;
      int count = absListView.getAdapter().getCount();
      int fristPos = absListView.getFirstVisiblePosition();
      if (fristPos == 0 && absListView.getChildAt(0).getTop() >= absListView.getPaddingTop()) {
        return false;
      }
      int lastPos = absListView.getLastVisiblePosition();
      if (lastPos > 0 && count > 0 && lastPos == count - 1) {
        return true;
      }
      return false;
    } else if (mPullView instanceof ScrollView) {
      ScrollView scrollView = (ScrollView) mPullView;
      View view = (View) scrollView.getChildAt(scrollView.getChildCount() - 1);
      if (view != null) {
        int diff = (view.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));
        if (diff == 0) {
          return true;
        }
      }
    } else if (mPullView instanceof NestedScrollView) {
      NestedScrollView nestedScrollView = (NestedScrollView) mPullView;
      View view = (View) nestedScrollView.getChildAt(nestedScrollView.getChildCount() - 1);
      if (view != null) {
        int diff = (view.getBottom()
            - (nestedScrollView.getHeight() + nestedScrollView.getScrollY()));
        if (diff == 0) {
          return true;
        }
      }
    }
    return false;
  }

  public void showEmpty() {
    this.showView(mEmptyView);
  }

  public void showEmpty(@DrawableRes int imgRes, String msg) {
    showEmpty(imgRes, msg, null);
  }

  public void showEmpty(@DrawableRes int imgRes, String msg, String msg2) {
    emptyImg.setImageDrawable(getContext().getResources().getDrawable(imgRes));
    emptyTxt.setText(msg);
    emptyTxtRed.setText(msg2);
    showEmpty();
  }

  public void showEmpty(@DrawableRes int imgRes, @StringRes int msgRes) {
    showEmpty(imgRes, getContext().getString(msgRes));
  }

  public void showEmpty(@DrawableRes int imgRes, @StringRes int msgRes, @StringRes int msgRes2) {
    showEmpty(imgRes, getContext().getString(msgRes), getContext().getString(msgRes2));
  }

  public void setEmptyState() {
    setHasFooter(false);
    setHasHeader(false);
  }

  public void resume() {
    setHasHeader(true);
    setHasFooter(true);
  }

  public void setEmptyView(View view) {
    this.mEmptyView = view;
  }

  public void showError(String message) {
    if (message != null) {
      TextView errorMsg = (TextView) mErrorView.findViewById(R.id.errorMsg);
      errorMsg.setText(message);
    }
    this.showView(mErrorView);
  }

  public void showView(View view) {
    if (view == null) {
      return;
    }
    if (this.mCenterViewContainer == null) {
      return;
    }
    hasFooter = false;
    mCenterViewContainer.removeAllViews();
    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
        mCenterViewContainer.getWidth(), mCenterViewContainer.getHeight());
    mCenterViewContainer.setForegroundGravity(Gravity.CENTER);
    mCenterViewContainer.addView(view, layoutParams);

    mCenterViewContainer.setVisibility(View.VISIBLE);
    contentView.setVisibility(View.GONE);
    mPullView = mCenterViewContainer;
    pullable = CanPullUtil.getPullAble(view);
  }

  public void hideView() {
    mCenterViewContainer.removeView(emptyLayout);
    mCenterViewContainer.setVisibility(View.GONE);
    contentView.setVisibility(View.VISIBLE);
    mPullView = contentView;
    pullable = CanPullUtil.getPullAble(contentView);
  }

  /**
   * 要求刷新框立即出现
   */
  public void startRefresh() {
    if (mHeader != null) {
      immediately = true;
      mHeader.startRefresh();
    }
  }

  public void stopRefresh() {
    if (mHeader != null) {
      mHeader.stopRefresh();
    }
  }

  /**
   * 立即刷新时，直接显示在目标位置
   *
   * @param startY
   * @param endY
   * @return
   */
  public int startMoveTo(float startY, float endY) {
    if (immediately) {
      immediately = false;
      return startMoveBy(endY, 0);
    } else {
      return startMoveBy(startY, endY - startY);
    }
  }

  public void setOnRefreshListener(BaseHeaderView.OnRefreshListener listener) {
    if (this.mHeader != null && this.mHeader instanceof BaseHeaderView) {
      ((BaseHeaderView) this.mHeader).setOnRefreshListener(listener);
    }
  }

  public void setOnLoadListener(BaseFooterView.OnLoadListener listener) {
    if (this.mFooter != null && this.mFooter instanceof BaseFooterView) {
      ((BaseFooterView) this.mFooter).setOnLoadListener(listener);
    }
  }

  public void setTopPadding(int topPadding) {
    if (this.mHeader != null && this.mHeader instanceof DefaultHeaderView) {
      ((DefaultHeaderView) this.mHeader).setTopPadding(topPadding);
    }
  }

  /**
   * RefreshLayout 停止加载刷新
   */
  public void stopLoad(boolean more) {
    if (isRefreshing())
      stopRefresh();
    if (isLoading())
      stopLoad();
    hideView();
    setHasFooter(more);
  }

}
