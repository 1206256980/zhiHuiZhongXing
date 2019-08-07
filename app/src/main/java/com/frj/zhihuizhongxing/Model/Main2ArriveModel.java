package com.frj.zhihuizhongxing.Model;

import android.util.Log;

import com.frj.zhihuizhongxing.Contract.Main2ArriveContract;
import com.frj.zhihuizhongxing.Contract.Main2Contract;
import com.frj.zhihuizhongxing.Data.BaseBean;
import com.frj.zhihuizhongxing.Data.DaiBanTaskBean;
import com.frj.zhihuizhongxing.Data.EncryptBean;
import com.frj.zhihuizhongxing.Data.UploadBean;
import com.frj.zhihuizhongxing.Tools.StringUtils;
import com.frj.zhihuizhongxing.Utils.AESCBCUtil;
import com.frj.zhihuizhongxing.Web.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.reactivex.Flowable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class Main2ArriveModel implements Main2ArriveContract.Model {
    private String TAG = "Main2ArriveModel";

    @Override
    public Flowable<BaseBean> arrive(int dam_id, String address, String message, String type, double latitude, double longitude, List<UploadBean.DataBean> dataBeanList) {
        JSONObject json = new JSONObject();
        try {
            json.put("dam_id", dam_id);
            json.put("address", address);
            json.put("message", message);
            json.put("type", type);
            json.put("latitude", latitude);
            json.put("longitude", longitude);
            String photo = "";
            for (int i = 0; i < dataBeanList.size(); i++) {
                if (i == 0) {
                    photo = StringUtils.photoUrl(dataBeanList.get(i).getPath());
                } else {
                    photo += "," + StringUtils.photoUrl(dataBeanList.get(i).getPath());
                }


            }
            json.put("pics", photo);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String loginJson = "";
        try {
            Log.e(TAG, json.toString());
            loginJson = AESCBCUtil.encrypt(json.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return RetrofitClient.getInstance().getApi().accidentSendmsg(new EncryptBean(loginJson));
    }

    @Override
    public Flowable<UploadBean> upload(File file) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);//表单类型
        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String time = df.format(new Date());// new Date()为获取当前系统时间


        builder.addFormDataPart("dcittime", time);
        builder.addFormDataPart("file", file.getName(), body);
        Log.e("提示上传后文件前",file.getName()+"");
        List<MultipartBody.Part> parts = builder.build().parts();
        return RetrofitClient.getInstance().getApi().upload(parts);
    }

    @Override
    public Flowable<BaseBean> completec(int dam_id, String message, String type, double latitude, double longitude, List<UploadBean.DataBean> dataBeanList) {
        JSONObject json = new JSONObject();
        try {
            json.put("dam_id", dam_id);
            json.put("address", "");
            json.put("message", message);
            json.put("type", type);
            json.put("latitude", latitude);
            json.put("longitude", longitude);
            String photo = "";
            for (int i = 0; i < dataBeanList.size(); i++) {
                if (i == 0) {
                    photo = StringUtils.photoUrl(dataBeanList.get(i).getPath());
                } else {
                    photo += "," + StringUtils.photoUrl(dataBeanList.get(i).getPath());
                }


            }
            json.put("pics", photo);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String loginJson = "";
        try {
            Log.e(TAG, json.toString());
            loginJson = AESCBCUtil.encrypt(json.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return RetrofitClient.getInstance().getApi().accidentSendmsg(new EncryptBean(loginJson));
    }
}
