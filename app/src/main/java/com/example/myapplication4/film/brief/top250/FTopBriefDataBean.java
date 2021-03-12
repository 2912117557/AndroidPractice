package com.example.myapplication4.film.brief.top250;

import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.PrimaryKey;

@Fts4
@Entity
public class FTopBriefDataBean {
    @PrimaryKey(autoGenerate = true)
    public int rowid;

    public String id;
    public String title;
    public String original_title;
    public float score;

    public FTopBriefDataBean(int rowid, String id, String title, String original_title, float score) {
        this.rowid = rowid;
        this.id = id;
        this.title = title;
        this.original_title = original_title;
        this.score = score;
    }

   
}
