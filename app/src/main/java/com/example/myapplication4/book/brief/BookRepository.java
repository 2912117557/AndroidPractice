package com.example.myapplication4.book.brief;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.myapplication4.film.brief.hotList.FilmHotListRepository;
import com.example.myapplication4.myLib.NetworkConnectStatus;
import com.example.myapplication4.myLib.NetworkConnectTest;
import com.example.myapplication4.myLib.NetworkReceiver;
import com.example.myapplication4.shared.BookType;
import com.example.myapplication4.shared.BriefDataDatabase;
import com.example.myapplication4.shared.GetRequest_Interface;
import com.example.myapplication4.shared.MyRepository;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BookRepository implements NetworkConnectStatus, MyRepository {
    String logTag = "BookRepository";

    private final BriefDataDatabase db;
    private Call<BookBriefDataBean_Network> call;
    private final Context context;
    private final MutableLiveData<String> errorMessage_LiveData;
    private String[] bookSmallTypeArray;
    private final String bookBigType;
    private String bookSmallType;
    private boolean isOnline;

    @Override
    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    private final PagedList.Config mPagingConfig = new PagedList.Config.Builder()
            .setPageSize(21)
            .setPrefetchDistance(63)
            .setEnablePlaceholders(false)
            .build();

    public BookRepository(BriefDataDatabase db, Context context, MutableLiveData<String> errorMessage_LiveData, String bookBigType) {
        this.db = db;
        this.context = context;
        this.errorMessage_LiveData = errorMessage_LiveData;
        this.bookBigType = bookBigType;
    }

    public LiveData<PagedList<BookBriefDataBean>> refreshBook() {
        if (bookSmallTypeArray == null) {
            bookSmallTypeArray = BookType.getBookSmallTypeArray(bookBigType);
        }
        bookSmallType = BookType.getRandomBookSmallType(bookSmallTypeArray);
        refreshBriefData();
        return new LivePagedListBuilder<>(
                db.bookBriefDataDao().getAllByType(bookSmallType), mPagingConfig).build();
    }

    public void refreshBriefData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean isExist = db.bookBriefDataDao().isExistDataByType(bookSmallType);
                if (!isExist) {
                    getDataFromNetwork();
                }
            }
        }).start();
    }

    public void getDataFromNetwork() {
        NetworkConnectTest.updateNetworkConnectStatus(BookRepository.this, context);
        if (isOnline) {
            errorMessage_LiveData.postValue("加载中...");
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.douban.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

            call = request.getBookBriefData(bookSmallType);

            call.enqueue(new Callback<BookBriefDataBean_Network>() {
                @Override
                public void onResponse(Call<BookBriefDataBean_Network> call, Response<BookBriefDataBean_Network> response) {
                    if (!response.isSuccessful()) {
                        try {
                            errorMessage_LiveData.postValue("服务器出错，点击重试");
                            String errorBody = response.errorBody().string();
                            Log.i(logTag, response.code() + " " + errorBody);
                            Log.i(logTag, bookSmallType);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        BookBriefDataBean_Network bookBriefDataBean_Network = response.body();
                        List<BookBriefDataBean> bookBriefDataList = bookBriefDataBean_Network.books;
                        int size = bookBriefDataList.size();
                        BookBriefDataBean[] bookBriefDataArray = new BookBriefDataBean[size];
                        BookBriefDataBean bookBriefDataBean;
                        for (int i = 0; i < size; i++) {
                            bookBriefDataBean = bookBriefDataList.get(i);
                            bookBriefDataBean.bookSmallType = bookSmallType;
                            bookBriefDataArray[i] = bookBriefDataBean;
                        }
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                db.bookBriefDataDao().insertArray(bookBriefDataArray);
                                errorMessage_LiveData.postValue(null);
                            }
                        }).start();
                    }
                }

                @Override
                public void onFailure(Call<BookBriefDataBean_Network> call, Throwable throwable) {
                    Log.i(logTag, throwable.getMessage());
                    errorMessage_LiveData.postValue("加载出错，点击重试");
                }
            });
        } else {
            errorMessage_LiveData.postValue("没有网络，点击刷新");
        }
    }


    public void cancelNetworkRequest() {
        if (call != null) {
            call.cancel();
        }
    }
}
