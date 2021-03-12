package com.example.myapplication4.book.brief;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


@Dao
public interface BookBriefDataDao {
    @Insert
    void insertArray(BookBriefDataBean[] bookBriefDataBeans);

    @Query("SELECT rowid,* FROM BookBriefDataBean WHERE bookSmallType = :bookSmallType")
    DataSource.Factory<Integer, BookBriefDataBean> getAllByType(String bookSmallType);

    @Query("SELECT (EXISTS(SELECT 1 FROM BookBriefDataBean WHERE bookSmallType = :bookSmallType limit 1))")
    boolean isExistDataByType(String bookSmallType);

    @Query("SELECT rowid,* FROM BOOKBRIEFDATABEAN WHERE title LIKE '%' || :query || '%' ")
    List<BookBriefDataBean> search(String query);
}
