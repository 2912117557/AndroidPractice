package com.example.myapplication4.myLib;

import android.content.pm.PackageManager;
import android.util.Log;

import androidx.activity.ComponentActivity;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;

public class MyRequestPermission {

    private String permission;
    private ComponentActivity componentActivity;
    private GrantedCallback callback;
    private ActivityResultLauncher<String> requestPermissionLauncher;


    public MyRequestPermission(String permission, ComponentActivity componentActivity, GrantedCallback callback) {
        this.permission = permission;
        this.componentActivity = componentActivity;
        this.callback = callback;
    }

    public void register() {
        requestPermissionLauncher = componentActivity.registerForActivityResult(new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        callback.grantedProcess();
                    } else {
                        Log.i("permission", "No Access");
                    }
                });
    }

    public void beginRequest() {
        if (ContextCompat.checkSelfPermission(componentActivity, permission) == PackageManager.PERMISSION_GRANTED) {
            callback.grantedProcess();
        } else if (componentActivity.shouldShowRequestPermissionRationale(permission)) {
            requestPermissionLauncher.launch(permission);
        } else {
            requestPermissionLauncher.launch(permission);
        }
    }

    public interface GrantedCallback {
        void grantedProcess();
    }

}
