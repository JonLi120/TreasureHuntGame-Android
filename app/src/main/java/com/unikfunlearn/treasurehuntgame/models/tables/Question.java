package com.unikfunlearn.treasurehuntgame.models.tables;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "question_table", foreignKeys = @ForeignKey(entity = Act.class, parentColumns = "AID", childColumns = "AID", onUpdate = CASCADE, onDelete = CASCADE),
indices = @Index("AID"))
public class Question {
    @PrimaryKey(autoGenerate = true)
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

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }
}
