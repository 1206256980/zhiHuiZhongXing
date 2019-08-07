package com.frj.zhihuizhongxing.Data;

import java.util.List;

public class CurrentTaskBean {


    /**
     * isErr : 0
     * data : [{"damd_id":2,"dam_id":4,"accident_id":1,"manage_id":"5D3BBECBL61AVXUI","address":"测试地址","pics":"test.jpg","message":"test","status":"unread","type":"colockin","longitude":"","latitude":"","review_id":"","create_time":"2019-07-27 16:46:12","update_time":null,"actor_img":null,"actor_sign_img":null},{"damd_id":3,"dam_id":4,"accident_id":1,"manage_id":"5D3BBECBL61AVXUI","address":"测试地址","pics":"test.jpg","message":"test","status":"unread","type":"colockin","longitude":"","latitude":"","review_id":"","create_time":"2019-07-27 16:47:12","update_time":null,"actor_img":null,"actor_sign_img":null},{"damd_id":4,"dam_id":4,"accident_id":1,"manage_id":"5D3BBECBL61AVXUI","address":"测试地址","pics":"test.jpg","message":"test","status":"unread","type":"waitcheck","longitude":"","latitude":"","review_id":"","create_time":"2019-07-27 16:47:33","update_time":null,"actor_img":null,"actor_sign_img":null},{"damd_id":5,"dam_id":4,"accident_id":1,"manage_id":"5D3BBECBL61AVXUI","address":"测试地址","pics":"test.jpg","message":"test","status":"unread","type":"accept","longitude":"","latitude":"","review_id":"","create_time":"2019-07-27 16:52:18","update_time":null,"actor_img":null,"actor_sign_img":null},{"damd_id":6,"dam_id":4,"accident_id":1,"manage_id":"5D3BBECBL61AVXUI","address":"测试地址","pics":"test.jpg","message":"test","status":"unread","type":"waitcheck","longitude":"","latitude":"","review_id":"","create_time":"2019-07-27 16:52:22","update_time":null,"actor_img":null,"actor_sign_img":null},{"damd_id":7,"dam_id":4,"accident_id":1,"manage_id":"5D3BBECBL61AVXUI","address":"测试地址","pics":"test.jpg","message":"test","status":"unread","type":"colockin","longitude":"","latitude":"","review_id":"","create_time":"2019-07-27 16:57:07","update_time":null,"actor_img":null,"actor_sign_img":null},{"damd_id":8,"dam_id":4,"accident_id":1,"manage_id":"5D3BBECBL61AVXUI","address":"测试地址","pics":"test.jpg","message":"test","status":"unread","type":"distribute","longitude":"","latitude":"","review_id":"","create_time":"2019-07-27 17:21:14","update_time":null,"actor_img":null,"actor_sign_img":null},{"damd_id":9,"dam_id":4,"accident_id":1,"manage_id":"5D3BBECBL61AVXUI","address":"测试地址","pics":"test.jpg","message":"test","status":"unread","type":"complete","longitude":"","latitude":"","review_id":"","create_time":"2019-07-27 17:36:09","update_time":null,"actor_img":null,"actor_sign_img":null},{"damd_id":10,"dam_id":4,"accident_id":1,"manage_id":"5D3BBECBL61AVXUI","address":"测试地址","pics":"test.jpg","message":"testinfo","status":"unread","type":"waitcheck","longitude":"","latitude":"","review_id":"","create_time":"2019-07-27 17:37:09","update_time":null,"actor_img":null,"actor_sign_img":null}]
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

    public static class DataBean {
        /**
         * damd_id : 2
         * dam_id : 4
         * accident_id : 1
         * manage_id : 5D3BBECBL61AVXUI
         * address : 测试地址
         * pics : test.jpg
         * message : test
         * status : unread
         * type : colockin
         * longitude :
         * latitude :
         * review_id :
         * create_time : 2019-07-27 16:46:12
         * update_time : null
         * actor_img : null
         * actor_sign_img : null
         */

        private int damd_id;
        private int dam_id;
        private int accident_id;
        private String manage_id;
        private String address;
        private String pics;
        private String message;
        private String status;
        private String type;
        private String longitude;
        private String latitude;
        private String review_id;
        private String create_time;
        private Object update_time;
        private Object actor_img;
        private Object actor_sign_img;

        public int getDamd_id() {
            return damd_id;
        }

        public void setDamd_id(int damd_id) {
            this.damd_id = damd_id;
        }

        public int getDam_id() {
            return dam_id;
        }

        public void setDam_id(int dam_id) {
            this.dam_id = dam_id;
        }

        public int getAccident_id() {
            return accident_id;
        }

        public void setAccident_id(int accident_id) {
            this.accident_id = accident_id;
        }

        public String getManage_id() {
            return manage_id;
        }

        public void setManage_id(String manage_id) {
            this.manage_id = manage_id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPics() {
            return pics;
        }

        public void setPics(String pics) {
            this.pics = pics;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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

        public String getReview_id() {
            return review_id;
        }

        public void setReview_id(String review_id) {
            this.review_id = review_id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public Object getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(Object update_time) {
            this.update_time = update_time;
        }

        public Object getActor_img() {
            return actor_img;
        }

        public void setActor_img(Object actor_img) {
            this.actor_img = actor_img;
        }

        public Object getActor_sign_img() {
            return actor_sign_img;
        }

        public void setActor_sign_img(Object actor_sign_img) {
            this.actor_sign_img = actor_sign_img;
        }
    }
}
