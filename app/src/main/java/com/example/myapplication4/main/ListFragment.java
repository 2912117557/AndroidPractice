package com.example.myapplication4.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapplication4.R;
import com.example.myapplication4.book.brief.BookBriefDataBean;
import com.example.myapplication4.book.brief.BookRepository;
import com.example.myapplication4.book.brief.BookViewModel;
import com.example.myapplication4.book.brief.BookViewModelFactory;
import com.example.myapplication4.book.brief.Book_PagedListAdapter;
import com.example.myapplication4.film.brief.hotList.FilmHotListRepository;
import com.example.myapplication4.film.brief.hotList.FilmHotListViewModel;
import com.example.myapplication4.film.brief.hotList.FilmHotListViewModelFactory;
import com.example.myapplication4.film.brief.hotList.FilmHotList_PagedListAdapter;
import com.example.myapplication4.film.brief.top250.FTopBriefDataBean;
import com.example.myapplication4.film.brief.top250.FTopRecyclerViewAdapter;
import com.example.myapplication4.myLib.NetworkErrorHandle;
import com.example.myapplication4.shared.BriefDataDatabase;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {
    private final String logTag = "ListFragment";
    private AppCompatActivity mActivity;

    private String tag;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Book_PagedListAdapter book_pagedListAdapter;
    private boolean isFirstLoad = true;

    public static ListFragment newInstance(String tag) {
        ListFragment listFragment = new ListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("tag", tag);
        listFragment.setArguments(bundle);
        return listFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity = (AppCompatActivity) requireActivity();

        tag = requireArguments().getString("tag");

        recyclerView = view.findViewById(R.id.fragList_RecycleView);
        swipeRefreshLayout = view.findViewById(R.id.fragList_SwipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        switch (tag) {
            case "film_HotList":
                filmHotListAction(view);
                break;
            case "film_Top250":
                filmTopAction();
                break;
            case "book_All":
            case "book_Literature":
            case "book_Popular":
            case "book_Culture":
            case "book_Life":
                bookSharedAction(view, tag);
                break;
        }
    }

    void filmHotListAction(View view) {
        recyclerView.setLayoutManager(new GridLayoutManager(mActivity, 3));
        BriefDataDatabase db = Room.databaseBuilder(mActivity.getApplicationContext(),
                BriefDataDatabase.class, "BriefDataDatabase")
                .fallbackToDestructiveMigration()
                .build();

        FilmHotListViewModel filmHotListViewModel = new ViewModelProvider(this, new FilmHotListViewModelFactory(db, mActivity))
                .get(FilmHotListViewModel.class);
        FilmHotListRepository filmHotListRepository = filmHotListViewModel.getFilmHotListRepository();

        TextView errorTextView = view.findViewById(R.id.fragList_ErrorTextView);
        NetworkErrorHandle.handleError(errorTextView, getViewLifecycleOwner(), filmHotListViewModel, filmHotListRepository);

        FilmHotList_PagedListAdapter filmHotList_pagedListAdapter = new FilmHotList_PagedListAdapter();
        filmHotListViewModel.getFHLBriefData_PagedList_LiveData().observe(getViewLifecycleOwner(), filmHotList_pagedListAdapter::submitList);
        recyclerView.setAdapter(filmHotList_pagedListAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                filmHotListRepository.refreshBriefData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    void filmTopAction() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        List<FTopBriefDataBean> fTopBriefDataList = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            fTopBriefDataList.add(new FTopBriefDataBean(1, "1920805", "成为简·奥斯汀", "Becoming Jane", (float) 8.3));
        }
        FTopRecyclerViewAdapter filmTop250_RecyclerViewAdapter = new FTopRecyclerViewAdapter(fTopBriefDataList);
        recyclerView.setAdapter(filmTop250_RecyclerViewAdapter);
    }

    void bookSharedAction(View view, String bookBigType) {
        recyclerView.setLayoutManager(new GridLayoutManager(mActivity, 3));
        BriefDataDatabase db = Room.databaseBuilder(mActivity.getApplicationContext(),
                BriefDataDatabase.class, "BriefDataDatabase")
                .fallbackToDestructiveMigration()
                .build();

        BookViewModel bookViewModel = new ViewModelProvider(this, new BookViewModelFactory(db, mActivity, bookBigType))
                .get(BookViewModel.class);
        BookRepository bookRepository = bookViewModel.getBookRepository();

        TextView errorTextView = view.findViewById(R.id.fragList_ErrorTextView);
        NetworkErrorHandle.handleError(errorTextView, getViewLifecycleOwner(), bookViewModel, bookRepository);

        book_pagedListAdapter = new Book_PagedListAdapter(mActivity);
        bookViewModel.getBookBriefData_PagedList_LiveData().observe(getViewLifecycleOwner(), book_pagedListAdapter::submitList);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                bookViewModel.getBookBriefData_PagedList_LiveData().removeObservers(getViewLifecycleOwner());
                LiveData<PagedList<BookBriefDataBean>> bookBriefData_PagedList_LiveData = bookRepository.refreshBook();
                bookBriefData_PagedList_LiveData.observe(getViewLifecycleOwner(), book_pagedListAdapter::submitList);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        switch (tag) {
            case "book_All":
            case "book_Literature":
            case "book_Popular":
            case "book_Culture":
            case "book_Life":
                if (isFirstLoad) {
                    //使用了ViewPager2预加载，在这个页面可见时，才执行onResume。为了使预加载的那几个页面在可见时，才执行图片加载
                    recyclerView.setAdapter(book_pagedListAdapter);
                    isFirstLoad = false;
                }
                break;
        }
    }


}
