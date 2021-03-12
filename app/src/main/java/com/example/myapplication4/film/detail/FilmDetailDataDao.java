package com.example.myapplication4.film.detail;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface FilmDetailDataDao {
    @Transaction
    @Query("SELECT * FROM FilmDetailDataBean_Basic WHERE id = :id")
    public LiveData<FilmDetailDataBean> getFilmDetailData_LiveData(String id);

    @Insert
    void insertOne_basic(FilmDetailDataBean_Basic filmDetailDataBean_basic);

    @Insert
    void insertList_actor(List<FilmDetailDataBean_Actor> filmDetailDataBean_actor_list);

    @Query("SELECT (EXISTS(SELECT 1 FROM FilmDetailDataBean_Basic WHERE id = :id limit 1))")
    boolean isExistData(String id);
}
