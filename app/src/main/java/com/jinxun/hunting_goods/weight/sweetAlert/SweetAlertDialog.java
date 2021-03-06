package com.jinxun.hunting_goods.weight.sweetAlert;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.jinxun.hunting_goods.R;
import com.jinxun.hunting_goods.util.IsEmpty;

import java.util.List;

public class SweetAlertDialog extends Dialog implements View.OnClickListener {

  private View mDialogView;
  private AnimationSet mModalInAnim;
  private AnimationSet mModalOutAnim;
  private Animation mOverlayOutAnim;
  private Animation mErrorInAnim;
  private AnimationSet mErrorXInAnim;
  private AnimationSet mSuccessLayoutAnimSet;
  private Animation mSuccessBowAnim;
  private TextView mTitleTextView;
  private TextView mContentTextView;
  private String mTitleText;
  private String mContentText;
  private boolean mShowCancel;
  private boolean mShowContent;
  private String mCancelText;
  private String mConfirmText;
  private int mAlertType;
  private FrameLayout mErrorFrame;
  private FrameLayout mSuccessFrame;
  private LinearLayout mProgressFrame;
  private SuccessTickView mSuccessTick;
  private ImageView mErrorX;
  private View mSuccessLeftMask;
  private View mSuccessRightMask;
  private Drawable mCustomImgDrawable;
  private ImageView mCustomImage, mAnimationIV;
  private Button mConfirmButton;
  private Button mCancelButton;
  private ProgressHelper mProgressHelper;
  private FrameLayout mWarningFrame;
  private LinearLayout mButtonLi, loading;
  private View line, line_;
  private OnSweetClickListener mCancelClickListener;
  private OnSweetClickListener mConfirmClickListener;
  private boolean mCloseFromCancel;
  private AnimationDrawable mAnimationDrawable;

  public static final int NORMAL_TYPE = 0;
  public static final int ERROR_TYPE = 1;
  public static final int SUCCESS_TYPE = 2;
  public static final int WARNING_TYPE = 3;
  public static final int CUSTOM_IMAGE_TYPE = 4;
  public static final int PROGRESS_TYPE = 5;

  public static interface OnSweetClickListener {
    public void onClick(SweetAlertDialog sweetAlertDialog);
  }

  public SweetAlertDialog(Context context) {
    this(context, NORMAL_TYPE);
  }

  public SweetAlertDialog(Context context, int alertType) {
    super(context, R.style.alert_dialog);
    setCancelable(true);
    setCanceledOnTouchOutside(false);
    mProgressHelper = new ProgressHelper(context);
    mAlertType = alertType;
    mErrorInAnim = OptAnimationLoader.loadAnimation(getContext(), R.anim.error_frame_in);
    mErrorXInAnim = (AnimationSet) OptAnimationLoader.loadAnimation(getContext(),
        R.anim.error_x_in);
    // 2.3.x system don't support alpha-animation on layer-list drawable
    // remove it from animation set
    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
      List<Animation> childAnims = mErrorXInAnim.getAnimations();
      int idx = 0;
      for (; idx < childAnims.size(); idx++) {
        if (childAnims.get(idx) instanceof AlphaAnimation) {
          break;
        }
      }
      if (idx < childAnims.size()) {
        childAnims.remove(idx);
      }
    }
    mSuccessBowAnim = OptAnimationLoader.loadAnimation(getContext(), R.anim.success_bow_roate);
    mSuccessLayoutAnimSet = (AnimationSet) OptAnimationLoader.loadAnimation(getContext(),
        R.anim.success_mask_layout);
    mModalInAnim = (AnimationSet) OptAnimationLoader.loadAnimation(getContext(), R.anim.modal_in);
    mModalOutAnim = (AnimationSet) OptAnimationLoader.loadAnimation(getContext(), R.anim.modal_out);
    mModalOutAnim.setAnimationListener(new Animation.AnimationListener() {
      @Override
      public void onAnimationStart(Animation animation) {

      }

      @Override
      public void onAnimationEnd(Animation animation) {
        mDialogView.setVisibility(View.GONE);
        mDialogView.post(new Runnable() {
          @Override
          public void run() {
            if (mCloseFromCancel) {
              SweetAlertDialog.super.cancel();
            } else {
              SweetAlertDialog.super.dismiss();
            }
          }
        });
      }

      @Override
      public void onAnimationRepeat(Animation animation) {

      }
    });
    // dialog overlay fade out
    mOverlayOutAnim = new Animation() {
      @Override
      protected void applyTransformation(float interpolatedTime, Transformation t) {
        WindowManager.LayoutParams wlp = getWindow().getAttributes();
        wlp.alpha = 1 - interpolatedTime;
        getWindow().setAttributes(wlp);
      }
    };
    mOverlayOutAnim.setDuration(120);
  }

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.alert_dialog);

    mDialogView = getWindow().getDecorView().findViewById(android.R.id.content);
    mTitleTextView = (TextView) findViewById(R.id.title_text);
    mContentTextView = (TextView) findViewById(R.id.content_text);
    mErrorFrame = (FrameLayout) findViewById(R.id.error_frame);
    mErrorX = (ImageView) mErrorFrame.findViewById(R.id.error_x);
    mSuccessFrame = (FrameLayout) findViewById(R.id.success_frame);
    mProgressFrame = (LinearLayout) findViewById(R.id.progress_dialog);
    mAnimationIV = (ImageView) findViewById(R.id.animationIV);

    mSuccessTick = (SuccessTickView) mSuccessFrame.findViewById(R.id.success_tick);
    mSuccessLeftMask = mSuccessFrame.findViewById(R.id.mask_left);
    mSuccessRightMask = mSuccessFrame.findViewById(R.id.mask_right);
    mCustomImage = (ImageView) findViewById(R.id.custom_image);
    mWarningFrame = (FrameLayout) findViewById(R.id.warning_frame);
    line = findViewById(R.id.line);
    line = findViewById(R.id.line);
    mButtonLi = (LinearLayout) findViewById(R.id.button_ll);
    line_ = (View) findViewById(R.id.line_);
    mConfirmButton = (Button) findViewById(R.id.confirm_button);
    mCancelButton = (Button) findViewById(R.id.cancel_button);
    loading = (LinearLayout) findViewById(R.id.loading);
    mProgressHelper.setProgressWheel((ProgressWheel) findViewById(R.id.progressWheel));
    mConfirmButton.setOnClickListener(this);
    mCancelButton.setOnClickListener(this);

    setTitleText(mTitleText);
    setContentText(mContentText);
    setCancelText(mCancelText);
    setConfirmText(mConfirmText);
    changeAlertType(mAlertType, true);

  }

  private void restore() {
    mCustomImage.setVisibility(View.GONE);
    mErrorFrame.setVisibility(View.GONE);
    mSuccessFrame.setVisibility(View.GONE);
    mWarningFrame.setVisibility(View.GONE);
    mProgressFrame.setVisibility(View.GONE);
    mConfirmButton.setVisibility(View.VISIBLE);

    // mConfirmButton.setBackgroundResource(R.drawable.blue_button_background);
    mErrorFrame.clearAnimation();
    mErrorX.clearAnimation();
    mSuccessTick.clearAnimation();
    mSuccessLeftMask.clearAnimation();
    mSuccessRightMask.clearAnimation();
    mAnimationDrawable = (AnimationDrawable) mAnimationIV.getDrawable();
    mAnimationDrawable.stop();
  }

  private void playAnimation() {
    if (mAlertType == ERROR_TYPE) {
      mErrorFrame.startAnimation(mErrorInAnim);
      mErrorX.startAnimation(mErrorXInAnim);
    } else if (mAlertType == SUCCESS_TYPE) {
      mSuccessTick.startTickAnim();
      mSuccessRightMask.startAnimation(mSuccessBowAnim);
    } else if (mAlertType == PROGRESS_TYPE) {
      mAnimationIV.setImageResource(R.drawable.loading);
      mAnimationDrawable = (AnimationDrawable) mAnimationIV.getDrawable();
      mAnimationDrawable.start();
    }
  }

  private void changeAlertType(int alertType, boolean fromCreate) {
    mAlertType = alertType;
    // call after created views
    if (mDialogView != null) {
      if (!fromCreate) {
        // restore all of views state before switching alert type
        restore();
      }
      switch (mAlertType) {
      case ERROR_TYPE:
        mErrorFrame.setVisibility(View.VISIBLE);
        mConfirmButton.setBackgroundResource(R.drawable.sweetdialog_background);
        break;
      case SUCCESS_TYPE:
        mSuccessFrame.setVisibility(View.VISIBLE);
        // initial rotate layout of finished mask
        mSuccessLeftMask.startAnimation(mSuccessLayoutAnimSet.getAnimations().get(0));
        mSuccessRightMask.startAnimation(mSuccessLayoutAnimSet.getAnimations().get(1));
        break;
      case WARNING_TYPE:
        // mConfirmButton.setBackgroundResource(R.drawable.red_button_background);
        mWarningFrame.setVisibility(View.GONE);
        break;
      case CUSTOM_IMAGE_TYPE:
        setCustomImage(mCustomImgDrawable);
        break;
      case PROGRESS_TYPE:
        loading.setBackgroundColor(Color.TRANSPARENT);
        mProgressFrame.setVisibility(View.VISIBLE);
        line.setVisibility(View.GONE);
        mButtonLi.setVisibility(View.GONE);
        mTitleTextView.setVisibility(View.GONE);
        // mConfirmButton.setVisibility(View.GONE);
        break;
      }
      if (!fromCreate) {
        playAnimation();
      }
    }
  }

  public int getAlerType() {
    return mAlertType;
  }

  public void changeAlertType(int alertType) {
    changeAlertType(alertType, false);
  }

  public String getTitleText() {
    return mTitleText;
  }

  public SweetAlertDialog setTitleText(String text) {
    mTitleText = IsEmpty.string(text) ? "" : text;
    if (mTitleTextView != null) {
      if (!IsEmpty.string(mTitleText)) {
        mTitleTextView.setVisibility(View.VISIBLE);
        mTitleTextView.setText(mTitleText);
      } else
        mTitleTextView.setVisibility(View.GONE);
    }
    return this;
  }

  public SweetAlertDialog setCustomImage(Drawable drawable) {
    mCustomImgDrawable = drawable;
    if (mCustomImage != null && mCustomImgDrawable != null) {
      mCustomImage.setVisibility(View.VISIBLE);
      mCustomImage.setImageDrawable(mCustomImgDrawable);
    }
    return this;
  }

  public SweetAlertDialog setCustomImage(int resourceId) {
    if (resourceId == 0) {
      return setCustomImage(null);
    } else
      return setCustomImage(getContext().getResources().getDrawable(resourceId));
  }

  public String getContentText() {
    return mContentText;
  }

  public SweetAlertDialog setContentText(String text) {
    mContentText = IsEmpty.string(text) ? "" : text;
    if (mContentTextView != null) {
      if (!IsEmpty.string(mContentText)) {
        mContentTextView.setVisibility(View.VISIBLE);
        mContentTextView.setText(mContentText);
      } else
        mContentTextView.setVisibility(View.GONE);
    }
    return this;
  }

  public boolean isShowCancelButton() {
    return mShowCancel;
  }

  public SweetAlertDialog showCancelButton(boolean isShow) {
    mShowCancel = isShow;
    if (mCancelButton != null) {
      mCancelButton.setVisibility(mShowCancel ? View.VISIBLE : View.GONE);
      line_.setVisibility(mShowCancel ? View.VISIBLE : View.GONE);
    }
    return this;
  }

  public boolean isShowContentText() {
    return mShowContent;
  }

  public SweetAlertDialog showContentText(boolean isShow) {
    mShowContent = isShow;
    if (mContentTextView != null) {
      mTitleTextView.setVisibility(mShowContent ? View.GONE : View.VISIBLE);
      mContentTextView.setVisibility(mShowContent ? View.VISIBLE : View.GONE);
    }
    return this;
  }

  public String getCancelText() {
    return mCancelText;
  }

  public SweetAlertDialog setCancelText(String text) {
    mCancelText = IsEmpty.string(text) ? "" : text;
    if (mCancelButton != null) {
      if (!IsEmpty.string(mCancelText)) {
        showCancelButton(true);
        mCancelButton.setText(mCancelText);
      } else
        showCancelButton(false);
    }
    return this;
  }

  public SweetAlertDialog setDismissListener(OnDismissListener dismissListener) {
    setOnDismissListener(dismissListener);
    return this;
  }

  public String getConfirmText() {
    return mConfirmText;
  }

  public SweetAlertDialog setConfirmText(String text) {
    mConfirmText = text;
    if (mConfirmButton != null && mConfirmText != null) {
      mConfirmButton.setText(mConfirmText);
    }
    return this;
  }

  public SweetAlertDialog setCancelClickListener(OnSweetClickListener listener) {
    mCancelClickListener = listener;
    return this;
  }

  public SweetAlertDialog setConfirmClickListener(OnSweetClickListener listener) {
    mConfirmClickListener = listener;
    return this;
  }

  protected void onStart() {
    mDialogView.startAnimation(mModalInAnim);
    playAnimation();
  }

  /**
   * The real Dialog.cancel() will be invoked async-ly after the animation
   * finishes.
   */
  @Override
  public void cancel() {
    dismissWithAnimation(true);
  }

  /**
   * The real Dialog.dismiss() will be invoked async-ly after the animation
   * finishes.
   */
  public void dismissWithAnimation() {
    dismissWithAnimation(false);
  }

  private void dismissWithAnimation(boolean fromCancel) {
    mCloseFromCancel = fromCancel;
    mConfirmButton.startAnimation(mOverlayOutAnim);
    mDialogView.startAnimation(mModalOutAnim);
  }

  @Override
  public void onClick(View v) {
    if (v.getId() == R.id.cancel_button) {
      if (mCancelClickListener != null) {
        mCancelClickListener.onClick(SweetAlertDialog.this);
      } else {
        dismissWithAnimation();
      }
    } else if (v.getId() == R.id.confirm_button) {
      if (mConfirmClickListener != null) {
        mConfirmClickListener.onClick(SweetAlertDialog.this);
      } else {
        dismissWithAnimation();
      }
    }
  }

  public ProgressHelper getProgressHelper() {
    return mProgressHelper;
  }
}