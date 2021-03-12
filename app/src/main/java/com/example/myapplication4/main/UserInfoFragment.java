package com.example.myapplication4.main;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.myapplication4.R;
import com.example.myapplication4.myLib.MySingleton;
import com.google.android.material.navigation.NavigationView;

import static android.content.Context.MODE_PRIVATE;


public class UserInfoFragment extends Fragment {
    private final String logTag = "UserInfoFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button logOutButton = view.findViewById(R.id.fragUserInfo_Button1);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("userInfo", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLogin", false);
                editor.apply();

                MySingleton mySingleton = MySingleton.getInstance(requireActivity().getApplicationContext());
                MySingleton.UserInfo userInfo = mySingleton.getUserInfo_firstFromSharedPreferences();
                userInfo.isLogin = false;

                NavigationView navView = requireActivity().findViewById(R.id.actMain_NavigationView);
                View headerView = navView.getHeaderView(0);
                TextView nameTextView = headerView.findViewById(R.id.actMain_drawerlayout_header_TextView1);
                nameTextView.setText("  登录/注册");
                nameTextView.setClickable(false);

                NavController navController = Navigation.findNavController(view);
                navController.popBackStack();

            }
        });
    }

}