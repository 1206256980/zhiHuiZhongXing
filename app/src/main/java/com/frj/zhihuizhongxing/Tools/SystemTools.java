package com.frj.zhihuizhongxing.Tools;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;


/**
 * 工具类
 */

public class SystemTools {
    private final static String TRANSFORMATION = "DES/CBC/PKCS5Padding";//DES是加密方式 CBC是工作模式 PKCS5Padding是填充模式
    private final static String ALGORITHM = "DES";//DES是加密方式

    /**
     * DES解密
     *
     * @param encryptText 加密字符串
     * @param key         密钥
     * @return 加密后的字符串
     */
    public static String DESEncrypt(String encryptText, String key) {
        try {
            byte[] encryptByte = Base64.decode(encryptText, Base64.NO_WRAP);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            IvParameterSpec iv = new IvParameterSpec(key.getBytes());
            DESKeySpec dks = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, keyFactory.generateSecret(dks), iv);
            byte[] original = cipher.doFinal(encryptByte);
            return new String(original, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 判断手机号码
     * @param phone
     * @return
     */
    public static boolean valPhone(String phone){
        return Pattern.matches("^1[3|4|5|7|8][0-9]\\d{4,8}$",phone);
    }



    /**
     * 验证手机号码
     *
     * @param phone 手机号码
     * @return 是否有效
     */
    public static boolean checkPhoneNumber(String phone) {
        return Pattern.matches("^\\d{11}$", phone);
    }


    /**
     * 显示键盘
     */
    public static void ShowKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm != null) {
            imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);
        }

    }

    public static void hideKeybord(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);

        }
    }


    /**
     * 检查是否有网络
     */
    public static boolean isNetworkAvailable(Context context) {

        NetworkInfo info = getNetworkInfo(context);
        boolean isNetwork=info != null && info.isAvailable();
        if(!isNetwork){
            Toast.makeText(context,"请检查网络",Toast.LENGTH_SHORT).show();
        }
        return isNetwork;
    }

    private static NetworkInfo getNetworkInfo(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }


    /**
     * 获取本地软件版本号
     */
    public static int getLocalVersion(Context ctx) {
        int localVersion = 0;
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    /**
     * 获取本地软件版本号名称
     */
    public static String getLocalVersionName(Context ctx) {
        String localVersion = "";
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    public static boolean valEmail(String email) {
        return Pattern.matches("^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w{2,3}){1,3})$", email);
    }

    public static File CreateFile(String fileName) {
        File outfile = new File(Environment.getExternalStorageDirectory(),
                "/temp/" + fileName);
        if (!outfile.getParentFile().exists()) outfile.getParentFile().mkdirs();
        return outfile;
    }

    public static String NewFile(String fileName) {
        File dir = new File(Environment.getExternalStorageDirectory(),
                "/FastCat");
        if (!dir.exists()) dir.mkdirs();

        return dir.getPath() + "/" + fileName;
    }

    public static String getStorageDirectory(){
        File dir = new File(Environment.getExternalStorageDirectory(),
                "/FastCat");
        if (!dir.exists()) dir.mkdirs();
        return dir.getPath();
    }

    public static File GetCacheDirectory() {
        File dir = new File(Environment.getExternalStorageDirectory(), "/Alpaca/Cache");
        if (!dir.exists()) dir.mkdirs();
        return dir;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String GetFilePath(Context context, Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    private static String getDataColumn(Context context, Uri uri, String selection,
                                        String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            Log.d("applog", "getDataColumn finally");
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


    public static void install(Context context, String filePath, int requestCode) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (context.getPackageManager().canRequestPackageInstalls()) {
                installAPK(context, filePath);
            } else {
                //请求安装未知应用来源的权限
                //ActivityCompat.requestPermissions((Activity)context, new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES}, requestCode);
                Toast.makeText(context,"需要同意安装未知来源应用", Toast.LENGTH_LONG).show();
                Uri packageURI = Uri.parse("package:" + context.getPackageName());
                Intent intent =new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,packageURI);
                ((Activity)context).startActivityForResult(intent, requestCode);
            }
        } else {
            installAPK(context, filePath);
        }
    }

    public static void installAPK(Context context, String filePath) {
        File apkFile = new File(Uri.parse(filePath).getPath());
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(
                    context
                    , context.getPackageName() + ".fileprovider"
                    , apkFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.parse(filePath), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }

    /**
     * 删除文件或文件夹（包括子文件）
     *
     * @param file
     */
    public static void DeleteFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                DeleteFile(f);
            }
            file.delete();
        } else if (file.exists()) {
            file.delete();
        }
    }

    public static void CopyToClipboard(Context context, String mAddress) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData mClipData = ClipData.newPlainText("Label", mAddress);
        if (cm != null) cm.setPrimaryClip(mClipData);
    }

    public static boolean checkNickName(String nickName) {
//        return Pattern.matches("^\\w+$", nickName);
        String all = "^[\\u4e00-\\u9fa5]+$";
        Pattern pattern = Pattern.compile(all);
        return Pattern.matches(all,nickName);
    }

    /**
     * 获取屏幕旋转状态
     *
     * @return
     */
    public static boolean getScreenRotation(Context context) {
        boolean isOpen = false;
        try {
            int status = Settings.System.getInt(context.getContentResolver(), "accelerometer_rotation");
            isOpen = status == 1;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return isOpen;
    }

    public static int dip2px(float dpValue) {
        float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @SuppressLint("HardwareIds")
    public static String getUniqueId(Context context) {
        if(context == null ) return null;
        String androidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        String id = androidID + Build.SERIAL;
        try {
            return toMD5(id);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return id;
        }
    }


    private static String toMD5(String text) throws NoSuchAlgorithmException {
        //获取摘要器 MessageDigest
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        //通过摘要器对字符串的二进制字节数组进行hash计算
        byte[] digest = messageDigest.digest(text.getBytes());

        StringBuilder sb = new StringBuilder();
        for (byte aDigest : digest) {
            //循环每个字符 将计算结果转化为正整数;
            int digestInt = aDigest & 0xff;
            //将10进制转化为较短的16进制
            String hexString = Integer.toHexString(digestInt);
            //转化结果如果是个位数会省略0,因此判断并补0
            if (hexString.length() < 2) {
                sb.append(0);
            }
            //将循环结果添加到缓冲区
            sb.append(hexString);
        }
        //返回整个结果
        return sb.toString();
    }

    public static boolean checkUrl(String url) {
        if(url == null) return false;
        return Pattern.matches("^http(s)?://\\S*$", url.toLowerCase());
    }
}
