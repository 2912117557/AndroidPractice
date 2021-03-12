package com.example.myapplication4.film.detail;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication4.shared.DetailDataDatabase;
import com.example.myapplication4.shared.MyViewModel;

public class FilmViewModel extends MyViewModel {
    private LiveData<FilmDetailDataBean> filmDetailDataBean_LiveData;
    private final FilmRepository filmRepository;
    private final MutableLiveData<String> errorMessage_LiveData = new MutableLiveData<>(null);
    private final DetailDataDatabase db;
    private final String filmId;

    public FilmViewModel(DetailDataDatabase db, Context context, String filmId) {
        this.db = db;
        this.filmId = filmId;
        filmRepository = new FilmRepository(db,context, errorMessage_LiveData,filmId);
    }

    public LiveData<FilmDetailDataBean> getfilmDetailDataBean_LiveData() {
        if (filmDetailDataBean_LiveData != null) {
            return filmDetailDataBean_LiveData;
        } else {
            filmRepository.refreshDetailData();
            filmDetailDataBean_LiveData = db.filmDetailDataDao().getFilmDetailData_LiveData(filmId);
            return filmDetailDataBean_LiveData;
        }
    }

    public FilmRepository getFilmRepository() {
        return filmRepository;
    }

    public MutableLiveData<String> getErrorMessage_LiveData() {
        return errorMessage_LiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        filmRepository.cancelNetworkRequest();
    }
}
