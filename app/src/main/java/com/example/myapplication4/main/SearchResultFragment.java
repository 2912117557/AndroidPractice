package com.example.myapplication4.main;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.SearchRecentSuggestions;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
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

import java.util.ArrayList;
import java.util.List;

public class SearchResultFragment extends Fragment{
    private AppCompatActivity mActivity;

    private BriefDataDatabase briefDataDatabase;
    private RecyclerView recyclerView;
    private TextView notFoundTextView;
    private final Handler handler = new Handler();
    private String query;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_result,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity = (AppCompatActivity) requireActivity();

        briefDataDatabase = Room.databaseBuilder(mActivity.getApplicationContext(),
                BriefDataDatabase.class, "BriefDataDatabase")
                .fallbackToDestructiveMigration()
                .build();

        recyclerView = view.findViewById(R.id.fragSearchResult_RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));

        notFoundTextView = view.findViewById(R.id.fragSearchResult_NotFoundTextView);

    }

    @Override
    public void onResume() {
        super.onResume();
        handleIntent(mActivity.getIntent());
    }

    public void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(mActivity,
                    MySearchRecentSuggestionsProvider.AUTHORITY, MySearchRecentSuggestionsProvider.MODE);
            suggestions.saveRecentQuery(query, null);
            doSearch();
        }
    }

    public void doSearch() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<SearchBean> searchBean_list = new ArrayList<>();
                List<FHLBriefDataBean> fhlBriefDataBean_list = briefDataDatabase.fHLBriefDataDao().search(query);
                for (int i = 0; i < fhlBriefDataBean_list.size(); i++) {
                    FHLBriefDataBean bean = fhlBriefDataBean_list.get(i);
                    searchBean_list.add(new SearchBean(bean.id, bean.title, "电影", null));
                }

                List<BookBriefDataBean> bookBriefDataBean_list = briefDataDatabase.bookBriefDataDao().search(query);
                for (int i = 0; i < bookBriefDataBean_list.size(); i++) {
                    BookBriefDataBean bean = bookBriefDataBean_list.get(i);
                    searchBean_list.add(new SearchBean(bean.id, bean.title, "图书", bean.image));
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (searchBean_list.size() != 0) {
                            notFoundTextView.setVisibility(View.GONE);
                            SearchRecyclerViewAdapter searchRecyclerViewAdapter = new SearchRecyclerViewAdapter(searchBean_list,
                                    mActivity);
                            recyclerView.setAdapter(searchRecyclerViewAdapter);
                        } else {
                            recyclerView.setAdapter(null);
                            notFoundTextView.setText("没找到与 " + query + "\n相关的数据");
                            notFoundTextView.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        }).start();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search_result_fragment_optionmenu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView mSearchView = (SearchView) searchItem.getActionView();
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setQuery(query, false);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryRefinementEnabled(true);
        mSearchView.setQueryHint("搜索媒体的名称");

        SearchManager searchManager = (SearchManager) mActivity.getSystemService(Context.SEARCH_SERVICE);
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(mActivity.getComponentName()));
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
}
