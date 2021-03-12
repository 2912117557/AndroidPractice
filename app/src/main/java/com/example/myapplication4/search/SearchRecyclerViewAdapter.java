package com.example.myapplication4.search;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapplication4.R;

import java.util.List;

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.ViewHolder> {

    private final List<SearchBean> searchBean_list;
    private final AppCompatActivity activity;

    static class ViewHolder extends RecyclerView.ViewHolder {

        View view;
        ImageView imageView;
        TextView textView1,textView2;
        public ViewHolder(View view) {
            super(view);
            this.view = view;
            imageView = view.findViewById(R.id.recyclerview_search_ImageView);
            textView1 = view.findViewById(R.id.recyclerview_search_TextView1);
            textView2 = view.findViewById(R.id.recyclerview_search_TextView2);
        }
    }


    public SearchRecyclerViewAdapter(List<SearchBean> searchBean_list, AppCompatActivity activity) {
        this.searchBean_list = searchBean_list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public SearchRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recyclerview_search, viewGroup, false);
        return new SearchRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchRecyclerViewAdapter.ViewHolder viewHolder, final int position) {
        SearchBean searchBean = searchBean_list.get(position);
        ImageView imageView = viewHolder.imageView;
        if(searchBean.image!=null){
            //使得ViewHolder再次使用时，取消上次仍在进行的网络请求
            Glide.clear(imageView);

            //使得ViewHolder再次使用时，没有上一次的图片
//            imageView.setImageResource(R.drawable.test_image);

            String imageUrl = searchBean.image;
            Glide.with(activity)
                    .load(imageUrl)
                    .placeholder(R.drawable.test_image)
                    .error(R.drawable.test_image)
                    .crossFade()
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .into(imageView);

        }
        viewHolder.textView1.setText(searchBean.type);
        viewHolder.textView2.setText(searchBean.title);

        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = searchBean.id;
                switch (searchBean.type) {
                    case "电影":
                        Bundle bundle = new Bundle();
                        bundle.putString("filmId", id);
                        Navigation.findNavController(v).navigate(R.id.film_detail_navgraph, bundle);
                        break;
                    case "图书":
                        Toast.makeText(activity, "没有图书详细信息", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchBean_list.size();
    }
}

