package com.example.myapplication4.shared;

import com.example.myapplication4.book.brief.BookBriefDataBean_Network;
import com.example.myapplication4.film.detail.FilmDetailDataBean_Network;
import com.example.myapplication4.film.brief.hotList.FHLBriefDataBean;
import com.example.myapplication4.loginRegister.LoginRegisterBean_Network;
//import com.example.myapplication4.film.filmDetail.FHLDetailDataBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface GetRequest_Interface {

    // 电影，大量简略信息，https://douban.8610000.xyz/
    @GET("q.json")
    Call<List<FHLBriefDataBean>> getFHLBriefData();

    //图书，对应tag的大量简略信息，https://api.douban.com/
    @GET("v2/book/search?count=100&fields=id,title,rating,image&apiKey=054022eaeae0b00e0fc068c0c0a2102a")
    Call<BookBriefDataBean_Network> getBookBriefData(@Query("tag") String tag);

    //电影，具体id对应的详细信息，https://douban.8610000.xyz/
    @GET("data/{id}.json")
    Call<FilmDetailDataBean_Network> getFilmDetailData(@Path("id") String id);

    //登录，http://10.0.2.2:8080/ ,
    //http://10.0.2.2:8080/login ,http://10.0.2.2:8080/register
    @FormUrlEncoded
    @POST("{loginOrRegister}")
    Call<LoginRegisterBean_Network> getLoginRegisterResult(@Path("loginOrRegister") String loginOrRegister,
                                                           @Field("username") String username, @Field("password") String password);


}
