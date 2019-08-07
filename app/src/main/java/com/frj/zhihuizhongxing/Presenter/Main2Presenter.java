package com.frj.zhihuizhongxing.Presenter;

import com.frj.zhihuizhongxing.Base.BasePresenter;
import com.frj.zhihuizhongxing.Contract.Main2Contract;
import com.frj.zhihuizhongxing.Data.DaiBanTaskBean;
import com.frj.zhihuizhongxing.Model.Main2Model;
import com.frj.zhihuizhongxing.Web.RxScheduler;

import io.reactivex.functions.Consumer;

public class Main2Presenter extends BasePresenter<Main2Contract.View> implements Main2Contract.Presenter {

    Main2Contract.Model model;

    public Main2Presenter(Main2Contract.View view) {
        attachView(view);
        model = new Main2Model();
    }

    @Override
    public void currentTaskList(int page, int length, String accident_status) {
        try {
            model.currentTaskList(page, length, accident_status)
                    .compose(RxScheduler.Flo_io_main())
                    .as(view.bindAutoDispose())
                    .subscribe(new Consumer<DaiBanTaskBean>() {
                        @Override
                        public void accept(DaiBanTaskBean bean) throws Exception {
                            view.onSuccessCurrentTaskList(bean);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            view.onError(throwable);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
