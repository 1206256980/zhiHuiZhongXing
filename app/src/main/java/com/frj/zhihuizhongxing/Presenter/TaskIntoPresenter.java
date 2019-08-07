package com.frj.zhihuizhongxing.Presenter;

import com.frj.zhihuizhongxing.Base.BasePresenter;
import com.frj.zhihuizhongxing.Contract.TaskIntoContract;
import com.frj.zhihuizhongxing.Data.TaskIntoBean;
import com.frj.zhihuizhongxing.Model.TaskIntoModel;
import com.frj.zhihuizhongxing.Web.RetrofitClient;
import com.frj.zhihuizhongxing.Web.RxScheduler;

import io.reactivex.functions.Consumer;

public class TaskIntoPresenter extends BasePresenter<TaskIntoContract.View> implements TaskIntoContract.Presenter {

    TaskIntoContract.Model model;

    public TaskIntoPresenter(TaskIntoContract.View view) {
        attachView(view);
        model = new TaskIntoModel();
    }

    @Override
    public void accidentInfo(int dam_id) {
        try {
            model.accidentInfo(dam_id)
                    .compose(RxScheduler.Flo_io_main())
                    .as(view.bindAutoDispose())
                    .subscribe(new Consumer<TaskIntoBean>() {
                        @Override
                        public void accept(TaskIntoBean taskIntoBean) throws Exception {
                            view.onSuccessAccidentInfo(taskIntoBean);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            view.onErrorAccidentInfo(throwable);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
