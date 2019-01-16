package com.unikfunlearn.treasurehuntgame.models.tables;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "school_table")
public class School {
    @PrimaryKey(autoGenerate = true)
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
