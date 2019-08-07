package com.frj.zhihuizhongxing.Contract;

import com.frj.zhihuizhongxing.Base.BaseView;
import com.frj.zhihuizhongxing.Data.BaseBean;
import com.frj.zhihuizhongxing.Data.MainBean;
import com.frj.zhihuizhongxing.Data.UserBean;

import io.reactivex.Flowable;

public interface MainContract {
    public interface Model {
        Flowable<MainBean> addCoordinate(String longlat);

        Flowable<UserBean> userinfo();

        Flowable<BaseBean> updatePostion(double latitude, double longitude);
    }

    public interface View extends BaseView {
        void onSuccessCoordinate(MainBean mainBean);

        void onSuccessUserInfo(UserBean userBean);

        void onUpdatePostion(BaseBean baseBean);

        void UpdatePostion(Throwable throwable);
    }

    public interface Presenter {
        void addCoordinate(String longlat);

        void userInfo();

        void updatePostion(double latitude, double longitude);
    }
}
