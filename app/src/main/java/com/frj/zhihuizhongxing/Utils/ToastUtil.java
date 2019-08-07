package com.frj.zhihuizhongxing.Utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;



/**
 * 项目名称：
 * 类描述：toast通用类
 * 创建人：renhaijun
 * 创建时间：2017/7/5 9:08
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ToastUtil {
    private static String TAG = ToastUtil.class.getSimpleName();
    public static Toast mToast;

    private ToastUtil()
    {
		/* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

//    public static boolean isShow = true;

    /**
     * 短时间显示Toast
     * renhaijun add
     * @param context
     * @param message
     */
    public static void showShort(final Context context, final CharSequence message) {
        if (context == null){
            return;
        }
        String tempMessage = message.toString();
        if (tempMessage.contains("sessionId不合法") || tempMessage.contains("会话id无效")){
            return;
        }
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (context == null){
                    return;
                }
                if(mToast==null){
                    mToast= Toast.makeText(context, message, Toast.LENGTH_SHORT);
                }else{
                    mToast.setText(message);
                    mToast.setDuration(Toast.LENGTH_SHORT);
                }
                if (context == null){
                    return;
                }
                mToast.show();
            }
        },10);
    }


    /**
     * 长时间显示Toast
     * renhaijun add
     * @param context
     * @param message
     */
    public static void showLong(final Context context, final CharSequence message) {
        if (context == null){
            Log.e(TAG,"弹框toash异常 context == null--------------------"  );
            return;
        }

        String tempMessage = message.toString();
        if (tempMessage.contains("sessionId不合法") || tempMessage.contains("会话id无效")){
            return;
        }

        try{
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(mToast==null){
                        mToast= Toast.makeText(context, message, Toast.LENGTH_LONG);
                    }else{
                        mToast.setText(message);
                        mToast.setDuration(Toast.LENGTH_LONG);
                    }
                    if (context == null){
                        return;
                    }
                    mToast.show();
                }
            },10);
        }catch (Exception e){
            Log.e(TAG,"弹框toash异常 e: " + e.toString());

        }
    }


}
