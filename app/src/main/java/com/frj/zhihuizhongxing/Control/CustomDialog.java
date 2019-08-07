package com.frj.zhihuizhongxing.Control;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.frj.zhihuizhongxing.R;


public class CustomDialog extends Dialog {

    View view;
    TextView textView;
    String content;

    public CustomDialog(Context context) {
        super(context);
    }

    public CustomDialog(Context context, int theme) {
        super(context, theme);
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog_load);
        view=findViewById(R.id.custom_dialog);
        /*textView=view.findViewById(R.id.tv_load_dialog);
        textView.setText(content);*/
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
