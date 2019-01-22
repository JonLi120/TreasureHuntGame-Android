package com.unikfunlearn.treasurehuntgame.models;

import java.util.List;

public class DownloadResponse {

    private String code;
    private List<ActivityBean> activity;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<ActivityBean> getActivity() {
        return activity;
    }

    public void setActivity(List<ActivityBean> activity) {
        this.activity = activity;
    }

    public static class ActivityBean {

        private int AID;
        private int SCID;
        private String school;
        private String title;
        private String content;
        private String note;
        private List<QuestionBean> question;

        public int getAID() {
            return AID;
        }

        public void setAID(int AID) {
            this.AID = AID;
        }

        public int getSCID() {
            return SCID;
        }

        public void setSCID(int SCID) {
            this.SCID = SCID;
        }

        public String getSchool() {
            return school;
        }

        public void setSchool(String school) {
            this.school = school;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public List<QuestionBean> getQuestion() {
            return question;
        }

        public void setQuestion(List<QuestionBean> question) {
            this.question = question;
        }

        public static class QuestionBean {

            private int QID;
            private int AID;
            private String bluesn;
            private String title;
            private String question;
            private String hit;
            private int atype;
            private String choose;
            private int answer;
            private int fraction;
            private int sort;
            private boolean skip;
            private int distance;

            public int getQID() {
                return QID;
            }

            public void setQID(int QID) {
                this.QID = QID;
            }

            public int getAID() {
                return AID;
            }

            public void setAID(int AID) {
                this.AID = AID;
            }

            public String getBluesn() {
                return bluesn;
            }

            public void setBluesn(String bluesn) {
                this.bluesn = bluesn;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getQuestion() {
                return question;
            }

            public void setQuestion(String question) {
                this.question = question;
            }

            public String getHit() {
                return hit;
            }

            public void setHit(String hit) {
                this.hit = hit;
            }

            public int getAtype() {
                return atype;
            }

            public void setAtype(int atype) {
                this.atype = atype;
            }

            public String getChoose() {
                return choose;
            }

            public void setChoose(String choose) {
                this.choose = choose;
            }

            public int getAnswer() {
                return answer;
            }

            public void setAnswer(int answer) {
                this.answer = answer;
            }

            public int getFraction() {
                return fraction;
            }

            public void setFraction(int fraction) {
                this.fraction = fraction;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public boolean getSkip() {
                return skip;
            }

            public void setSkip(boolean skip) {
                this.skip = skip;
            }

            public int getDistance() {
                return distance;
            }

            public void setDistance(int distance) {
                this.distance = distance;
            }
        }
    }
}
