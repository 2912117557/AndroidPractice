package com.example.myapplication4.shared;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.myapplication4.film.detail.FilmDetailDataBean;
import com.example.myapplication4.film.detail.FilmDetailDataBean_Actor;
import com.example.myapplication4.film.detail.FilmDetailDataBean_Basic;
import com.example.myapplication4.film.detail.FilmDetailDataDao;

@Database(entities = {FilmDetailDataBean_Basic.class, FilmDetailDataBean_Actor.class}, version = 1,exportSchema = false)
public abstract class DetailDataDatabase extends RoomDatabase {
    public abstract FilmDetailDataDao filmDetailDataDao();
}
