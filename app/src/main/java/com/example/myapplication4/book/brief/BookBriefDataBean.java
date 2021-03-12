package com.example.myapplication4.book.brief;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Fts4
@Entity
public class BookBriefDataBean {
    @PrimaryKey(autoGenerate = true)
    public int rowid;
    public String id;
    public String title;
    public String image;
    @Embedded
    public BookScore rating;
    public String bookSmallType;

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
        return  title + " " + rating.toString() + " " + image;
    }

    public static class BookScore{
        @Ignore
        public int max;
        @Ignore
        public int numRaters;
        public String average;
        @Ignore
        public int min;

        @NonNull
        @Override
        public String toString() {
            return average;
        }
    }


}




