package com.example.myapplication4.shared;

import android.content.Intent;
import android.content.SharedPreferences;

import androidx.activity.ComponentActivity;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.example.myapplication4.main.LoginRegisterAcitvity;
import com.example.myapplication4.myLib.MySingleton;

import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class LoginInterceptor {

    public static void intercept(ComponentActivity componentActivity,LoginInterceptSuccess loginInterceptSuccess) {
        MySingleton mySingleton = MySingleton.getInstance(componentActivity.getApplicationContext());
        MySingleton.UserInfo userInfo = mySingleton.getUserInfo_firstFromSharedPreferences();
        boolean isLogin = userInfo.isLogin;
        if (isLogin) {
            loginInterceptSuccess.originalOperation();
        } else {
            ActivityResultLauncher<Intent> loginRegisterLanucher = componentActivity.registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK) {
                            loginInterceptSuccess.originalOperation();
                        }
                    });
            Intent intent = new Intent(componentActivity, LoginRegisterAcitvity.class);
            loginRegisterLanucher.launch(intent);
        }
    }

    //在原本的类中，新建内部类，让内部类实现这个类。或者新建类实现这个类。这样可以在一个类中有多个登录拦截事件
    //类中为登录拦截，成功登录后，本来要做的事
    public interface LoginInterceptSuccess{
        void originalOperation();
    }
}
