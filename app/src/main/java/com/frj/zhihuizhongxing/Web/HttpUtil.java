package com.frj.zhihuizhongxing.Web;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;


/**
 * 发送http请求工具类
 */

public class HttpUtil {

    private static OkHttpClient mClient;

    private static OkHttpClient getClient() {
        if (mClient == null) {
            mClient = new OkHttpClient().newBuilder().cookieJar(new CookiesManager()).build();
        }
        return mClient;
    }

    /**
     * 使用GET方法异步获取数据
     *
     * @param url
     * @param callback
     */
    public static void GetDataSync(String url, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * 使用GET方法同步获取数据
     *
     * @param url
     * @return
     */
    public static String GetData(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response != null && response.code() == 200) {
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用GET方法同步获取数据
     *
     * @param url
     * @return
     */
    public static Response GetResponse(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            return client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用get方法检测链接是否可用
     *
     * @param url
     * @return
     */
    public static boolean checkAvailable(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            return response != null && response.code() == 200;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 使用POST方式异步提交数据
     *
     * @param url
     * @param json
     * @param callback
     */
    private static Call PostDataSync(String url, String json, okhttp3.Callback callback) {

        OkHttpClient mClient = getClient();
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                , json);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = mClient.newCall(request);
        call.enqueue(callback);
        return call;
    }

    private static <TResult extends IWebAPIResult> okhttp3.Callback CreateCallback(final OnRequestComplete<TResult> complete) {
        okhttp3.Callback callback = new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                if (complete == null) return;
                Log.e("提示错误请求URL", call.request().url() + "");
                TResult t = complete.getInstance();
                t.setError("无法连接服务器", -10);
                Log.d("WebResult", "onFailure: " + e.getMessage());

                /*AppendFileLog("接口返回数据：无法连接服务器");
                AppendFileLog("请求的URL: " + call.request().url());
                AppendFileLog("接口返回数据：onFailure: " + e.getMessage());*/

                complete.complete(t);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (complete == null) return;
                TResult t = complete.getInstance();
                int code = response.code();
                if (code == 200) {
                    Log.e("提示正确请求URL", call.request().url() + "");
                    String responseText = response.body().string();
                    Log.d("WebResult", responseText);
                    t.setResult(responseText);

                    /*AppendFileLog("请求的URL: " + call.request().url());

                    AppendFileLog("接口返回数据：code  " + response.code());
                    AppendFileLog("接口返回数据：response.body().string() : " + responseText);*/

                } else {
                    Log.d("WebResult", "ResponseCode:" + code);
                    t.setError(String.format("Response Code: %s", code), -10);

                    /*AppendFileLog("请求的URL: " + call.request().url());

                    AppendFileLog("接口返回数据：code  " + response.code());
                    AppendFileLog("接口返回数据：" + String.format("Response Code: %s", code));*/

                }
                complete.complete(t);
            }
        };
        return callback;
    }

    /**
     * 使用POST方式异步提交数据
     *
     * @param url       请求地址
     * @param json      请求json字符串
     * @param complete  请求完成后的回调
     * @param <TResult> IWebAPIResult
     */
    public static <TResult extends IWebAPIResult> Call PostDataSync(String url, String json, OnRequestComplete<TResult> complete) {
        return PostDataSync(url, json, CreateCallback(complete));
    }

    /**
     * 使用PUT方式更改数据
     *
     * @param url       请求地址
     * @param json      请求json字符串
     * @param complete  请求完成后的回调
     * @param <TResult> IWebAPIResult
     */
    public static <TResult extends IWebAPIResult> Call PutDataSync(String url, String json, OnRequestComplete<TResult> complete) {
        OkHttpClient client = new OkHttpClient().newBuilder().cookieJar(new CookiesManager()).build();
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                , json);
        Request request = new Request.Builder()
                .url(url)
                .put(requestBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(CreateCallback(complete));
        return call;
    }

    /**
     * 使用GET方式获取数据
     *
     * @param url       请求地址
     * @param complete  请求完成后的回调
     * @param <TResult> IWebAPIResult
     */
    public static <TResult extends IWebAPIResult> Call GetDataSync(String url, OnRequestComplete<TResult> complete) {
        OkHttpClient client = new OkHttpClient().newBuilder().cookieJar(new CookiesManager()).build();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(CreateCallback(complete));
        return call;
    }


    /**
     * 使用 DELETE方式删除数据
     *
     * @param url       请求地址
     * @param json      请求json字符串
     * @param complete  请求完成后的回调
     * @param <TResult> IWebAPIResult
     */
    public static <TResult extends IWebAPIResult> void DeleteDataSync(String url, String json, OnRequestComplete<TResult> complete) {
        OkHttpClient client = new OkHttpClient().newBuilder().cookieJar(new CookiesManager()).build();
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                , json);
        Request request = new Request.Builder()
                .url(url)
                .delete(requestBody)
                .build();

        client.newCall(request).enqueue(CreateCallback(complete));
    }


    public static <TResult extends IWebAPIResult> Call postFile(String url, HashMap<String, Object> paramsMap, OnUploadProgress progress, OnRequestComplete<TResult> complete) {
        OkHttpClient client = getUploadClient();
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);


        //追加参数
        for (String key : paramsMap.keySet()) {
            Object object = paramsMap.get(key);
            if (!(object instanceof File)) {
                builder.addFormDataPart(key, object.toString());
            } else {
                File file = (File) object;
                builder.addFormDataPart(key, file.getName(),
                        progress == null ? RequestBody.create(null, file) :
                                createProgressRequestBody(key, file, progress));
                //RequestBody.create(MediaType.parse("application/octet-stream"), file)
            }
        }
        //创建RequestBody
        RequestBody body = builder.build();
        //创建Request
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Call call = client.newCall(request);
        call.enqueue(CreateCallback(complete));
        return call;
    }


    private static OkHttpClient uploadClient;

    private static OkHttpClient getUploadClient() {
        if (uploadClient == null) {
            uploadClient = new OkHttpClient()
                    .newBuilder()
                    .writeTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .cookieJar(new CookiesManager())
                    .build();
        }
        return uploadClient;
    }

    public static <TResult extends IWebAPIResult> Call postFile(String url, byte[] data, HashMap<String, String> paramsMap, OnRequestComplete<TResult> complete) {
        OkHttpClient client = getUploadClient();

        RequestBody requestBody = FormBody.create(MediaType.parse("text/html"), data);
        Headers headers = Headers.of(paramsMap);

        Request request = new Request.Builder()
                .url(url)
                .headers(headers)
                .post(requestBody)
                .build();

        Call call = client.newCall(request);
        call.enqueue(CreateCallback(complete));
        return call;
    }

    private static RequestBody createProgressRequestBody(final String fileType, final File file, final OnUploadProgress callBack) {
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return MediaType.parse("application/octet-stream");
            }

            @Override
            public long contentLength() {
                return file.length();
            }

            @Override
            public void writeTo(BufferedSink sink) {
                Source source;
                try {
                    source = Okio.source(file);
                    Buffer buf = new Buffer();
                    long remaining = contentLength();
                    long current = 0;
                    for (long readCount; (readCount = source.read(buf, 2048)) != -1; ) {
                        sink.write(buf, readCount);
                        current += readCount;
                        if (callBack != null) callBack.progress(fileType, current, remaining);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        };
    }

    public interface OnRequestComplete<TResult extends IWebAPIResult> {
        TResult getInstance();

        void complete(TResult result);
    }

    public interface OnUploadProgress {
        void progress(String key, long current, long total);
    }


    public static void method1(String file, String conent) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
            out.write(conent);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 追加文件：使用FileWriter
     *
     * @param fileName
     * @param content
     */
    public static void method2(String fileName, String content) {
        FileWriter writer = null;
        try {
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            writer = new FileWriter(fileName, true);
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 追加文件：使用RandomAccessFile
     *
     * @param fileName 文件名
     * @param content  追加的内容
     */
    public static void method3(String fileName, String content) {
        RandomAccessFile randomFile = null;
        try {
            // 打开一个随机访问文件流，按读写方式
            randomFile = new RandomAccessFile(fileName, "rw");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            // 将写文件指针移到文件尾。
            randomFile.seek(fileLength);
            randomFile.writeBytes(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (randomFile != null) {
                try {
                    randomFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /*public static void AppendFileLog(String content) {

        if (BuildConfig.IS_OUTPUTRECORD) {

            String sdCardDir = Environment.getExternalStorageDirectory().getAbsolutePath();
            File file = new File(sdCardDir, "kuaitoutiao.txt");

            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                method1(file.getAbsolutePath(), content + "\n");
            } else {
                method2(file.getAbsolutePath(), content + "\n");
//            method1(file.getAbsolutePath(), content + "\n");
            }
        }
    }


    public static void RecordCrashLog(String content) {
        String sdCardDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(sdCardDir, "kuaitoutiao_crash.txt");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            method1(file.getAbsolutePath(), content + "\n");
        } else {
            method2(file.getAbsolutePath(), content + "\n");
//            method1(file.getAbsolutePath(), content + "\n");
        }
    }*/
}
