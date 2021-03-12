package com.example.myapplication4.myLib;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import static android.content.Context.MODE_PRIVATE;

public class MySingleton {
    private static MySingleton instance;
    private final Context context;//只能为applicaionContext
    private UserInfo userInfo;


    private MySingleton(Context context) {
        this.context = context;
    }

    public static synchronized MySingleton getInstance(Context context) {
        if (instance == null) {
            instance = new MySingleton(context);
        }
        return instance;
    }

    public UserInfo getUserInfo_firstFromSharedPreferences(){
        if(userInfo == null){
            userInfo = new UserInfo();
            SharedPreferences sharedPreferences=context.getSharedPreferences("userInfo",MODE_PRIVATE);
            userInfo.isLogin = sharedPreferences.getBoolean("isLogin",false);
            userInfo.username = sharedPreferences.getString("username",null);
        }
        return userInfo;
    }

    public static class UserInfo{
        public boolean isLogin;
        public String username;
    }

}

