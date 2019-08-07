package com.frj.zhihuizhongxing.Data;

/**
 * Create Time: 2017/6/17.
 *
 * @author RyanLee
 */

public class AdPageInfo {
    /**
     * 广告标题
     */
    private String title;
    /**
     * 广告图片url
     */
    private String picUrl;
    /**
     * 图片点击url
     */
    private String clickUlr;
    /**
     * 顺序
     */
    private int order;

    /**
     * 是否可以下载
     */
    private boolean isDownloadFile=false;

    public AdPageInfo(String title, String picUrl, String clickUlr, int order) {
        this.title = title;
        this.picUrl = picUrl;
        this.clickUlr = clickUlr;
        this.order = order;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public String getClickUlr() {
        return clickUlr;
    }

    public int getOrder() {
        return order;
    }

    public boolean isDownloadFile() {
        return isDownloadFile;
    }

    public AdPageInfo setDownloadFile(boolean downloadFile) {
        isDownloadFile = downloadFile;
        return this;
    }

    @Override
    public String toString() {
        return "AdPageInfo{" +
                "title='" + title + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", clickUlr='" + clickUlr + '\'' +
                ", order=" + order +
                '}';
    }

}
