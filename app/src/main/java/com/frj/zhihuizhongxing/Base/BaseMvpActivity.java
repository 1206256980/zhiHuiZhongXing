package com.frj.zhihuizhongxing.Base;

import android.arch.lifecycle.Lifecycle;

import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

public abstract class BaseMvpActivity<T extends BasePresenter> extends BaseActivity implements BaseView {
    public T presenter;

    @Override
    protected void onDestroy() {
        if(presenter!=null){
            presenter.detachView();
        }
        super.onDestroy();
    }

    /**
     * 绑定生命周期 防止内存泄漏
     * @param <T>
     * @return
     */
    @Override
    public <T> AutoDisposeConverter<T> bindAutoDispose() {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY));
    }
}
