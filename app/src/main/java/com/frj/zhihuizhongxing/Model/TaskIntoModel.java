package com.frj.zhihuizhongxing.Model;

import com.frj.zhihuizhongxing.Contract.TaskIntoContract;
import com.frj.zhihuizhongxing.Data.EncryptBean;
import com.frj.zhihuizhongxing.Data.TaskIntoBean;
import com.frj.zhihuizhongxing.Utils.AESCBCUtil;
import com.frj.zhihuizhongxing.Web.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Flowable;

public class TaskIntoModel implements TaskIntoContract.Model {
    @Override
    public Flowable<TaskIntoBean> accidentInfo(int dam_id) {
        JSONObject json = new JSONObject();
        try {
            json.put("dam_id", dam_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String loginJson="";
        try {
            loginJson= AESCBCUtil.encrypt(json.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return RetrofitClient.getInstance().getApi().accidentInfo(new EncryptBean(loginJson));
    }
}
