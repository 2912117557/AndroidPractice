package com.example.myapplication4.shared;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.myapplication4.book.brief.BookBriefDataBean;
import com.example.myapplication4.book.brief.BookBriefDataDao;
import com.example.myapplication4.film.brief.hotList.FHLBriefDataBean;
import com.example.myapplication4.film.brief.hotList.FHLBriefDataDao;
import com.example.myapplication4.film.brief.top250.FTopBriefDataBean;
import com.example.myapplication4.film.brief.top250.FTopBriefDataDao;

@Database(entities = {FHLBriefDataBean.class, FTopBriefDataBean.class, BookBriefDataBean.class}, version = 1,exportSchema = false)
public abstract class BriefDataDatabase extends RoomDatabase {
    public abstract FHLBriefDataDao fHLBriefDataDao();
    public abstract FTopBriefDataDao fTopBriefDataDao();
    public abstract BookBriefDataDao bookBriefDataDao();
}