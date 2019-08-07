package com.frj.zhihuizhongxing.Model;

import android.util.Log;

import com.frj.zhihuizhongxing.Contract.LoginContract;
import com.frj.zhihuizhongxing.Data.EncryptBean;
import com.frj.zhihuizhongxing.Data.LoginBean;
import com.frj.zhihuizhongxing.Utils.AESCBCUtil;
import com.frj.zhihuizhongxing.Web.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Flowable;
import okhttp3.RequestBody;

public class LoginModel implements LoginContract.Model {

    @Override
    public Flowable<LoginBean> login(String username, String password,String equipment) {
        JSONObject json = new JSONObject();
        try {
            json.put("equipment", equipment);
            json.put("password", password);
            json.put("account", username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String loginJson="";
        try {
            loginJson= AESCBCUtil.encrypt(json.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return RetrofitClient.getInstance().getApi().login(new EncryptBean(loginJson));
    }
}
