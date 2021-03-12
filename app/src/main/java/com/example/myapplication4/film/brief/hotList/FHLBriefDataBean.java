package com.example.myapplication4.film.brief.hotList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Fts4
@Entity
public class FHLBriefDataBean {
    @PrimaryKey(autoGenerate = true)
    public int rowid;
    public String id;
    public String title;
    @Ignore
    public String original_title;
    @Ignore
    public boolean is_tv;
    public String year;

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj != null) {
            return this.toString().equals(obj.toString());
        } else {
            return false;
        }
    }

    @NonNull
    @Override
    public String toString() {
        return title + " " + year;
    }
}
