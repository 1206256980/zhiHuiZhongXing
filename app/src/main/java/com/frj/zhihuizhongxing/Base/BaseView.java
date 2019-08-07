package com.frj.zhihuizhongxing.Base;


import android.content.Context;

import com.uber.autodispose.AutoDisposeConverter;

public interface BaseView {
    void onError(Throwable throwable);

    /**
     * 绑定Android生命周期
     * @param <T>
     * @return
     */
    <T> AutoDisposeConverter<T> bindAutoDispose();

}
