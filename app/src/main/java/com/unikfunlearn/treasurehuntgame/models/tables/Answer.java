package com.unikfunlearn.treasurehuntgame.models.tables;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "answer_table", foreignKeys = @ForeignKey(entity = Record.class, parentColumns = "RID", childColumns = "rid", onDelete = ForeignKey.CASCADE),
indices = @Index("rid"))
public class Answer{
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int rid;
    private String title;
    private String answer;
    private String img;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

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
