package com.example.myapplication4.film.detail;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication4.film.brief.hotList.FilmHotListRepository;
import com.example.myapplication4.myLib.NetworkConnectStatus;
import com.example.myapplication4.myLib.NetworkConnectTest;
import com.example.myapplication4.myLib.NetworkReceiver;
import com.example.myapplication4.shared.DetailDataDatabase;
import com.example.myapplication4.shared.GetRequest_Interface;
import com.example.myapplication4.shared.MyRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FilmRepository implements NetworkConnectStatus, MyRepository {
    String logTag = "FilmDetailRepository";
    private final DetailDataDatabase db;
    private Call<FilmDetailDataBean_Network> call;
    private final Context context;
    private final MutableLiveData<String> errorMessage_LiveData;
    private final String filmId;
    private boolean isOnline;


    @Override
    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    public FilmRepository(DetailDataDatabase db, Context context, MutableLiveData<String> errorMessage_LiveData, String filmId) {
        this.db = db;
        this.context = context;
        this.errorMessage_LiveData = errorMessage_LiveData;
        this.filmId = filmId;
    }

    public void refreshDetailData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (db.filmDetailDataDao().isExistData(filmId)) {
                    return;
                }
                getDataFromNetwork();
            }
        }).start();
    }


    public void getDataFromNetwork() {
        NetworkConnectTest.updateNetworkConnectStatus(FilmRepository.this, context);
        if (isOnline) {
            errorMessage_LiveData.postValue("加载中...");
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://douban.8610000.xyz/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

            call = request.getFilmDetailData(filmId);

            call.enqueue(new Callback<FilmDetailDataBean_Network>() {
                @Override
                public void onResponse(Call<FilmDetailDataBean_Network> call, Response<FilmDetailDataBean_Network> response) {
                    if (!response.isSuccessful()) {
                        try {
                            errorMessage_LiveData.postValue("服务器出错，点击重试");
                            String errorBody = response.errorBody().string();
                            Log.i(logTag, response.code() + " " + errorBody);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        FilmDetailDataBean_Network bean = response.body();
                        String id = bean.id;
                        FilmDetailDataBean_Basic filmDetailDataBean_basic = new FilmDetailDataBean_Basic(id, bean.title,
                                bean.original_title, bean.is_tv, bean.pic.normal, bean.year, joinStringList(bean.countries),
                                joinStringList(bean.languages), joinStringList(bean.genres), bean.rating.count, bean.rating.value,
                                bean.intro);
                        List<FilmDetailDataBean_Network.FilmDirectorInfo> directors = bean.directors;
                        List<FilmDetailDataBean_Network.FilmActorInfo> actors = bean.actors;
                        List<FilmDetailDataBean_Actor> filmDetailDataBean_actor_list = new ArrayList<>();

                        // 一定要导演在前面
                        for (int i = 0; i < directors.size(); i++) {
                            FilmDetailDataBean_Network.FilmDirectorInfo director = directors.get(i);
                            filmDetailDataBean_actor_list.add(new FilmDetailDataBean_Actor(director.name,
                                    joinStringList(director.roles), director.url, director.cover_url, id));
                        }
                        for (int i = 0; i < actors.size(); i++) {
                            FilmDetailDataBean_Network.FilmActorInfo actor = actors.get(i);
                            filmDetailDataBean_actor_list.add(new FilmDetailDataBean_Actor(actor.name,
                                    joinStringList(actor.roles), actor.url, actor.cover_url, id));
                        }


                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                db.filmDetailDataDao().insertOne_basic(filmDetailDataBean_basic);
                                db.filmDetailDataDao().insertList_actor(filmDetailDataBean_actor_list);
                                errorMessage_LiveData.postValue(null);
                            }
                        }).start();

                    }
                }

                @Override
                public void onFailure(Call<FilmDetailDataBean_Network> call, Throwable throwable) {
                    Log.i(logTag, throwable.getMessage());
                    errorMessage_LiveData.postValue("加载出错，点击重试");
                }
            });
        } else {
            errorMessage_LiveData.postValue("没有网络，点击刷新");
        }
    }

    public String joinStringList(List<String> list) {
        StringBuilder string = new StringBuilder();
        int size = list.size();
        int sizeMinus = size - 1;
        for (int i = 0; i < size; i++) {
            string.append(list.get(i));
            if (i != sizeMinus) {
                string.append("/");
            }
        }
        return string.toString();
    }

    public void cancelNetworkRequest() {
        if (call != null) {
            call.cancel();
        }
    }
}
