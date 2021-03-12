package com.example.myapplication4.film.detail;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Relation;

import java.util.List;

public class FilmDetailDataBean {
    @Embedded
    public FilmDetailDataBean_Basic filmDetailDataBean_basic;

    @Relation(
            parentColumn = "id",
            entityColumn = "filmDetailDataBean_Basic_id"
    )
    public List<FilmDetailDataBean_Actor> filmDetailDataBean_actor_list;

    public FilmDetailDataBean(FilmDetailDataBean_Basic filmDetailDataBean_basic,
                              List<FilmDetailDataBean_Actor> filmDetailDataBean_actor_list) {
        this.filmDetailDataBean_basic = filmDetailDataBean_basic;
        this.filmDetailDataBean_actor_list = filmDetailDataBean_actor_list;
    }


}

