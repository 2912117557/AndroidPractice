package com.example.myapplication4.main;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication4.R;
import com.example.myapplication4.search.MySearchRecentSuggestionsProvider;
import com.example.myapplication4.shared.MyFragmentStateAdapter;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ContentFragment extends Fragment {
    private final String logTag = "ContentFragment";
    private AppCompatActivity mActivity;
    private RadioGroup radioGroup;
    private ViewPager2 mViewPager2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_content, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity = (AppCompatActivity) requireActivity();

        radioGroup = mActivity.findViewById(R.id.actMain_Radiogroup);
        radioGroup.setOnCheckedChangeListener(onCheckedChangeListener);

        FilmBookMusicFragment filmFragment = FilmBookMusicFragment.newInstance("film");
        FilmBookMusicFragment bookFragment = FilmBookMusicFragment.newInstance("book");
        FilmBookMusicFragment musicFragment = FilmBookMusicFragment.newInstance("music");

        List<Fragment> mFragments = new ArrayList<Fragment>();
        mFragments.add(filmFragment);
        mFragments.add(bookFragment);
        mFragments.add(musicFragment);

        mViewPager2 = view.findViewById(R.id.fragContent_ViewPager2);
        MyFragmentStateAdapter myFragmentStateAdapter = new MyFragmentStateAdapter(this, mFragments);
        mViewPager2.setAdapter(myFragmentStateAdapter);
        mViewPager2.registerOnPageChangeCallback(onPageChangeCallback);
        Objects.requireNonNull(
                ((RecyclerView) mViewPager2.getChildAt(0)).getLayoutManager()
        ).setItemPrefetchEnabled(false);


    }

    @Override
    public void onResume() {
        super.onResume();
        radioGroup.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStop() {
        super.onStop();
        radioGroup.setVisibility(View.GONE);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_content_fragment_optionmenu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView mSearchView = (SearchView) searchItem.getActionView();
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryRefinementEnabled(true);
        mSearchView.setQueryHint("搜索媒体的名称");

        SearchManager searchManager = (SearchManager) mActivity.getSystemService(Context.SEARCH_SERVICE);
        ComponentName componentName = new ComponentName("com.example.myapplication4", "com.example.myapplication4.main.SearchActivity");
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(componentName);
        mSearchView.setSearchableInfo(searchableInfo);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_clearSearchHistory) {
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(mActivity,
                    MySearchRecentSuggestionsProvider.AUTHORITY, MySearchRecentSuggestionsProvider.MODE);
            suggestions.clearHistory();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private final ViewPager2.OnPageChangeCallback onPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }

        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            switch (position) {
                case 0:
                    radioGroup.check(R.id.actMain_RadioButton_film);
                    break;
                case 1:
                    radioGroup.check(R.id.actMain_RadioButton_book);
                    break;
                case 2:
                    radioGroup.check(R.id.actMain_RadioButton_music);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            super.onPageScrollStateChanged(state);
        }
    };

    private final RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            int currentFragment = 0;
            switch (checkedId) {
                case R.id.actMain_RadioButton_film:
                    currentFragment = 0;
                    break;
                case R.id.actMain_RadioButton_book:
                    currentFragment = 1;
                    break;
                case R.id.actMain_RadioButton_music:
                    currentFragment = 2;
                    break;
            }
            mViewPager2.setCurrentItem(currentFragment, true);
        }
    };

}


