package com.frj.zhihuizhongxing.Contract;

import com.frj.zhihuizhongxing.Base.BaseView;
import com.frj.zhihuizhongxing.Data.BaseBean;
import com.frj.zhihuizhongxing.Data.UploadBean;

import java.io.File;
import java.util.List;

import io.reactivex.Flowable;

public interface Main2ArriveContract {
    public interface Model {
        Flowable<BaseBean> arrive(int dam_id, String address, String message, String type, double latitude, double longitude, List<UploadBean.DataBean> dataBeanList);

        Flowable<UploadBean> upload(File file);

        Flowable<BaseBean> completec(int dam_id, String message, String type, double latitude, double longitude, List<UploadBean.DataBean> dataBeanList);
    }

    public interface View extends BaseView {
        void onSuccessArrive(BaseBean baseBean);

        void onErrorArrive(Throwable throwable);

        void onSuccessUpload(UploadBean baseBean);

        void onErrorUpload(Throwable throwable);

        void onSuccesComplete(BaseBean baseBean);
        void onErrorComplete(Throwable throwable);
    }

    public interface Presenter {
        void arrive(int dam_id, String address, String message, String type, double latitude, double longitude,List<UploadBean.DataBean> dataBeanList);

        void upload(File file);

        void completec(int dam_id, String message, String type, double latitude, double longitude,List<UploadBean.DataBean> dataBeanList);
    }
}
