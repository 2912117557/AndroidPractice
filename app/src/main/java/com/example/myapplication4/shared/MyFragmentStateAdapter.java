package com.example.myapplication4.shared;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

/**
 * ViewPager2的adapter
 */
public class MyFragmentStateAdapter extends FragmentStateAdapter {
    List<Fragment> mFragments;

    public MyFragmentStateAdapter(@NonNull Fragment fragment, List<Fragment> mFragments) {
        super(fragment);
        this.mFragments = mFragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getItemCount() {
        return mFragments.size();
    }
}
