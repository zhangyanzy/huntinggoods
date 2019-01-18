package com.jinxun.hunting_goods.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;

import com.jinxun.hunting_goods.listener.OnOperItemClickL;

/**
 * Created by zhangyan on 2019/1/8.
 */

public class PhotoPickerUtil {

    private static ActionSheetDialog sheetDialog;
    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }

    public static void setContent(String title,String[] contents, View animateView) {
        sheetDialog = new ActionSheetDialog(mContext, contents, animateView);
        if (title != null && !title.equals("")) {
            sheetDialog.title(title).titleTextSize_SP(14.5f);
        }
        sheetDialog.setCanceledOnTouchOutside(true);
        //设置弹窗内容的显示动画,默认有动画
    }

    public static void show(final OnItemClickListener listener) {
        sheetDialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    listener.onItemClick(position);
                    sheetDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        if (sheetDialog!=null){
            sheetDialog.show();
        }

    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

}
