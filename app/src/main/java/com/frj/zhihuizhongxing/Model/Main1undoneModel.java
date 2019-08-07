package com.frj.zhihuizhongxing.Model;

import android.util.Log;

import com.frj.zhihuizhongxing.Contract.Main1undoneContract;
import com.frj.zhihuizhongxing.Data.BaseBean;
import com.frj.zhihuizhongxing.Data.DaiBanTaskBean;
import com.frj.zhihuizhongxing.Data.EncryptBean;
import com.frj.zhihuizhongxing.Utils.AESCBCUtil;
import com.frj.zhihuizhongxing.Web.RetrofitClient;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Flowable;

public class Main1undoneModel implements Main1undoneContract.Model {
    @Override
    public Flowable<DaiBanTaskBean> accident(int page, int length, String accident_status) {
        //List<Map<String, Object>> imgList=new ArrayList<>();
        Map map = new HashMap();
        map.put("page", page);//map里面装有yes
        map.put("length", length);
        map.put("accident_status", accident_status);

        String loginJson = "";
        try {
            Gson gson = new Gson();
            String jsonImgList = gson.toJson(map);
            loginJson = AESCBCUtil.encrypt(jsonImgList);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return RetrofitClient.getInstance().getApi().accident(new EncryptBean(loginJson));
    }
    String TAG="Main1undoneModel";

    @Override
    public Flowable<BaseBean> accidentSendmsg(int dam_id, String type,double latitude,double longitude) {

        JSONObject json = new JSONObject();
        try {
            json.put("dam_id", dam_id);
            json.put("type", type);
            json.put("latitude", latitude);
            json.put("longitude",longitude);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String loginJson = "";
        try {
            Log.e(TAG,json.toString());
            loginJson = AESCBCUtil.encrypt(json.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return RetrofitClient.getInstance().getApi().accidentSendmsg(new EncryptBean(loginJson));
    }
}
