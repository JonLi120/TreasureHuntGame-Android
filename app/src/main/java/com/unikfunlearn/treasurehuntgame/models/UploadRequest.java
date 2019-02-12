package com.unikfunlearn.treasurehuntgame.models;


import java.util.List;

public class UploadRequest {

    private int SCID;
    private int RID;
    private int AID;
    private int scores;
    private String mClass;
    private String seatNumber;
    private String name;
    private String explain;
    private List<AnswerBean> answer;

    public int getScores() {
        return scores;
    }

    public void setScores(int scores) {
        this.scores = scores;
    }

    public String getMClass() {
        return mClass;
    }

    public void setMClass(String mClass) {
        this.mClass = mClass;
    }

    public int getSCID() {
        return SCID;
    }

    public void setSCID(int SCID) {
        this.SCID = SCID;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public int getRID() {
        return RID;
    }

    public void setRID(int RID) {
        this.RID = RID;
    }

    public int getAID() {
        return AID;
    }

    public void setAID(int AID) {
        this.AID = AID;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AnswerBean> getAnswer() {
        return answer;
    }

    public void setAnswer(List<AnswerBean> answer) {
        this.answer = answer;
    }

    public static class AnswerBean {

        private String title;
        private String answer;
        private String img;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}
