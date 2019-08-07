package com.frj.zhihuizhongxing.Data;

public class UserBean {

    /**
     * isErr : 0
     * data : {"company":"赣州测试公司","name":"欧阳涛","business":"","email":"","address":"","phone":"","police_no":"","face":""}
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
         * company : 赣州测试公司
         * name : 欧阳涛
         * business :
         * email :
         * address :
         * phone :
         * police_no :
         * face :
         */

        private String company;
        private String name;
        private String business;
        private String email;
        private String address;
        private String phone;
        private String police_no;
        private String face;

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBusiness() {
            return business;
        }

        public void setBusiness(String business) {
            this.business = business;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPolice_no() {
            return police_no;
        }

        public void setPolice_no(String police_no) {
            this.police_no = police_no;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }
    }
}
