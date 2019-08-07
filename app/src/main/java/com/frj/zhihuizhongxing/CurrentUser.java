package com.frj.zhihuizhongxing;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import io.reactivex.disposables.Disposable;

public class CurrentUser {

    private static String company;//单位
    private static String name;
    private static String business;
    private static String email;
    private static String address;
    private static String phone;
    private static String face;
    private static double latitude;//纬度
    private static double longitude;//经度
    private static String police_no;//警号
    public  static Disposable disposable;//储存上次的上传地位时间

    private static boolean isLogin = false;

    public static boolean isLogin() {
        SharedPreferences sharedPreferences = MyApplication.getInstance().getSharedPreferences("data", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("PHPSESSID", "");
        if (TextUtils.isEmpty(token.trim())) {
            isLogin = false;
            logOut();
        } else {
            isLogin = true;
        }
        return isLogin;
    }

    public static boolean isIsLogin() {
        return isLogin;
    }

    public static void setIsLogin(boolean isLogin) {
        CurrentUser.isLogin = isLogin;
    }

    public static void logOut() {
        isLogin = false;
        company = "";
        name = "";
        business = "";
        email = "";
        address = "";
        phone = "";
        face = "";
        SharedPreferences.Editor editor = MyApplication.getInstance()
                .getSharedPreferences("data", Context.MODE_PRIVATE)
                .edit();
        editor.putString("PHPSESSID", "").apply();
    }

    public static String getCompany() {
        return company;
    }

    public static void setCompany(String company) {
        CurrentUser.company = company;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        CurrentUser.name = name;
    }

    public static String getBusiness() {
        return business;
    }

    public static void setBusiness(String business) {
        CurrentUser.business = business;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        CurrentUser.email = email;
    }

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        CurrentUser.address = address;
    }

    public static String getPhone() {
        return phone;
    }

    public static void setPhone(String phone) {
        CurrentUser.phone = phone;
    }

    public static String getFace() {
        return face;
    }

    public static void setFace(String face) {
        CurrentUser.face = face;
    }

    public static double getLatitude() {
        return latitude;
    }

    public static void setLatitude(double latitude) {
        CurrentUser.latitude = latitude;
    }

    public static double getLongitude() {
        return longitude;
    }

    public static void setLongitude(double longitude) {
        CurrentUser.longitude = longitude;
    }

    public static String getPolice_no() {
        return police_no;
    }

    public static void setPolice_no(String police_no) {
        CurrentUser.police_no = police_no;
    }
}
