package com.example.myapplication4.template;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication4.R;
import com.example.myapplication4.film.brief.hotList.FHLBriefDataBean;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    List<FHLBriefDataBean> briefDataList;

    static class ViewHolder extends RecyclerView.ViewHolder {

        View view;
        public ViewHolder(View view) {
            super(view);
            this.view = view;

        }
    }


    public MyRecyclerViewAdapter(List<FHLBriefDataBean> briefDataList) {
        this.briefDataList = briefDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recyclerview_filmhotlist_book, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

    }

    @Override
    public int getItemCount() {
        return briefDataList.size();
    }
}

