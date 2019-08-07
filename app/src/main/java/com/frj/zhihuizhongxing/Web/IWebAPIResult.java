package com.frj.zhihuizhongxing.Web;

/**
 * WebAPI返回数据接口
 */

public interface IWebAPIResult {
    void setError(String errorMessage, int resultCode);
    IWebAPIResult setResult(String result) ;
}
