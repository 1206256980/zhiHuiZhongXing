package com.frj.zhihuizhongxing.Contract;

import com.frj.zhihuizhongxing.Base.BaseView;
import com.frj.zhihuizhongxing.Data.TaskIntoBean;

import io.reactivex.Flowable;

public interface TaskIntoContract {
    public interface Model {
        Flowable<TaskIntoBean> accidentInfo(int dam_id);
    }

    public interface View extends BaseView {
        void onSuccessAccidentInfo(TaskIntoBean taskIntoBean);

        void onErrorAccidentInfo(Throwable throwable);
    }

    public interface Presenter {
        void accidentInfo(int dam_id);


    }
}
