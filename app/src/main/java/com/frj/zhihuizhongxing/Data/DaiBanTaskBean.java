package com.frj.zhihuizhongxing.Data;

import java.io.Serializable;
import java.util.List;

public class DaiBanTaskBean {


    /**
     * isErr : 0
     * data : [{"dam_id":96,"title":"明细账","name":"欧阳涛","phone":"","create_time":"2019-07-29 21:00:41","longitude":"114.893476","latitude":"25.857697"},{"dam_id":97,"title":"俺大爷来咯需要","name":"欧阳涛","phone":"","create_time":"2019-07-29 19:38:15","longitude":"114.893511","latitude":"25.857572"}]
     * msg :
     */

    private int isErr;
    private String msg;
    private List<DataBean> data;

    public int getIsErr() {
        return isErr;
    }

    public void setIsErr(int isErr) {
        this.isErr = isErr;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * dam_id : 96
         * title : 明细账
         * name : 欧阳涛
         * phone :
         * create_time : 2019-07-29 21:00:41
         * longitude : 114.893476
         * latitude : 25.857697
         */

        private int dam_id;
        private String title;
        private String name;
        private String phone;
        private String create_time;
        private String longitude;
        private String latitude;

        public int getDam_id() {
            return dam_id;
        }

        public void setDam_id(int dam_id) {
            this.dam_id = dam_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }
    }
}
