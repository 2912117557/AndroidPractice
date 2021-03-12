package com.example.myapplication4.film.detail;

import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.PrimaryKey;

@Fts4
@Entity
public class FilmDetailDataBean_Actor {
    @PrimaryKey(autoGenerate = true)
    public int rowid;

    public String actor_name;
    public String actor_roles;
    public String actor_url;
    public String actor_coverUrl;

    public String filmDetailDataBean_Basic_id; // 外键

    public FilmDetailDataBean_Actor(String actor_name, String actor_roles, String actor_url, String actor_coverUrl,
                                    String filmDetailDataBean_Basic_id) {
        this.actor_name = actor_name;
        this.actor_roles = actor_roles;
        this.actor_url = actor_url;
        this.actor_coverUrl = actor_coverUrl;
        this.filmDetailDataBean_Basic_id = filmDetailDataBean_Basic_id;
    }
}
