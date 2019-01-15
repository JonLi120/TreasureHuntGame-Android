package com.unikfunlearn.treasurehuntgame.models.tables;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "act_table")
public class Act {
    @PrimaryKey(autoGenerate = true)
    private int AID;
    private int SCID;
    private String school;
    private String title;
    private String content;

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
}
