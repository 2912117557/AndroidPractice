package com.example.myapplication4.film.brief.hotList;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.myapplication4.shared.BriefDataDatabase;
import com.example.myapplication4.shared.MyViewModel;

public class FilmHotListViewModel extends MyViewModel {
    private LiveData<PagedList<FHLBriefDataBean>> fHLBriefData_PagedList_LiveData;
    private final FilmHotListRepository filmHotListRepository;
    private final MutableLiveData<String> errorMessage_LiveData = new MutableLiveData<>(null);
    private final BriefDataDatabase db;

    private final PagedList.Config mPagingConfig = new PagedList.Config.Builder()
            .setPageSize(21)
            .setPrefetchDistance(63)
            .setEnablePlaceholders(false)
            .build();

    public FilmHotListViewModel(BriefDataDatabase db, Context context) {
        this.db = db;
        filmHotListRepository = new FilmHotListRepository(db,context, errorMessage_LiveData);
    }

    public LiveData<PagedList<FHLBriefDataBean>> getFHLBriefData_PagedList_LiveData() {
        if (fHLBriefData_PagedList_LiveData != null) {
            return fHLBriefData_PagedList_LiveData;
        } else {
            filmHotListRepository.refreshBriefData();
            fHLBriefData_PagedList_LiveData = new LivePagedListBuilder<>(
                    db.fHLBriefDataDao().getAll(), mPagingConfig).build();
            return fHLBriefData_PagedList_LiveData;
        }
    }

    public FilmHotListRepository getFilmHotListRepository() {
        return filmHotListRepository;
    }

    public MutableLiveData<String> getErrorMessage_LiveData() {
        return errorMessage_LiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        filmHotListRepository.cancelNetworkRequest();
    }
}
