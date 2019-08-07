package com.frj.zhihuizhongxing.Tools;

public class StringUtils {

    public static String photoUrl(String uploadUrl){
       return uploadUrl.replace("./","http://abncp.jxdcit.cn/");
    }
}
