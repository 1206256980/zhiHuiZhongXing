package com.frj.zhihuizhongxing.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.frj.zhihuizhongxing.Base.BaseMvpActivity;
import com.frj.zhihuizhongxing.CurrentUser;
import com.frj.zhihuizhongxing.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class LaunchActivity extends BaseMvpActivity {


    @Override
    public int getLayoutId() {
        return R.layout.activity_launch;
    }

    @Override
    public void initView() {
        Observable.interval(1, TimeUnit.SECONDS).take(1)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (CurrentUser.isLogin()) {
                            startActivity(new Intent(LaunchActivity.this, MainActivity.class));
                        } else {
                            startActivity(new Intent(LaunchActivity.this, LoginActivity.class));
                        }
                        finish();
                    }
                });
    }

    @Override
    public void onError(Throwable throwable) {

    }
}
