package com.example.myapplication4.book.brief;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;

import com.example.myapplication4.shared.BriefDataDatabase;
import com.example.myapplication4.shared.MyViewModel;

public class BookViewModel extends MyViewModel {
    private LiveData<PagedList<BookBriefDataBean>> bookBriefData_PagedList_LiveData;
    private final BookRepository bookRepository;
    private final MutableLiveData<String> errorMessage_LiveData = new MutableLiveData<>(null);

    private final PagedList.Config mPagingConfig = new PagedList.Config.Builder()
            .setPageSize(21)
            .setPrefetchDistance(63)
            .setEnablePlaceholders(false)
            .build();

    public BookViewModel(BriefDataDatabase db, Context context,String bookBigType) {
        bookRepository = new BookRepository(db,context, errorMessage_LiveData,bookBigType);
    }

    public LiveData<PagedList<BookBriefDataBean>> getBookBriefData_PagedList_LiveData() {
        if (bookBriefData_PagedList_LiveData != null) {
            return bookBriefData_PagedList_LiveData;
        } else {
            bookBriefData_PagedList_LiveData = bookRepository.refreshBook();
            return bookBriefData_PagedList_LiveData;
        }
    }

    public BookRepository getBookRepository(){
        return bookRepository;
    }

    public MutableLiveData<String> getErrorMessage_LiveData() {
        return errorMessage_LiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        bookRepository.cancelNetworkRequest();
    }
}
