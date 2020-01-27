package com.motivation.first.myapplication.Model;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note")
public class Utils {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id")
    private long id;

    private String title;
    private String description;

    public Utils(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
