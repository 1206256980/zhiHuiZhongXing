package com.frj.zhihuizhongxing.Tools;

import android.content.Context;

import com.frj.zhihuizhongxing.Control.CustomDialog;
import com.frj.zhihuizhongxing.R;

public class DiaLogUtils {

    private static volatile DiaLogUtils instance;

    public static DiaLogUtils getInstance() {
        if (instance == null) {
            synchronized (DiaLogUtils.class) {
                if (instance == null) {
                    instance = new DiaLogUtils();
                }
            }
        }
        return instance;
    }

    private CustomDialog customDialog;

    public void show(Context context, boolean flag) {
        customDialog = new CustomDialog(context, R.style.dialog);
        customDialog.setCancelable(flag);
        customDialog.show();

    }

    public void cancel() {
        if (customDialog != null) {
            if (customDialog.isShowing()) {
                customDialog.cancel();
            }
        }
    }
}
