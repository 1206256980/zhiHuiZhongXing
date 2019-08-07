package com.frj.zhihuizhongxing.Utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * 项目名称：
 * 类描述：判断网络工具类
 * 创建人：renhaijun
 * 创建时间：2016/4/18 10:45
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class NetUtils {

    /**
     * 判断网络连接是否打开
     */
    public static boolean CheckNetwork(Context ctx) {
        boolean flag = false;
        try{
            ConnectivityManager netManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (netManager.getActiveNetworkInfo() != null) {
                flag = netManager.getActiveNetworkInfo().isAvailable();
            }
            return flag;
        }catch (Exception e){
            return flag;
        }
    }

    /**
     * 判断有无网络链接
     * @param mContext
     * @return
     */
    public static boolean checkNetworkInfo(Context mContext) {
        ConnectivityManager conMan = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo.State mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        NetworkInfo.State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        if (mobile == NetworkInfo.State.CONNECTED || mobile == NetworkInfo.State.CONNECTING) {
            return true;
        }
        if (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING) {
            return true;
        }
        return false;
    }


    /**
     * 判断是否是wifi连接
     */
    public static boolean isWifi(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm == null) {
            return false;
        }
        return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;

    }
    /**
     * 判断是否是wifi连接
     */
    public static boolean isWifi2(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

//        if (cm == null)
//            return false;
//        return cm.getActiveNetworkInfo().getStatus() == ConnectivityManager.TYPE_WIFI;
        NetworkInfo networkINfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkINfo != null
                && networkINfo.getState()== NetworkInfo.State.CONNECTED) {
            return true;
        }
        return false;

    }
    /**
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity)
    {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings",
                "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }

    /**
     * 判断当前网络状态，2G,3G,4G,wifi. renahijun add
     */
    public static String getCurrentNetType(Context context) {
        String type = "";
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) {
            type = "null";
        } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
            type = "wifi";
        } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
            int subType = info.getSubtype();
            if (subType == TelephonyManager.NETWORK_TYPE_CDMA || subType == TelephonyManager.NETWORK_TYPE_GPRS
                    || subType == TelephonyManager.NETWORK_TYPE_EDGE) {
                type = "2g";
            } else if (subType == TelephonyManager.NETWORK_TYPE_UMTS || subType == TelephonyManager.NETWORK_TYPE_HSDPA
                    || subType == TelephonyManager.NETWORK_TYPE_EVDO_A || subType == TelephonyManager.NETWORK_TYPE_EVDO_0
                    || subType == TelephonyManager.NETWORK_TYPE_EVDO_B) {
                type = "3g";
            } else if (subType == TelephonyManager.NETWORK_TYPE_LTE) {// LTE是3g到4g的过渡，是3.9G的全球标准
                type = "4g";
            }
        }
        return type;
    }

}
