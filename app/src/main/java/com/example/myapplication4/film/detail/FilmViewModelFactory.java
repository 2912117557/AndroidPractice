package com.example.myapplication4.film.detail;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication4.shared.BriefDataDatabase;
import com.example.myapplication4.shared.DetailDataDatabase;

import java.lang.reflect.InvocationTargetException;

public class FilmViewModelFactory implements ViewModelProvider.Factory{
    DetailDataDatabase db;
    Context context;
    String filmId;
    public FilmViewModelFactory(DetailDataDatabase db, Context context, String filmId) {
        this.db = db;
        this.context = context;
        this.filmId = filmId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        T viewmodel = null;
        try {
            viewmodel = modelClass.getConstructor(DetailDataDatabase.class,Context.class,String.class).newInstance(db,context,filmId);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return viewmodel;
    }
}
