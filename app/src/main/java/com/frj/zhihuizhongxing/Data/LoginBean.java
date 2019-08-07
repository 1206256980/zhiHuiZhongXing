package com.frj.zhihuizhongxing.Data;

import java.util.List;

public class LoginBean {


    /**
     * isErr : 0
     * data : {"rbac":[],"PHPSESSID":"bpc8dqh27a0u1pommk2qkivar2","info":{"company_id":"5CF08D20U190FYQP","face":"","name":"欧阳涛"}}
     * msg :
     */

    private int isErr;
    private DataBean data;
    private String msg;
    private String code;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static class DataBean {
        /**
         * rbac : []
         * PHPSESSID : bpc8dqh27a0u1pommk2qkivar2
         * info : {"company_id":"5CF08D20U190FYQP","face":"","name":"欧阳涛"}
         */

        private String PHPSESSID;
        private InfoBean info;
        private List<?> rbac;

        public String getPHPSESSID() {
            return PHPSESSID;
        }

        public void setPHPSESSID(String PHPSESSID) {
            this.PHPSESSID = PHPSESSID;
        }

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

        public List<?> getRbac() {
            return rbac;
        }

        public void setRbac(List<?> rbac) {
            this.rbac = rbac;
        }

        public static class InfoBean {
            /**
             * company_id : 5CF08D20U190FYQP
             * face :
             * name : 欧阳涛
             */

            private String company_id;
            private String face;
            private String name;

            public String getCompany_id() {
                return company_id;
            }

            public void setCompany_id(String company_id) {
                this.company_id = company_id;
            }

            public String getFace() {
                return face;
            }

            public void setFace(String face) {
                this.face = face;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
