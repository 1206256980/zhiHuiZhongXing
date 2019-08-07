package com.frj.zhihuizhongxing.Model;

import android.util.Log;

import com.frj.zhihuizhongxing.Contract.MainContract;
import com.frj.zhihuizhongxing.Data.BaseBean;
import com.frj.zhihuizhongxing.Data.EncryptBean;
import com.frj.zhihuizhongxing.Data.MainBean;
import com.frj.zhihuizhongxing.Data.UserBean;
import com.frj.zhihuizhongxing.Utils.AESCBCUtil;
import com.frj.zhihuizhongxing.Web.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Flowable;

public class MainModel implements MainContract.Model {
    @Override
    public Flowable<MainBean> addCoordinate(String longlat) {
        return RetrofitClient.getInstance().getApi().addCoordinate(longlat);
    }

    @Override
    public Flowable<UserBean> userinfo() {
        return RetrofitClient.getInstance().getApi().userinfo();
    }
    private String TAG="";

    @Override
    public Flowable<BaseBean> updatePostion(double latitude, double longitude) {
        JSONObject json = new JSONObject();
        try {
            json.put("latitude", latitude);
            json.put("longitude", longitude);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String loginJson = "";
        try {
            loginJson = AESCBCUtil.encrypt(json.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return RetrofitClient.getInstance().getApi().updatePostion(new EncryptBean(loginJson));
    }
}
