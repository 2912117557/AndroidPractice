package com.example.myapplication4.film.brief.hotList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication4.R;

public class FilmHotList_PagedListAdapter extends PagedListAdapter<FHLBriefDataBean, FilmHotList_PagedListAdapter.ViewHolder> {
    private static final DiffUtil.ItemCallback<FHLBriefDataBean> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<FHLBriefDataBean>() {
                @Override
                public boolean areItemsTheSame(FHLBriefDataBean oldFHLBriefDataBean, FHLBriefDataBean newFHLBriefDataBean) {
                    return oldFHLBriefDataBean.rowid == newFHLBriefDataBean.rowid;
                }

                @Override
                public boolean areContentsTheSame(FHLBriefDataBean oldFHLBriefDataBean,
                                                  @NonNull FHLBriefDataBean newFHLBriefDataBean) {
                    return oldFHLBriefDataBean.equals(newFHLBriefDataBean);
                }
            };

    static class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        ImageView imageView;
        TextView textView1, textView2;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            imageView = view.findViewById(R.id.recyclerview_filmhotlist_book_ImageView);
            textView1 = view.findViewById(R.id.recyclerview_filmhotlist_book_TextView1);
            textView2 = view.findViewById(R.id.recyclerview_filmhotlist_book_TextView2);
        }

    }

    public FilmHotList_PagedListAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_filmhotlist_book, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,
                                 int position) {
        FHLBriefDataBean fHLBriefDataBean = getItem(position);
        holder.textView1.setText(fHLBriefDataBean.title);
        holder.textView2.setText(fHLBriefDataBean.year + " 年");
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = fHLBriefDataBean.id;
                Bundle bundle = new Bundle();
                bundle.putString("filmId", id);
                Navigation.findNavController(v).navigate(R.id.film_detail_navgraph, bundle);
            }
        });

    }


}
