package com.frj.zhihuizhongxing.Presenter;


import android.content.Context;

import com.frj.zhihuizhongxing.Base.BasePresenter;
import com.frj.zhihuizhongxing.Contract.LoginContract;
import com.frj.zhihuizhongxing.Data.LoginBean;
import com.frj.zhihuizhongxing.Model.LoginModel;
import com.frj.zhihuizhongxing.Tools.SystemTools;
import com.frj.zhihuizhongxing.Web.RxScheduler;

import io.reactivex.functions.Consumer;

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {
    LoginContract.Model model;

    public LoginPresenter(LoginContract.View view) {
        attachView(view);
        model = new LoginModel();
    }

    @Override
    public void login(String username, String password,String mdaf) {
        model.login(username,password,mdaf)
                .compose(RxScheduler.<LoginBean>Flo_io_main())
                .as(view.<LoginBean>bindAutoDispose())
                .subscribe(new Consumer<LoginBean>() {
                    @Override
                    public void accept(LoginBean loginBean) throws Exception {
                        view.onSuccessLogin(loginBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.onError(throwable);
                    }
                });
    }
}
