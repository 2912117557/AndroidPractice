package com.example.myapplication4.main;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.SearchRecentSuggestions;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.myapplication4.R;
import com.example.myapplication4.book.brief.BookBriefDataBean;
import com.example.myapplication4.film.brief.hotList.FHLBriefDataBean;
import com.example.myapplication4.search.MySearchRecentSuggestionsProvider;
import com.example.myapplication4.search.SearchBean;
import com.example.myapplication4.search.SearchRecyclerViewAdapter;
import com.example.myapplication4.shared.BriefDataDatabase;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SearchActivity extends AppCompatActivity {
    private final String logTag = "SearchActivity";
    private NavController navController;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = findViewById(R.id.actSearch_Toolbar);
        setSupportActionBar(toolbar);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.actSearch_FragmentContainerView);
        navController = Objects.requireNonNull(navHostFragment).getNavController();
        //无法解决首页的向上按钮的返回问题
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(0)
                        .build();
        NavigationUI.setupWithNavController(
                toolbar, navController, appBarConfiguration);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    public void onBackPressed() {
        boolean res = navController.popBackStack();
        if (!res) {
            finish();
        }
    }

}
