package com.frj.zhihuizhongxing.Model;

import com.frj.zhihuizhongxing.Contract.Main2Contract;
import com.frj.zhihuizhongxing.Data.DaiBanTaskBean;
import com.frj.zhihuizhongxing.Data.EncryptBean;
import com.frj.zhihuizhongxing.Utils.AESCBCUtil;
import com.frj.zhihuizhongxing.Web.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Flowable;

public class Main2Model implements Main2Contract.Model {
    @Override
    public Flowable<DaiBanTaskBean> currentTaskList(int page, int length, String accident_status) {//当前列表
        JSONObject json = new JSONObject();
        try {
            json.put("page", page);
            json.put("length", length);
            json.put("accident_status", accident_status);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String taskJson="";
        try {
            taskJson= AESCBCUtil.encrypt(json.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return RetrofitClient.getInstance().getApi().currentTask(new EncryptBean(taskJson));
    }
}
