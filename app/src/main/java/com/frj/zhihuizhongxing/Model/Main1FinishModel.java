package com.frj.zhihuizhongxing.Model;

import android.util.Log;

import com.frj.zhihuizhongxing.Contract.Main1FinishContract;
import com.frj.zhihuizhongxing.Data.BaseBean;
import com.frj.zhihuizhongxing.Data.EncryptBean;
import com.frj.zhihuizhongxing.Utils.AESCBCUtil;
import com.frj.zhihuizhongxing.Web.RetrofitClient;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Flowable;

public class Main1FinishModel implements Main1FinishContract.Model {
    private String TAG="Main1FinishModel";
    @Override
    public Flowable<BaseBean> createAccident(String address, String message, Double latitude, Double longitude) {
        Map map = new HashMap();
        map.put("address", address);
        map.put("title", message);
        map.put("message","");
        map.put("latitude", latitude);
        map.put("longitude", longitude);

        String loginJson = "";
        try {
            Gson gson = new Gson();
            String jsonImgList = gson.toJson(map);
            Log.e(TAG,"发送"+jsonImgList);
            loginJson = AESCBCUtil.encrypt(jsonImgList);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return RetrofitClient.getInstance().getApi().appCreate(new EncryptBean(loginJson));
    }
}
