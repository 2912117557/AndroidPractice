package com.example.myapplication4.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication4.R;
import com.example.myapplication4.film.brief.hotList.FilmHotListRepository;
import com.example.myapplication4.loginRegister.LoginRegisterBean_Network;
import com.example.myapplication4.myLib.MySingleton;
import com.example.myapplication4.myLib.NetworkConnectStatus;
import com.example.myapplication4.myLib.NetworkConnectTest;
import com.example.myapplication4.myLib.NetworkReceiver;
import com.example.myapplication4.shared.GetRequest_Interface;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginRegisterAcitvity extends AppCompatActivity implements NetworkConnectStatus {

    private TextView titleTextView, errorTextView;
    private EditText editText1, editText2, editText3;
    private Button submitButton,transformButton;
    private boolean isLoginPage;
    private boolean isOnline;
    private Call<LoginRegisterBean_Network> call;
    private Toolbar toolbar;

    @Override
    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        toolbar = findViewById(R.id.actLoginRegister_Toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = Objects.requireNonNull(getSupportActionBar());
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        titleTextView = findViewById(R.id.actLoginRegister_TextView1);
        transformButton = findViewById(R.id.actLoginRegister_Button2);
        editText1 = findViewById(R.id.actLoginRegister_EditText1);
        editText2 = findViewById(R.id.actLoginRegister_EditText2);
        editText3 = findViewById(R.id.actLoginRegister_EditText3);
        submitButton = findViewById(R.id.actLoginRegister_Button1);
        errorTextView = findViewById(R.id.actLoginRegister_TextView3);

        isLoginPage = true;//本页面登录和注册并存，默认显示登录

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorTextView.setText(null);
                submitButton.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                submitButton.setClickable(false);
                NetworkConnectTest.updateNetworkConnectStatus(LoginRegisterAcitvity.this, LoginRegisterAcitvity.this);
                if (isOnline) {
                    String username = editText1.getText().toString();
                    String password = editText2.getText().toString();
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://10.0.2.2:8080/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
                    if (isLoginPage) {
                        //登录操作
                        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                            errorTextView.setText("有字段没有填");
                        } else {
                            call = request.getLoginRegisterResult("login", username, password);
                            call.enqueue(new Callback<LoginRegisterBean_Network>() {
                                @Override
                                public void onResponse(Call<LoginRegisterBean_Network> call, Response<LoginRegisterBean_Network> response) {
                                    if (!response.isSuccessful()) {
                                        errorTextView.setText("登录失败");
                                    } else {
                                        LoginRegisterBean_Network loginRegisterBean_network = response.body();
                                        int code = loginRegisterBean_network.code;
                                        if (code == 0) {
                                            SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            boolean isLogin = true;
                                            editor.putBoolean("isLogin", isLogin);
                                            editor.putString("username", username);
                                            editor.apply();

                                            MySingleton mySingleton = MySingleton.getInstance(getApplicationContext());
                                            MySingleton.UserInfo userInfo = mySingleton.getUserInfo_firstFromSharedPreferences();
                                            userInfo.isLogin = isLogin;
                                            userInfo.username = username;

                                            Intent intent = new Intent();
                                            setResult(RESULT_OK, intent);
                                            finish();
                                        } else {
                                            errorTextView.setText("登录失败");
                                        }
                                    }
                                }
                                @Override
                                public void onFailure(Call<LoginRegisterBean_Network> call, Throwable t) {
                                    errorTextView.setText("登录失败");
                                }
                            });
                        }
                    } else {
                        //注册操作
                        String password_again = editText3.getText().toString();
                        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(password_again)) {
                            errorTextView.setText("有字段没有填");
                        } else if (!password.equals(password_again)) {
                            errorTextView.setText("两次密码不一致");
                        } else {
                            call = request.getLoginRegisterResult("register", username, password);
                            call.enqueue(new Callback<LoginRegisterBean_Network>() {
                                @Override
                                public void onResponse(Call<LoginRegisterBean_Network> call, Response<LoginRegisterBean_Network> response) {
                                    if (!response.isSuccessful()) {
                                        errorTextView.setText("注册失败");
                                    } else {
                                        LoginRegisterBean_Network loginRegisterBean_network = response.body();
                                        int code = loginRegisterBean_network.code;
                                        if (code == 0) {
                                           transformToLogin();
                                           editText1.setText(username);//事先填写好用户名
                                        } else {
                                            errorTextView.setText("注册失败");
                                        }
                                    }
                                }
                                @Override
                                public void onFailure(Call<LoginRegisterBean_Network> call, Throwable t) {
                                    errorTextView.setText("注册失败");
                                }
                            });
                        }
                    }
                } else {
                    errorTextView.setText("没有网络");
                }
                submitButton.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
                submitButton.setClickable(true);
            }
        });

        transformButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLoginPage) {
                    transformToRegister();
                } else {
                    transformToLogin();
                }
            }
        });
    }

    public void transformToLogin(){
        editText1.setText(null);
        editText2.setText(null);
        editText3.setText(null);
        errorTextView.setText(null);
        toolbar.setTitle("登录页面");
        titleTextView.setText("登   录");
        transformButton.setText("注册");
        editText3.setVisibility(View.GONE);
        isLoginPage = true;
    }

    public void transformToRegister(){
        editText1.setText(null);
        editText2.setText(null);
        editText3.setText(null);
        errorTextView.setText(null);
        toolbar.setTitle("注册页面");
        titleTextView.setText("注   册");
        transformButton.setText("登录");
        editText3.setVisibility(View.VISIBLE);
        isLoginPage = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelNetworkRequest();
    }

    public void cancelNetworkRequest() {
        if (call != null) {
            call.cancel();
        }
    }

}
