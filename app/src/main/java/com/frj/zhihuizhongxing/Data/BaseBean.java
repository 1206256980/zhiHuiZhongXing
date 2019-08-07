package com.frj.zhihuizhongxing.Data;

import android.util.Log;

import com.frj.zhihuizhongxing.Web.IWebAPIResult;

import org.json.JSONException;
import org.json.JSONObject;

public class BaseBean implements IWebAPIResult {

    /**
     * isErr : 0
     * data : 创建事故业务成功
     * msg :
     */

    private int isErr;
    private String data;
    private String msg;

    public int getIsErr() {
        return isErr;
    }

    public void setIsErr(int isErr) {
        this.isErr = isErr;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public void setError(String errorMessage, int resultCode) {

    }

    protected JSONObject jsonObject;

    @Override
    public IWebAPIResult setResult(String result) {
        try {
            Log.d("提示返回json:", result + "");
            jsonObject = new JSONObject(result);
            isErr = jsonObject.getInt("isErr");
            this.msg = jsonObject.getString("msg");
        } catch (JSONException e) {
            e.printStackTrace();
            isErr = 1;
            msg = e.getMessage();
        }
        return this;
    }
}
