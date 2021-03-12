package com.example.myapplication4.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication4.R;
import com.example.myapplication4.shared.MyFragmentStateAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FilmBookMusicFragment extends Fragment {

    public static FilmBookMusicFragment newInstance(String tag){
        FilmBookMusicFragment filmBookMusicFragment = new FilmBookMusicFragment();
        Bundle bundle = new Bundle();
        bundle.putString("tag",tag);
        filmBookMusicFragment.setArguments(bundle);
        return filmBookMusicFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_film_book_music, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String tag = requireArguments().getString("tag");

        List<Fragment> mFragments = new ArrayList<Fragment>();
        List<String> mTitles = new ArrayList<String>();
        switch (tag) {
            case "film":
                ListFragment filmHotListFragment = ListFragment.newInstance("film_HotList");
                ListFragment filmTop250Fragment = ListFragment.newInstance("film_Top250");

                mFragments.add(filmHotListFragment);
                mFragments.add(filmTop250Fragment);

                mTitles.add("热映榜");
                mTitles.add("TOP250");

                break;

            case "book":
                ListFragment bookAllFragment = ListFragment.newInstance("book_All");
                ListFragment bookLiteratureFragment = ListFragment.newInstance("book_Literature");
                ListFragment bookPopularFragment = ListFragment.newInstance("book_Popular");
                ListFragment bookCultureFragment = ListFragment.newInstance("book_Culture");
                ListFragment bookLifeFragment = ListFragment.newInstance("book_Life");

                mFragments.add(bookAllFragment);
                mFragments.add(bookLiteratureFragment);
                mFragments.add(bookPopularFragment);
                mFragments.add(bookCultureFragment);
                mFragments.add(bookLifeFragment);

                mTitles.add("综合");
                mTitles.add("文学");
                mTitles.add("流行");
                mTitles.add("文化");
                mTitles.add("生活");

                break;

            case "music":
                ListFragment musicPopularFragment = ListFragment.newInstance("music_Popular");
                ListFragment musicClassicFragment = ListFragment.newInstance("music_Classic");
                ListFragment musicKoreanFragment = ListFragment.newInstance("music_Korean");
                ListFragment musicEuropeAmericaFragment = ListFragment.newInstance("music_EuropeAmerica");

                mFragments.add(musicPopularFragment);
                mFragments.add(musicClassicFragment);
                mFragments.add(musicKoreanFragment);
                mFragments.add(musicEuropeAmericaFragment);

                mTitles.add("流行");
                mTitles.add("经典");
                mTitles.add("韩系");
                mTitles.add("欧美");

                break;

        }

        ViewPager2 mViewPager2 = view.findViewById(R.id.fragFilmBookMusic_ViewPager2);
        MyFragmentStateAdapter myFragmentStateAdapter = new MyFragmentStateAdapter(this, mFragments);
        mViewPager2.setAdapter(myFragmentStateAdapter);
        mViewPager2.setOffscreenPageLimit(mFragments.size());

        TabLayout mTabLayout = view.findViewById(R.id.fragFilmBookMusic_TabLayout);
        new TabLayoutMediator(mTabLayout, mViewPager2, ((tab, position) -> tab.setText(mTitles.get(position)))).attach();

    }
}
