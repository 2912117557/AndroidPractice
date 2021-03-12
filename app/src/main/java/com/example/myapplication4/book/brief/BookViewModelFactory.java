package com.example.myapplication4.book.brief;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication4.shared.BriefDataDatabase;

import java.lang.reflect.InvocationTargetException;

public class BookViewModelFactory implements ViewModelProvider.Factory {
    BriefDataDatabase db;
    Context context;
    String bookBigType;
    public BookViewModelFactory(BriefDataDatabase db, Context context,String bookBigType) {
        this.db = db;
        this.context = context;
        this.bookBigType = bookBigType;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        T viewmodel = null;
        try {
            viewmodel = modelClass.getConstructor(BriefDataDatabase.class,Context.class,String.class).newInstance(db,context, bookBigType);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return viewmodel;
    }
}