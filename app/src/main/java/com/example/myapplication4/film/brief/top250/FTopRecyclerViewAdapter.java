package com.example.myapplication4.film.brief.top250;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication4.R;
import com.example.myapplication4.main.ContentFragmentDirections;

import java.util.List;

public class FTopRecyclerViewAdapter extends RecyclerView.Adapter<FTopRecyclerViewAdapter.ViewHolder> {

    List<FTopBriefDataBean> briefDataList;

    static class ViewHolder extends RecyclerView.ViewHolder {

        View view;
        ImageView imageView1;
        TextView textView1,textView2,textView3,textView4;
        public ViewHolder(View view) {
            super(view);
            this.view = view;
            imageView1 = view.findViewById(R.id.recyclerview_filmtop_ImageView1);
            textView1 = view.findViewById(R.id.recyclerview_filmtop_TextView1);
            textView2 = view.findViewById(R.id.recyclerview_filmtop_TextView2);
            textView3 = view.findViewById(R.id.recyclerview_filmtop_TextView3);
            textView4 = view.findViewById(R.id.recyclerview_filmtop_TextView4);

        }
    }


    public FTopRecyclerViewAdapter(List<FTopBriefDataBean> briefDataList) {
        this.briefDataList = briefDataList;
    }

    @NonNull
    @Override
    public FTopRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recyclerview_filmtop, viewGroup, false);
        return new FTopRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FTopRecyclerViewAdapter.ViewHolder viewHolder, final int position) {
        FTopBriefDataBean fTopBriefDataBean = briefDataList.get(position);
        viewHolder.textView1.setText(String.format("%02d",position));
        viewHolder.textView2.setText(fTopBriefDataBean.title);
        viewHolder.textView3.setText(fTopBriefDataBean.original_title);
        viewHolder.textView4.setText("评分："+String.format("%.1f",fTopBriefDataBean.score));
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = fTopBriefDataBean.id;
                Bundle bundle = new Bundle();
                bundle.putString("filmId", id);
                Navigation.findNavController(v).navigate(R.id.film_detail_navgraph, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return briefDataList.size();
    }
}

