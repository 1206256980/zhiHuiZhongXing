package com.frj.zhihuizhongxing.Web;


import com.frj.zhihuizhongxing.Data.BaseBean;
import com.frj.zhihuizhongxing.Data.DaiBanTaskBean;
import com.frj.zhihuizhongxing.Data.EncryptBean;
import com.frj.zhihuizhongxing.Data.LoginBean;
import com.frj.zhihuizhongxing.Data.MainBean;
import com.frj.zhihuizhongxing.Data.TaskIntoBean;
import com.frj.zhihuizhongxing.Data.UploadBean;
import com.frj.zhihuizhongxing.Data.UserBean;

import java.util.List;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface APIService {
    @Headers({"Content-Type: application/json"})
    @POST("index/applogin")
    Flowable<LoginBean> login(@Body EncryptBean bean);

    //http://wx.showpu.cn/api/manage/addCoordinate?longlat=1
    @GET("manage/addCoordinate")
    Flowable<MainBean> addCoordinate(@Query("longlat") String longlat);

    @POST("accident/accident_list")
    Flowable<DaiBanTaskBean> accident(@Body EncryptBean bean);//待办任务

    @POST("accident/app_create_accident")
    Flowable<BaseBean> appCreate(@Body EncryptBean bean);//发起任务

    @POST("frame/userinfo")
    Flowable<UserBean> userinfo();//获取用户信息

    @POST("accident/accident_sendmsg")
    Flowable<BaseBean> accidentSendmsg(@Body EncryptBean bean);//接受任务//抵达任务

    @POST("accident/accident_list")
    Flowable<DaiBanTaskBean> currentTask(@Body EncryptBean bean);//当前任务列表

    @POST("accident/accident_info")
    Flowable<TaskIntoBean> accidentInfo(@Body EncryptBean bean);//获取任务详情

    @Multipart
    @POST("accident/upload")
    Flowable<UploadBean> upload(@Part List<MultipartBody.Part> partLis);//上传图片

    @POST("accident/update_postion")
    Flowable<BaseBean> updatePostion(@Body EncryptBean bean);//定位

}
