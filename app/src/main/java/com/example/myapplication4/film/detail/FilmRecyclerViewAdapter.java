package com.example.myapplication4.film.detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapplication4.R;
import com.example.myapplication4.main.FilmDetailFragmentDirections;

import java.util.List;

public class FilmRecyclerViewAdapter extends RecyclerView.Adapter<FilmRecyclerViewAdapter.ViewHolder> {
    private final List<FilmDetailDataBean_Actor> filmDetailDataBean_actor_list;
    private final Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {

        View view;
        ImageView imageView;
        TextView textView1, textView2;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            imageView = view.findViewById(R.id.recycleview_filmdetail_ImageView);
            textView1 = view.findViewById(R.id.recycleview_filmdetail_TextView2);
            textView2 = view.findViewById(R.id.recycleview_filmdetail_TextView4);
        }
    }


    public FilmRecyclerViewAdapter(List<FilmDetailDataBean_Actor> filmDetailDataBean_actor_list, Context context) {
        this.filmDetailDataBean_actor_list = filmDetailDataBean_actor_list;
        this.context =context;
    }

    @NonNull
    @Override
    public FilmRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycleview_filmdetail, viewGroup, false);
        return new FilmRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FilmRecyclerViewAdapter.ViewHolder viewHolder, final int position) {
        FilmDetailDataBean_Actor filmDetailDataBean_actor = filmDetailDataBean_actor_list.get(position);
        viewHolder.textView1.setText(filmDetailDataBean_actor.actor_name);
        viewHolder.textView2.setText(filmDetailDataBean_actor.actor_roles);

        String imageUrl = filmDetailDataBean_actor.actor_coverUrl;
        ImageView imageView =viewHolder.imageView;
        //使得ViewHolder再次使用时，取消上次仍在进行的网络请求
        Glide.clear(imageView);

//        //使得ViewHolder再次使用时，没有上一次的图片
//        imageView.setImageResource(R.drawable.test_image);

        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.test_image)
                .error(R.drawable.test_image)
                .crossFade()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageView);

        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = filmDetailDataBean_actor.actor_url;
                FilmDetailFragmentDirections.ActionFilmDetailFragmentToFilmActorFragment action = FilmDetailFragmentDirections
                        .actionFilmDetailFragmentToFilmActorFragment(url);
                Navigation.findNavController(v).navigate(action);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filmDetailDataBean_actor_list.size();
    }
}
