package com.unikfunlearn.treasurehuntgame.models;

import java.util.List;

public class SchoolResponse {

    private int code;
    private List<SchoolBean> school;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<SchoolBean> getSchool() {
        return school;
    }

    public void setSchool(List<SchoolBean> school) {
        this.school = school;
    }

    public static class SchoolBean {

        private int SCID;
        private String name;

        public int getSCID() {
            return SCID;
        }

        public void setSCID(int SCID) {
            this.SCID = SCID;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
