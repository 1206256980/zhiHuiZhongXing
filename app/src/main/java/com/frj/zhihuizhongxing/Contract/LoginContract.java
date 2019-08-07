package com.frj.zhihuizhongxing.Contract;

import com.frj.zhihuizhongxing.Base.BaseView;
import com.frj.zhihuizhongxing.Data.LoginBean;

import io.reactivex.Flowable;

public interface LoginContract {

    public interface Model {
        Flowable<LoginBean> login(String username, String password,String mdaf);
    }

    public interface View extends BaseView {
        void onSuccessLogin(LoginBean loginBean);
    }

    public interface Presenter {
        void login (String username, String password,String mdaf);
    }

}
