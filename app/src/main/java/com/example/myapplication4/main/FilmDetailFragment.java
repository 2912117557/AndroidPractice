package com.example.myapplication4.main;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapplication4.R;
import com.example.myapplication4.film.detail.FilmDetailDataBean;
import com.example.myapplication4.film.detail.FilmDetailDataBean_Actor;
import com.example.myapplication4.film.detail.FilmDetailDataBean_Basic;
import com.example.myapplication4.film.detail.FilmRecyclerViewAdapter;
import com.example.myapplication4.film.detail.FilmRepository;
import com.example.myapplication4.film.detail.FilmViewModel;
import com.example.myapplication4.film.detail.FilmViewModelFactory;
import com.example.myapplication4.myLib.NetworkErrorHandle;
import com.example.myapplication4.shared.DetailDataDatabase;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class FilmDetailFragment extends Fragment {
    private final String logTag = "FilmDetailFragment";
    private AppCompatActivity mActivity;
    private FilmRepository filmRepository;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_film_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity = (AppCompatActivity) requireActivity();

        String filmId = requireArguments().getString("filmId");

        DetailDataDatabase db = Room.databaseBuilder(mActivity.getApplicationContext(),
                DetailDataDatabase.class, "DetailDataDatabase")
                .fallbackToDestructiveMigration()
                .build();

        FilmViewModel filmViewModel = new ViewModelProvider(this, new FilmViewModelFactory(db, mActivity, filmId))
                .get(FilmViewModel.class);
        filmRepository =filmViewModel.getFilmRepository();
        TextView errorTextView = view.findViewById(R.id.fragFilmDetail_ErrorTextView);
        NetworkErrorHandle.handleError(errorTextView, getViewLifecycleOwner(), filmViewModel, filmRepository);

        filmViewModel.getfilmDetailDataBean_LiveData().observe(getViewLifecycleOwner(), new Observer<FilmDetailDataBean>() {
            @Override
            public void onChanged(FilmDetailDataBean filmDetailDataBean) {
                if (filmDetailDataBean != null) {
                    FilmDetailDataBean_Basic filmDetailDataBean_basic = filmDetailDataBean.filmDetailDataBean_basic;
                    ((TextView) view.findViewById(R.id.ffd_TextView2)).setText(filmDetailDataBean_basic.title);
                    ((TextView) view.findViewById(R.id.ffd_TextView4)).setText(filmDetailDataBean_basic.original_title);
                    if (filmDetailDataBean_basic.is_tv) {
                        ((TextView) view.findViewById(R.id.ffd_TextView6)).setText("电视剧");
                    } else {
                        ((TextView) view.findViewById(R.id.ffd_TextView6)).setText("电影");
                    }
                    ((TextView) view.findViewById(R.id.ffd_TextView8)).setText(filmDetailDataBean_basic.countries);
                    ((TextView) view.findViewById(R.id.ffd_TextView10)).setText(filmDetailDataBean_basic.languages);
                    ((TextView) view.findViewById(R.id.ffd_TextView12)).setText(filmDetailDataBean_basic.year + "年");
                    ((TextView) view.findViewById(R.id.ffd_TextView14)).setText(String.format("%.1f", filmDetailDataBean_basic.rating_value) + "分");
                    ((TextView) view.findViewById(R.id.ffd_TextView15)).setText(filmDetailDataBean_basic.rating_count + "人评价");
                    ((TextView) view.findViewById(R.id.ffd_TextView19)).setText(filmDetailDataBean_basic.intro);

                    ImageView imageView = view.findViewById(R.id.fragFilmDetail_ImageView);
                    String imageUrl = filmDetailDataBean_basic.pic_normal;
                    Glide.with(mActivity)
                            .load(imageUrl)
                            .placeholder(R.drawable.test_image)
                            .error(R.drawable.test_image)
                            .crossFade()
                            .skipMemoryCache(true)
                            .diskCacheStrategy(DiskCacheStrategy.RESULT)
                            .into(imageView);

                    RecyclerView recyclerView = view.findViewById(R.id.fragFilmDetail_RecycleView);
                    recyclerView.setLayoutManager(new LinearLayoutManager(mActivity) {
                        @Override
                        public boolean canScrollVertically() {
                            return false;
                        }
                    });

                    List<FilmDetailDataBean_Actor> filmDetailDataBean_actor_list = filmDetailDataBean.filmDetailDataBean_actor_list;
                    FilmRecyclerViewAdapter filmRecyclerViewAdapter = new FilmRecyclerViewAdapter(filmDetailDataBean_actor_list,
                            mActivity);
                    recyclerView.setAdapter(filmRecyclerViewAdapter);
                }
            }
        });

        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.fragFilmDetail_SwipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                filmRepository.refreshDetailData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
