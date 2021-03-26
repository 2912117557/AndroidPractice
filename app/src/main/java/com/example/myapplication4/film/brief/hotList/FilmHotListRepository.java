package com.example.myapplication4.film.brief.hotList;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication4.myLib.NetworkConnectStatus;
import com.example.myapplication4.shared.BriefDataDatabase;
import com.example.myapplication4.shared.GetRequest_Interface;
import com.example.myapplication4.myLib.NetworkConnectTest;
import com.example.myapplication4.myLib.NetworkReceiver;
import com.example.myapplication4.shared.MyRepository;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FilmHotListRepository implements NetworkConnectStatus, MyRepository {

    String logTag = "FHLRepository";
    private final BriefDataDatabase db;
    private Call<List<FHLBriefDataBean>> call;
    private final Context context;
    private final MutableLiveData<String> errorMessage_LiveData;
    private boolean isOnline;

    @Override
    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    public FilmHotListRepository(BriefDataDatabase db, Context context, MutableLiveData<String> errorMessage_LiveData) {
        this.db = db;
        this.context = context;
        this.errorMessage_LiveData = errorMessage_LiveData;
    }

    public void refreshBriefData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (db.fHLBriefDataDao().isExistData()) {
                    return;
                }
                getDataFromNetwork();
            }
        }).start();
    }

    public void getDataFromNetwork() {
        NetworkConnectTest.updateNetworkConnectStatus(FilmHotListRepository.this, context);
        if (isOnline) {
            errorMessage_LiveData.postValue("加载中...");
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://douban.8610000.xyz/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

            call = request.getFHLBriefData();

            call.enqueue(new Callback<List<FHLBriefDataBean>>() {
                @Override
                public void onResponse(Call<List<FHLBriefDataBean>> call, Response<List<FHLBriefDataBean>> response) {
                    if (!response.isSuccessful()) {
                        try {
                            errorMessage_LiveData.postValue("服务器出错，点击重试");
                            String errorBody = response.errorBody().string();
                            Log.i(logTag, response.code() + " " + errorBody);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        List<FHLBriefDataBean> fHLBriefDataList = response.body();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                db.fHLBriefDataDao().insertArray(fHLBriefDataList.toArray(new FHLBriefDataBean[fHLBriefDataList.size()]));
                                errorMessage_LiveData.postValue(null);
                            }
                        }).start();
                    }
                }

                @Override
                public void onFailure(Call<List<FHLBriefDataBean>> call, Throwable throwable) {
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
