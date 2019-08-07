package com.frj.zhihuizhongxing.Data;

public class UploadBean {

    /**
     * isErr : 0
     * data : {"path":"./accident/1564553563.jpg"}
     * msg :
     */

    private int isErr;
    private DataBean data;
    private String msg;

    public int getIsErr() {
        return isErr;
    }

    public void setIsErr(int isErr) {
        this.isErr = isErr;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * path : ./accident/1564553563.jpg
         */

        private String path;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
