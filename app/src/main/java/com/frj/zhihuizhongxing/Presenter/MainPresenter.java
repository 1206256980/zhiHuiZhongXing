package com.frj.zhihuizhongxing.Presenter;

import android.content.Intent;

import com.frj.zhihuizhongxing.Activity.LoginActivity;
import com.frj.zhihuizhongxing.Activity.MainActivity;
import com.frj.zhihuizhongxing.Base.BasePresenter;
import com.frj.zhihuizhongxing.Base.BaseView;
import com.frj.zhihuizhongxing.Contract.MainContract;
import com.frj.zhihuizhongxing.CurrentUser;
import com.frj.zhihuizhongxing.Data.BaseBean;
import com.frj.zhihuizhongxing.Data.MainBean;
import com.frj.zhihuizhongxing.Data.UserBean;
import com.frj.zhihuizhongxing.Model.MainModel;
import com.frj.zhihuizhongxing.Web.RxScheduler;

import io.reactivex.functions.Consumer;

public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {
    MainContract.Model model;

    public MainPresenter(MainContract.View view) {
        attachView(view);
        model = new MainModel();
    }


    @Override
    public void addCoordinate(String longlat) {
        model.addCoordinate(longlat)
                .compose(RxScheduler.<MainBean>Flo_io_main())
                .as(view.<MainBean>bindAutoDispose())
                .subscribe(new Consumer<MainBean>() {
                    @Override
                    public void accept(MainBean mainBean) throws Exception {
                        view.onSuccessCoordinate(mainBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.onError(throwable);
                    }
                });
    }

    @Override
    public void userInfo() {
        try {
            model.userinfo()
                    .compose(RxScheduler.<UserBean>Flo_io_main())
                    .as(view.bindAutoDispose())
                    .subscribe(new Consumer<UserBean>() {
                        @Override
                        public void accept(UserBean userBean) throws Exception {
                            view.onSuccessUserInfo(userBean);
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

    @Override
    public void updatePostion(double latitude, double longitude) {
        try {

            model.updatePostion(latitude, longitude)
                    .compose(RxScheduler.Flo_io_main())
                    .subscribe(new Consumer<BaseBean>() {
                        @Override
                        public void accept(BaseBean baseBean) throws Exception {
                            //view.onUpdatePostion(baseBean);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            //view.onError(throwable);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
