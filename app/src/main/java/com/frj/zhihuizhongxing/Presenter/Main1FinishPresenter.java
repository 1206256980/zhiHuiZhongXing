package com.frj.zhihuizhongxing.Presenter;

import com.frj.zhihuizhongxing.Base.BasePresenter;
import com.frj.zhihuizhongxing.Contract.LoginContract;
import com.frj.zhihuizhongxing.Contract.Main1FinishContract;
import com.frj.zhihuizhongxing.Data.BaseBean;
import com.frj.zhihuizhongxing.Fragment.Main1FinishFragment;
import com.frj.zhihuizhongxing.Model.LoginModel;
import com.frj.zhihuizhongxing.Model.Main1FinishModel;
import com.frj.zhihuizhongxing.Web.RxScheduler;

import io.reactivex.functions.Consumer;

public class Main1FinishPresenter extends BasePresenter<Main1FinishContract.View> implements Main1FinishContract.Presenter {

    Main1FinishContract.Model model;

    public Main1FinishPresenter(Main1FinishContract.View view) {
        attachView(view);
        model = new Main1FinishModel();
    }

    @Override
    public void createAccident(String address, String message, Double latitude, Double longitude) {
        try {
            model.createAccident(address, message, latitude, longitude)
                    .compose(RxScheduler.Flo_io_main())
                    .as(view.bindAutoDispose())
                    .subscribe(new Consumer<BaseBean>() {
                        @Override
                        public void accept(BaseBean baseBean) throws Exception {
                            view.onSuccessCreateAccident(baseBean);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            view.onErrorCreateAccident(throwable);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

