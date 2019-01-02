package com.jinxun.hunting_goods.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jinxun.hunting_goods.R;

import java.lang.reflect.Field;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zhangyan on 2018/11/20.
 * 验证码输入框
 */

public class EditSmsCodeLayout extends GridLayout implements TextWatcher, View.OnKeyListener {


    private EditText[] edits;
    private int edit_position = 0;
    private StringBuffer code;
    private InputFinishListener mListener;
    private TypedArray mTypedArray;

    private int maxLen;//布局总长度 默认6位
    private int txtSize;//文本大小
    private int txtColor;//文本颜色
    private int editSize;//每个输入框的长度

    private float itemWidth = 0;//验证码的间隔

    public EditSmsCodeLayout(Context context) {
        super(context);
        init(null);
    }

    public EditSmsCodeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public EditSmsCodeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public EditSmsCodeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }


    public EditText[] getEdits() {
        return edits;
    }

    private void init(AttributeSet set) {
        initDefValue();
        initSet(set);
        code = new StringBuffer();
        edits = initEdits(maxLen);
        edits[edit_position].setFocusableInTouchMode(true);
        edits[edit_position].requestFocus();
        final InputMethodManager methodManager = (InputMethodManager) getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                methodManager.showSoftInput(edits[edit_position], 0);
            }
        }, 500);
    }


    /**
     * 初始化默认值
     */
    private void initDefValue() {
        maxLen = DefValue.MAX_LEN.value;
        txtSize = DefValue.TXT_SIZE.value;
        itemWidth = DefValue.DIVIDER_WIDTH.value;
        txtColor = getContext().getResources().getColor(DefValue.TXT_COLOR.value);
        editSize = dip2px(DefValue.SIZE.value);
    }

    /**
     * 初始化输入框
     */

    private EditText[] initEdits(int maxLen) {
        EditText[] editTexts = new EditText[maxLen];
        for (int i = 1; i <= maxLen; i++) {
            final EditText editText = new EditText(getContext());
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            editText.setOnClickListener(null);
//            editText.setLayoutParams(new LayoutParams(new LinearLayout.LayoutParams((editSize), (editSize))));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(editSize, editSize);
            GridLayout.LayoutParams gl = new GridLayout.LayoutParams(params);
            gl.rightMargin = 30;
            gl.leftMargin = 0;
            editText.setLayoutParams(gl);
            editText.setGravity(Gravity.CENTER);
            editText.setTextSize(txtSize);
            editText.setTextColor(txtColor);
            editText.addTextChangedListener(this);
            editText.setOnKeyListener(this);
            Field f = null;

            try {
                f = TextView.class.getDeclaredField("mCursorDrawableRes");
                f.setAccessible(true);
                f.set(editText, R.drawable.edit_cursor);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            setEdtBg(editText, i, maxLen);
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
            editTexts[i - 1] = editText;
            addView(editText);
            editText.setFocusableInTouchMode(false);
        }
        return editTexts;
    }


    /**
     * 初始化
     */
    private void initSet(AttributeSet set) {
        if (set == null)
            return;
        mTypedArray = getContext().obtainStyledAttributes(set, R.styleable.EdtSmsCodeLayout);
        if (mTypedArray != null) {
            txtSize = mTypedArray.getDimensionPixelSize(R.styleable.EdtSmsCodeLayout_text_size, -1) == -1 ? txtSize : px2dip(mTypedArray.getDimensionPixelSize(R.styleable.EdtSmsCodeLayout_text_size, -1));
            txtColor = mTypedArray.getColor(R.styleable.EdtSmsCodeLayout_text_color, txtColor);
            editSize = mTypedArray.getDimensionPixelSize(R.styleable.EdtSmsCodeLayout_item_size, editSize);
            maxLen = mTypedArray.getInt(R.styleable.EdtSmsCodeLayout_max_len, maxLen);


        }
    }

    public void setInputFinishListener(InputFinishListener listener) {
        mListener = listener;
    }

    private void setEdtBg(EditText text, int position, int maxLen) {
        if (position == 1)
            text.setBackgroundDrawable(getResources().getDrawable(R.mipmap.edit_bg_icon));
        else if (position == 2)
            text.setBackgroundDrawable(getResources().getDrawable(R.mipmap.edit_bg_icon));
        else if (position == maxLen)
            text.setBackgroundDrawable(getResources().getDrawable(R.mipmap.edit_bg_icon));
        else
            text.setBackgroundDrawable(getResources().getDrawable(R.mipmap.edit_bg_icon));
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        if (edits[edit_position].getText().toString().length() >= 1) {
            if (edit_position == edits.length - 1) {
                code.append(edits[edit_position].getText().toString());
                if (mListener != null)
                    mListener.onInputFinish(code.toString());
                removeCode();
                return;
            }
            nextEdt();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void nextEdt() {
        setEdtFocus(1);
    }


    /**
     * 清空验证码，默认输入完成后清空，也可手动调用
     */
    public void removeCode() {
        edit_position = 0;
        code.delete(0, code.length());
        for (EditText text : edits) {
            text.setFocusableInTouchMode(false);
            text.setText(null);
        }
        edits[edit_position].setFocusableInTouchMode(true);
        edits[edit_position].requestFocus();
    }

    private void setEdtFocus(int type) {
        if (type > 0) {
            code.append(edits[edit_position].getText().toString());
        } else {
            code.deleteCharAt(code.length() - 1);
        }
        edits[edit_position].setFocusableInTouchMode(false);
        edit_position += type;
        edits[edit_position].setFocusableInTouchMode(true);
        edits[edit_position].requestFocus();
    }


    private void backEdt() {
        if (edit_position == 0)
            return;
        edits[edit_position - 1].setText(null);
        setEdtFocus(-1);
    }


    private int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private int px2dip(float pxValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN)
            backEdt();
        return false;
    }


    public interface InputFinishListener {
        void onInputFinish(String code);
    }
}
