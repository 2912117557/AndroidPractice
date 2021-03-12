package com.example.myapplication4.myLib;

import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.example.myapplication4.shared.MyRepository;
import com.example.myapplication4.shared.MyViewModel;

public class NetworkErrorHandle {

    public static void handleError(TextView textView_error, LifecycleOwner lifecycleOwner, MyViewModel myViewModel, MyRepository myRepository) {
        myViewModel.getErrorMessage_LiveData().observe(lifecycleOwner, new Observer<String>() {
            private TextView errorTextView;

            @Override
            public void onChanged(String string) {
                if (errorTextView == null) {
                    errorTextView = textView_error;
                    errorTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            myRepository.getDataFromNetwork();
                        }
                    });
                }
                if (string != null) {
                    errorTextView.setText(string);
                    errorTextView.setVisibility(View.VISIBLE);
                    if (string.equals("加载中...")) {
                        errorTextView.setClickable(false);
                    } else {
                        errorTextView.setClickable(true);
                    }
                } else {
                    errorTextView.setVisibility(View.GONE);
                }
            }
        });
    }
}
