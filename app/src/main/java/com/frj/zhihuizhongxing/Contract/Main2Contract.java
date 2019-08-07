package com.frj.zhihuizhongxing.Contract;

import com.frj.zhihuizhongxing.Base.BaseView;
import com.frj.zhihuizhongxing.Data.DaiBanTaskBean;

import io.reactivex.Flowable;

public interface Main2Contract {
    public interface Model{
        public Flowable<DaiBanTaskBean> currentTaskList(int page, int length, String accident_status);
    }
    public interface View extends BaseView {
        public void onSuccessCurrentTaskList(DaiBanTaskBean daiBanTaskBean);
    }
    public interface Presenter{
        public void currentTaskList(int page, int length, String accident_status);
    }
}
