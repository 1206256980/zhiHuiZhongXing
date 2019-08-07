package com.frj.zhihuizhongxing.Contract;

import com.frj.zhihuizhongxing.Base.BaseView;
import com.frj.zhihuizhongxing.Data.BaseBean;

import io.reactivex.Flowable;

public interface Main1FinishContract {
    public interface Model {
        Flowable<BaseBean> createAccident(String address, String message, Double latitude, Double longitude);
    }

    public interface View extends BaseView {
        void onSuccessCreateAccident(BaseBean baseBean);
        void onErrorCreateAccident(Throwable throwable);
    }

    public interface Presenter {
        void createAccident(String address, String message, Double latitude, Double longitude);
    }
}
