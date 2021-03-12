package com.example.myapplication4.film.detail;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.PrimaryKey;

@Entity
public class FilmDetailDataBean_Basic {
    @PrimaryKey
    @NonNull
    public String id;
    public String title;
    public String original_title;
    public boolean is_tv;
    public String pic_normal;
    public String year;
    public String countries;
    public String languages;
    public String genres;
    public int rating_count;
    public float rating_value;
    public String intro;

    public FilmDetailDataBean_Basic(@NonNull String id, String title, String original_title,
                                    boolean is_tv, String pic_normal, String year, String countries,
                                    String languages, String genres, int rating_count, float rating_value,
                                    String intro) {
        this.id = id;
        this.title = title;
        this.original_title = original_title;
        this.is_tv = is_tv;
        this.pic_normal = pic_normal;
        this.year = year;
        this.countries = countries;
        this.languages = languages;
        this.genres = genres;
        this.rating_count = rating_count;
        this.rating_value = rating_value;
        this.intro = intro;
    }
}
