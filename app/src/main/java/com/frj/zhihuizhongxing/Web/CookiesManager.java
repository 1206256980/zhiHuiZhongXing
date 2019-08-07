package com.frj.zhihuizhongxing.Web;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;


import com.frj.zhihuizhongxing.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Cookies管理，保存用户状态
 */

public class CookiesManager implements CookieJar {

    @Override
    public void saveFromResponse(@NonNull HttpUrl url, @NonNull List<Cookie> cookies) {
        MyApplication context = MyApplication.getInstance();
        if (cookies.size() > 0) {
            for (Cookie item : cookies) {
                if (item.name().equals("PHPSESSID")) {
                    try {
                        JSONObject jsonObject = new JSONObject()
                                .put("name", item.name())
                                .put("value", item.value())
                                .put("expiresAt", item.expiresAt())
                                .put("domain", item.domain())
                                .put("path", item.path())
                                .put("secure", item.secure())
                                .put("httpOnly", item.httpOnly())
                                .put("hostOnly", item.hostOnly())
                                .put("persistent", item.persistent());

                        SharedPreferences.Editor editor = context.getSharedPreferences("data", Context.MODE_PRIVATE).edit();
                        editor.putString("PHPSESSID", jsonObject.toString());
                        editor.apply();
                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }
                }

            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(@NonNull HttpUrl url) {
        MyApplication context = MyApplication.getInstance();
        List<Cookie> cookies = new ArrayList<>();
       /* Cookie serviceCookie = context.getServiceCookie();
        if (serviceCookie != null) {
            cookies.add(serviceCookie);
        }*/
        SharedPreferences sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("PHPSESSID", "");
        if (!token.equals("")) {
            try {
                JSONObject jsonObject = new JSONObject(token);
                Cookie.Builder builder = new Cookie.Builder()
                        .name(jsonObject.getString("name"))
                        .value(jsonObject.getString("value"))
                        .expiresAt(jsonObject.getLong("expiresAt"))
                        .path(jsonObject.getString("path"));

                if(jsonObject.getBoolean("secure")) builder.secure();
                if(jsonObject.getBoolean("httpOnly")) builder.httpOnly();
                String domain = jsonObject.getString("domain");
                if(jsonObject.getBoolean("hostOnly")) builder.hostOnlyDomain(domain);
                else builder.domain(domain);
                cookies.add(builder.build());
            }
            catch (JSONException e){
                e.printStackTrace();
            }
        }
        return cookies;
    }
}

