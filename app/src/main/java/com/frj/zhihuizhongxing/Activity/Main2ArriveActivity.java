package com.frj.zhihuizhongxing.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.frj.zhihuizhongxing.Base.BaseMvpActivity;
import com.frj.zhihuizhongxing.Contract.Main2ArriveContract;
import com.frj.zhihuizhongxing.CurrentUser;
import com.frj.zhihuizhongxing.Data.BaseBean;
import com.frj.zhihuizhongxing.Data.DaiBanTaskBean;
import com.frj.zhihuizhongxing.Data.UploadBean;
import com.frj.zhihuizhongxing.Presenter.Main2ArrivePresenter;
import com.frj.zhihuizhongxing.R;
import com.frj.zhihuizhongxing.Tools.DiaLogUtils;
import com.frj.zhihuizhongxing.Tools.PictureSelectUtils;
import com.frj.zhihuizhongxing.Web.CookiesManager;
import com.frj.zhihuizhongxing.Web.HttpUtil;
import com.wildma.pictureselector.PictureSelector;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 抵达页面
 */
public class Main2ArriveActivity extends BaseMvpActivity<Main2ArrivePresenter> implements Main2ArriveContract.View, GeocodeSearch.OnGeocodeSearchListener {
    String TAG = "Main2ArriveActivity";

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.ll_head)
    LinearLayout llHead;
    @BindView(R.id.iv_photo1)
    ImageView ivPhoto1;
    @BindView(R.id.iv_photo2)
    ImageView ivPhoto2;
    @BindView(R.id.iv_photo3)
    ImageView ivPhoto3;
    @BindView(R.id.iv_photo4)
    ImageView ivPhoto4;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    /*@BindView(R.id.et_address)
    EditText etAddress;*/
    @BindView(R.id.et_msg)
    EditText etMsg;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main2_arrive;
    }

    GeocodeSearch geocodeSearch;

    @Override
    public void initView() {
        geocodeSearch = new GeocodeSearch(this);
        geocodeSearch.setOnGeocodeSearchListener(this);
        presenter = new Main2ArrivePresenter(this);
        getIntentData();
        ivBack.setOnClickListener(v -> finish());
    }

    @Override
    public void onError(Throwable throwable) {

    }

    DaiBanTaskBean.DataBean dataBean;
    double latitude;
    double longitude;

    private void getIntentData() {
        try {
            dataBean = (DaiBanTaskBean.DataBean) getIntent().getSerializableExtra("dataBean");
            latitude = CurrentUser.getLatitude();
            longitude = CurrentUser.getLongitude();
            if (CurrentUser.getLatitude() == 0) {
                latitude = Double.parseDouble(dataBean.getLatitude());
                longitude = Double.parseDouble(dataBean.getLongitude());
            }

            getAddressByLatlng(new LatLng(latitude, longitude));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @OnClick({R.id.iv_photo1, R.id.iv_photo2, R.id.iv_photo3, R.id.iv_photo4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_photo1:
                selectPhoto(1);
                break;
            case R.id.iv_photo2:
                selectPhoto(2);
                break;
            case R.id.iv_photo3:
                selectPhoto(3);
                break;
            case R.id.iv_photo4:
                selectPhoto(4);
                break;
        }
    }


    int postiton;

    private void selectPhoto(int i) {
        try {
            PictureSelector
                    .create(this, PictureSelector.SELECT_REQUEST_CODE)
                    .selectPicture(false, 0, 0, 0, 0);
            postiton = i;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String[] pictureArray = {"", "", "", ""};//图片路径

    private void loadPhoto(String picturePath) {
        ImageView imageView = new ImageView(this);
        switch (postiton) {
            case 1:
                imageView = ivPhoto1;
                pictureArray[0] = picturePath;
                break;
            case 2:
                imageView = ivPhoto2;
                pictureArray[1] = picturePath;
                break;
            case 3:
                imageView = ivPhoto3;
                pictureArray[2] = picturePath;
                break;
            case 4:
                imageView = ivPhoto4;
                pictureArray[3] = picturePath;
                break;
            default:
                break;
        }

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.centerCrop();

        Glide.with(this).load(picturePath).apply(requestOptions).into(imageView);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*结果回调*/
        if (resultCode == RESULT_OK) {
            if (requestCode == PictureSelector.SELECT_REQUEST_CODE) {
                if (data != null) {
                    String picturePath = data.getStringExtra(PictureSelector.PICTURE_PATH);
                    Log.e(TAG, picturePath);
                    loadPhoto(picturePath);


                }
            }
        }
    }

    @Override
    public void onSuccessArrive(BaseBean baseBean) {//提交抵达
        DiaLogUtils.getInstance().cancel();
        if (baseBean.getIsErr() == 0) {
            toast("提交成功");
            finish();
        } else {
            toast(baseBean.getMsg() + "");
        }
    }

    @Override
    public void onErrorArrive(Throwable throwable) {//抵达
        DiaLogUtils.getInstance().cancel();
        throwable.printStackTrace();
        toast("提交失败");
    }

    List<UploadBean.DataBean> dataBeanList = new ArrayList<>();//图片url

    @Override
    public void onSuccessUpload(UploadBean baseBean) {//上传
        if (baseBean.getIsErr() == 0) {
            uploadNum++;
            dataBeanList.add(baseBean.getData());
            if (uploadNum >= photoNum) {
                presenter.arrive(dataBean.getDam_id(), simpleAddress, getMsg(), "colockin", latitude, longitude, dataBeanList);
            } else {
                photoCompress(pictureArray[uploadNum]);
            }
        } else {
            DiaLogUtils.getInstance().cancel();
            toast(baseBean.getMsg() + "");
        }
    }

    @Override
    public void onErrorUpload(Throwable throwable) {//上传
        DiaLogUtils.getInstance().cancel();
        throwable.printStackTrace();
        toast("提交失败");
    }

    @Override
    public void onSuccesComplete(BaseBean baseBean) {

    }

    @Override
    public void onErrorComplete(Throwable throwable) {

    }

    private int uploadNum = 0;//已上传数量
    private int photoNum = 0;//所需要上传的数量

    @OnClick(R.id.tv_submit)
    public void onViewClicked() {
        DiaLogUtils.getInstance().show(this, false);
        dataBeanList = new ArrayList<>();
        photoNum = 0;
        uploadNum = 0;
        //presenter.arrive(dataBean.getDam_id(), getMsg(), "colockin", latitude,longitude, "http://pic1.win4000.com/wallpaper/2019-07-30/5d3fef07ecfea.jpg", "http://pic1.win4000.com/wallpaper/2019-07-30/5d3fe70d20e4e_270_185.jpg");
        for (int i = 0; i < pictureArray.length; i++) {
            if (!TextUtils.isEmpty(pictureArray[i])) {
                photoNum++;

                //upload(new File(pictureArray[i].trim()));
                //presenter.arrive(dataBean.getDam_id(), simpleAddress, getMsg(), "colockin", latitude, longitude, "http://pic1.win4000.com/wallpaper/2019-07-30/5d3fef07ecfea.jpg", "http://pic1.win4000.com/wallpaper/2019-07-30/5d3fe70d20e4e_270_185.jpg");
            }
        }
        if(photoNum>0){
            photoCompress(pictureArray[uploadNum]);
        }else{
            presenter.arrive(dataBean.getDam_id(), simpleAddress, getMsg(), "colockin", latitude, longitude, dataBeanList);
        }
        //presenter.arrive(dataBean.getDam_id(), simpleAddress, getMsg(), "colockin", latitude, longitude, "http://pic1.win4000.com/wallpaper/2019-07-30/5d3fef07ecfea.jpg", "http://pic1.win4000.com/wallpaper/2019-07-30/5d3fe70d20e4e_270_185.jpg");

    }


    /**
     * 图片压缩以及上传
     */
    private void photoCompress(String photoUrl) {
        if (!TextUtils.isEmpty(photoUrl)) {
            Log.d("提示", photoUrl + "////");
            Luban.get(this)
                    .load(new File(photoUrl))                     //传人要压缩的图片
                    .putGear(Luban.THIRD_GEAR)      //设定压缩档次，默认三挡
                    .setCompressListener(new OnCompressListener() { //设置回调

                        @Override
                        public void onStart() {
                            // TODO 压缩开始前调用，可以在方法内启动 loading UI
                        }

                        @Override
                        public void onSuccess(File file) {
                            // TODO 压缩成功后调用，返回压缩后的图片文件

                            presenter.upload(file);
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            DiaLogUtils.getInstance().cancel();
                            toast("提交失败，请重新提交");
                            Log.e(TAG, e.getMessage());
                            // TODO 当压缩过去出现问题时调用
                        }
                    }).launch();    //启动压缩

            //upload(new File(pictureArray[i].trim()));
            //presenter.arrive(dataBean.getDam_id(), simpleAddress, getMsg(), "colockin", latitude, longitude, "http://pic1.win4000.com/wallpaper/2019-07-30/5d3fef07ecfea.jpg", "http://pic1.win4000.com/wallpaper/2019-07-30/5d3fe70d20e4e_270_185.jpg");

        }
    }


    private String getMsg() {
        return etMsg.getText().toString();
    }

    private void getAddressByLatlng(LatLng latLng) {
        //逆地理编码查询条件：逆地理编码查询的地理坐标点、查询范围、坐标类型。
        LatLonPoint latLonPoint = new LatLonPoint(latLng.latitude, latLng.longitude);
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 500f, GeocodeSearch.AMAP);
        //异步查询
        geocodeSearch.getFromLocationAsyn(query);
    }

    String simpleAddress = "";//简单路径
    String formatAddress = "";//全路径

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        RegeocodeAddress regeocodeAddress = regeocodeResult.getRegeocodeAddress();
        formatAddress = regeocodeAddress.getFormatAddress();
        simpleAddress = formatAddress.substring(9);
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }
}
