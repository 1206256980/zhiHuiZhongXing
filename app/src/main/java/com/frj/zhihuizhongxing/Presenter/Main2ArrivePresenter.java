package com.frj.zhihuizhongxing.Presenter;

import com.frj.zhihuizhongxing.Base.BasePresenter;
import com.frj.zhihuizhongxing.Contract.Main2ArriveContract;
import com.frj.zhihuizhongxing.Contract.Main2Contract;
import com.frj.zhihuizhongxing.Data.BaseBean;
import com.frj.zhihuizhongxing.Data.UploadBean;
import com.frj.zhihuizhongxing.Model.Main2ArriveModel;
import com.frj.zhihuizhongxing.Web.RxScheduler;

import java.io.File;
import java.util.List;

import io.reactivex.functions.Consumer;

public class Main2ArrivePresenter extends BasePresenter<Main2ArriveContract.View> implements Main2ArriveContract.Presenter {
    Main2ArriveContract.Model model;

    public Main2ArrivePresenter(Main2ArriveContract.View view) {
        attachView(view);
        model = new Main2ArriveModel();
    }


    @Override
    public void arrive(int dam_id, String address, String message, String type, double latitude, double longitude, List<UploadBean.DataBean> dataBeanList) {
        try {
            model.arrive(dam_id, address, message, type, latitude, longitude, dataBeanList)
                    .compose(RxScheduler.Flo_io_main())
                    .as(view.bindAutoDispose())
                    .subscribe(new Consumer<BaseBean>() {
                        @Override
                        public void accept(BaseBean baseBean) throws Exception {
                            view.onSuccessArrive(baseBean);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            view.onErrorArrive(throwable);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void upload(File file) {
        try {
            model.upload(file)
                    .compose(RxScheduler.Flo_io_main())
                    .as(view.bindAutoDispose())
                    .subscribe(new Consumer<UploadBean>() {
                        @Override
                        public void accept(UploadBean baseBean) throws Exception {
                            view.onSuccessUpload(baseBean);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            view.onErrorUpload(throwable);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Override
    public void completec(int dam_id, String message, String type, double latitude, double longitude, List<UploadBean.DataBean> dataBeanList) {
        try {
            model.completec(dam_id, message, type, latitude, longitude, dataBeanList)
                    .compose(RxScheduler.Flo_io_main())
                    .as(view.bindAutoDispose())
                    .subscribe(new Consumer<BaseBean>() {
                        @Override
                        public void accept(BaseBean baseBean) throws Exception {
                            view.onSuccesComplete(baseBean);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            view.onErrorComplete(throwable);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
