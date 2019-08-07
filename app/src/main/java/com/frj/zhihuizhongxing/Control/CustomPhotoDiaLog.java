package com.frj.zhihuizhongxing.Control;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;


import com.frj.zhihuizhongxing.R;


public class CustomPhotoDiaLog extends Dialog {
    Context context;
    public CustomPhotoDiaLog(@NonNull Context context) {
        super(context);
    }

    public CustomPhotoDiaLog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CustomPhotoDiaLog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
    public CustomPhotoDiaLog(Context context, int themeResId, boolean cancelable) {
        super(context, themeResId);
        this.context=context;
        setCancelable(cancelable);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_photo_custom);
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        initView();
    }
    LinearLayout ll_change,ll_relieve;
    SelectManagement selectManagement;

    public void initView(){
        ll_change=findViewById(R.id.ll_change);
        ll_relieve=findViewById(R.id.ll_relieve);
        ll_change.setOnClickListener(v -> selectManagement.onChangeListen(this));
        ll_relieve.setOnClickListener(v -> selectManagement.onRelieveListen(this));
    }

    public interface  SelectManagement{
        void onChangeListen(CustomPhotoDiaLog customAccountDiaLog);
        void onRelieveListen(CustomPhotoDiaLog customAccountDiaLog);
    }

    public CustomPhotoDiaLog setSelectManagement(SelectManagement selectManagement) {
        this.selectManagement = selectManagement;
        return this;
    }
}
