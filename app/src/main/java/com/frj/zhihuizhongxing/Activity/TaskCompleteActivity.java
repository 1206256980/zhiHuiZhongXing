package com.frj.zhihuizhongxing.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.wildma.pictureselector.PictureSelector;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class TaskCompleteActivity extends BaseMvpActivity<Main2ArrivePresenter> implements Main2ArriveContract.View {


    @BindView(R.id.tv_back)
    ImageView tvBack;
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
    @BindView(R.id.et_msg)
    EditText etMsg;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;

    @Override
    public int getLayoutId() {
        return R.layout.activity_task_complete;
    }

    @Override
    public void initView() {
        presenter = new Main2ArrivePresenter(this);
        getIntentData();
        tvBack.setOnClickListener(v -> finish());
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    private String TAG = "TaskCompleteActivity";

    DaiBanTaskBean.DataBean dataBean;
    double latitude;
    double longitude;

    private void getIntentData() {
        try {
            dataBean = (DaiBanTaskBean.DataBean) getIntent().getSerializableExtra("dataBean");

            //此处需要修改
            latitude = CurrentUser.getLatitude();
            longitude = CurrentUser.getLongitude();
            if (CurrentUser.getLatitude() == 0) {
                latitude = Double.parseDouble(dataBean.getLatitude());
                longitude = Double.parseDouble(dataBean.getLongitude());
            }
            //getAddressByLatlng(new LatLng(latitude, longitude));
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

    String[] pictureArray = {"", "", "", ""};

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

    private int uploadNum = 0;//已上传数量
    private int photoNum = 0;//所需要上传的数量

    @OnClick(R.id.tv_submit)
    public void onViewClicked() {
        DiaLogUtils.getInstance().show(this, false);
        dataBeanList = new ArrayList<>();
        photoNum = 0;
        uploadNum = 0;
        for (int i = 0; i < pictureArray.length; i++) {
            if (!TextUtils.isEmpty(pictureArray[i])) {
                photoNum++;
            }
        }
        if (photoNum > 0) {
            photoCompress(pictureArray[uploadNum]);
        } else {
            presenter.completec(dataBean.getDam_id(), getMsg(), "waitcheck", latitude, longitude, dataBeanList);
        }
        //presenter.arrive(dataBean.getDam_id(), getMsg(), "colockin", latitude,longitude, "http://pic1.win4000.com/wallpaper/2019-07-30/5d3fef07ecfea.jpg", "http://pic1.win4000.com/wallpaper/2019-07-30/5d3fe70d20e4e_270_185.jpg");
        /*for (int i = 0; i < pictureArray.length; i++) {
            if (!TextUtils.isEmpty(pictureArray[i])) {
                photoNum++;
                Log.d("提示", pictureArray[i].trim() + "////");
                Luban.get(this)
                        .load(new File(pictureArray[i]))                     //传人要压缩的图片
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
                                Log.e(TAG, e.getMessage());
                                // TODO 当压缩过去出现问题时调用
                            }
                        }).launch();    //启动压缩

                //upload(new File(pictureArray[i].trim()));
                //presenter.arrive(dataBean.getDam_id(), simpleAddress, getMsg(), "colockin", latitude, longitude, "http://pic1.win4000.com/wallpaper/2019-07-30/5d3fef07ecfea.jpg", "http://pic1.win4000.com/wallpaper/2019-07-30/5d3fe70d20e4e_270_185.jpg");

            }
        }*/
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
                            Log.e(TAG, e.getMessage());
                            // TODO 当压缩过去出现问题时调用
                        }
                    }).launch();    //启动压缩

            //upload(new File(pictureArray[i].trim()));
            //presenter.arrive(dataBean.getDam_id(), simpleAddress, getMsg(), "colockin", latitude, longitude, "http://pic1.win4000.com/wallpaper/2019-07-30/5d3fef07ecfea.jpg", "http://pic1.win4000.com/wallpaper/2019-07-30/5d3fe70d20e4e_270_185.jpg");

        }
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

    private String getMsg() {
        return etMsg.getText().toString();
    }

    @Override
    public void onSuccessArrive(BaseBean baseBean) {

    }

    @Override
    public void onErrorArrive(Throwable throwable) {

    }

    List<UploadBean.DataBean> dataBeanList = new ArrayList<>();//图片url

    @Override
    public void onSuccessUpload(UploadBean baseBean) {
        if (baseBean.getIsErr() == 0) {
            uploadNum++;
            dataBeanList.add(baseBean.getData());
            Log.e("提示上传后文件名", baseBean.getData().getPath());
            if (uploadNum >= photoNum) {
                presenter.completec(dataBean.getDam_id(), getMsg(), "waitcheck", latitude, longitude, dataBeanList);
            } else {

                photoCompress(pictureArray[uploadNum]);
            }
        } else {
            DiaLogUtils.getInstance().cancel();
            toast(baseBean.getMsg() + "");
        }
    }

    @Override
    public void onErrorUpload(Throwable throwable) {
        DiaLogUtils.getInstance().cancel();
        throwable.printStackTrace();
        toast("提交失败");
    }

    @Override
    public void onSuccesComplete(BaseBean baseBean) {
        DiaLogUtils.getInstance().cancel();
        if (baseBean.getIsErr() == 0) {
            toast("提交完成");
            finish();
        } else {
            toast(baseBean.getMsg());
        }
    }

    @Override
    public void onErrorComplete(Throwable throwable) {
        DiaLogUtils.getInstance().cancel();
        throwable.printStackTrace();
        toast("提交失败");

    }


}
