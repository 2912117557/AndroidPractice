package com.example.myapplication4.film.brief.hotList;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication4.shared.BriefDataDatabase;

import java.lang.reflect.InvocationTargetException;

public class FilmHotListViewModelFactory implements ViewModelProvider.Factory {
    BriefDataDatabase db;
    Context context;
    public FilmHotListViewModelFactory(BriefDataDatabase db, Context context) {
        this.db = db;
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        T viewmodel = null;
        try {
            viewmodel = modelClass.getConstructor(BriefDataDatabase.class,Context.class).newInstance(db,context);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return viewmodel;
    }
}