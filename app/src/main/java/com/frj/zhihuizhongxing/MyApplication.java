package com.frj.zhihuizhongxing;

import android.app.Application;



public class MyApplication extends Application {

    public static MyApplication instance;
    public static MyApplication mContext;
    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext=this;
        instance = this;
    }
}
