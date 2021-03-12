package com.example.myapplication4.film.brief.hotList;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myapplication4.search.SearchBean;

import java.util.List;

@Dao
public interface FHLBriefDataDao {
    @Insert
    void insertArray(FHLBriefDataBean[] FHLBriefDataBeans);

    @Query("SELECT rowid, * FROM FHLBriefDataBean")
    DataSource.Factory<Integer, FHLBriefDataBean> getAll();

    @Query("SELECT (EXISTS(SELECT 1 FROM FHLBriefDataBean limit 1))")
    boolean isExistData();

    @Query("SELECT rowid,* FROM FHLBriefDataBean WHERE title LIKE '%' || :query || '%'")
    List<FHLBriefDataBean> search(String query);

}
