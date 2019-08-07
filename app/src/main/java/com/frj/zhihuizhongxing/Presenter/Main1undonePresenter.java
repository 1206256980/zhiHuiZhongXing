package com.frj.zhihuizhongxing.Presenter;

import com.frj.zhihuizhongxing.Base.BasePresenter;
import com.frj.zhihuizhongxing.Contract.Main1undoneContract;
import com.frj.zhihuizhongxing.Data.BaseBean;
import com.frj.zhihuizhongxing.Data.DaiBanTaskBean;
import com.frj.zhihuizhongxing.Model.Main1undoneModel;
import com.frj.zhihuizhongxing.Web.RxScheduler;

import io.reactivex.functions.Consumer;

public class Main1undonePresenter extends BasePresenter<Main1undoneContract.View> implements Main1undoneContract.Presenter {
    Main1undoneContract.Model model;

    public Main1undonePresenter(Main1undoneContract.View view) {
        model = new Main1undoneModel();
        attachView(view);
    }

    @Override
    public void accident(int page, int length, String accident_status) {
        try {
            model.accident(page, length, accident_status)
                    .compose(RxScheduler.Flo_io_main())
                    .as(view.bindAutoDispose())
                    .subscribe(new Consumer<DaiBanTaskBean>() {
                        @Override
                        public void accept(DaiBanTaskBean bean) throws Exception {
                            view.onSuccessAccident(bean);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            view.onErrorAccident(throwable);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void accidentSendmsg(int dam_id, String type, double latitude, double longitude) {
        try {
            model.accidentSendmsg(dam_id, type, latitude, longitude)
                    .compose(RxScheduler.Flo_io_main())
                    .as(view.bindAutoDispose())
                    .subscribe(new Consumer<BaseBean>() {
                        @Override
                        public void accept(BaseBean baseBean) throws Exception {
                            view.onSuccessAccidentSendmsg(baseBean);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            view.onErroraccidentSendmsg(throwable);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
