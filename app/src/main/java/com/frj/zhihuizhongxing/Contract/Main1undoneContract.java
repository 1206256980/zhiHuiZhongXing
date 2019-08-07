package com.frj.zhihuizhongxing.Contract;

import com.frj.zhihuizhongxing.Base.BaseView;
import com.frj.zhihuizhongxing.Data.BaseBean;
import com.frj.zhihuizhongxing.Data.DaiBanTaskBean;
import com.frj.zhihuizhongxing.Data.LoginBean;

import io.reactivex.Flowable;

public interface Main1undoneContract {
    public interface Model {
        Flowable<DaiBanTaskBean> accident(int page, int length, String accident_status);
        Flowable<BaseBean> accidentSendmsg(int dam_id,String type,double latitude,double longitude);
    }

    public interface View extends BaseView {
        void onSuccessAccident(DaiBanTaskBean bean);
        void onErrorAccident(Throwable throwable);

        void onSuccessAccidentSendmsg(BaseBean baseBean);
        void onErroraccidentSendmsg(Throwable throwable);
    }

    public interface Presenter {
        void accident(int page, int length, String accident_status);
        void accidentSendmsg(int dam_id,String type,double latitude,double longitude);
    }
}
