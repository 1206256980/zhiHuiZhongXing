package com.frj.zhihuizhongxing.Web;


import android.support.annotation.NonNull;


import com.frj.zhihuizhongxing.MyApplication;
import com.frj.zhihuizhongxing.R;
import com.frj.zhihuizhongxing.Utils.NetUtils;
import com.frj.zhihuizhongxing.Utils.ToastUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author azheng
 * @date 2018/4/17.
 * GitHub：https://github.com/RookieExaminer
 * email：wei.azheng@foxmail.com
 * description：
 */
public class RetrofitClient {

    private static volatile RetrofitClient instance;
    private APIService apiService;
    private String baseUrl = "http://abncp.jxdcit.cn/server/";
    ;

    private RetrofitClient() {
    }

    public static RetrofitClient getInstance() {
        if (instance == null) {
            synchronized (RetrofitClient.class) {
                if (instance == null) {
                    instance = new RetrofitClient();
                }
            }
        }
        return instance;
    }

    OkHttpClient client;

    private OkHttpClient getClient() {
        //初始化一个client,不然retrofit会自己默认添加一个
        if (client == null) {
            client = new OkHttpClient().newBuilder()
                    .connectTimeout(60, TimeUnit.SECONDS).
                            readTimeout(60, TimeUnit.SECONDS).
                            writeTimeout(60, TimeUnit.SECONDS)
                    //设置Header
                    .addInterceptor(getHeaderInterceptor())
                    //设置拦截器
                    .addInterceptor(getInterceptor())
                    .cookieJar(new CookiesManager())
                    .build();
        }
        return client;
    }

    /**
     * 设置Header
     *
     * @return
     */
    private Interceptor getHeaderInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        //添加Token
                        .header("token", "");

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };

    }

    /**
     * 设置拦截器
     *
     * @return
     */
    private Interceptor getInterceptor() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        //显示日志
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return interceptor;
    }

    public APIService getApi() {
        if (!NetUtils.CheckNetwork(MyApplication.getInstance())) {
            ToastUtil.showLong(MyApplication.getInstance(), "网络连接已断开，请检查");
        }

        Retrofit retrofit = new Retrofit.Builder()
                .client(getClient())
                //设置网络请求的Url地址
                .baseUrl(baseUrl)
                //设置数据解析器
                .addConverterFactory(GsonConverterFactory.create())
                //设置网络请求适配器，使其支持RxJava与RxAndroid
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        //创建—— 网络请求接口—— 实例
        apiService = retrofit.create(APIService.class);
        return apiService;
    }

}
