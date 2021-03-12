package com.example.myapplication4.book.brief;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapplication4.R;

public class Book_PagedListAdapter extends PagedListAdapter<BookBriefDataBean, Book_PagedListAdapter.ViewHolder> {
    String logTag = "Book_PagedListAdapter";
    private final Context context;

    private static final DiffUtil.ItemCallback<BookBriefDataBean> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<BookBriefDataBean>() {
                @Override
                public boolean areItemsTheSame(BookBriefDataBean oldBookBriefDataBean, BookBriefDataBean newBookBriefDataBean) {
                    return oldBookBriefDataBean.rowid == newBookBriefDataBean.rowid;
                }

                @Override
                public boolean areContentsTheSame(BookBriefDataBean oldBookBriefDataBean,
                                                  @NonNull BookBriefDataBean newBookBriefDataBean) {
                    return oldBookBriefDataBean.equals(newBookBriefDataBean);
                }
            };

    static class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        ImageView imageView;
        TextView textView1,textView2;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            imageView = view.findViewById(R.id.recyclerview_filmhotlist_book_ImageView);
            textView1 = view.findViewById(R.id.recyclerview_filmhotlist_book_TextView1);
            textView2 =view.findViewById(R.id.recyclerview_filmhotlist_book_TextView2);
        }

    }

    public Book_PagedListAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
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
        BookBriefDataBean bookBriefDataBean = getItem(position);
        ImageView imageView = holder.imageView;

        holder.textView1.setText(bookBriefDataBean.title);
        holder.textView2.setText("评分："+bookBriefDataBean.rating.average);

        //使得ViewHolder再次使用时，取消上次仍在进行的网络请求
        Glide.clear(imageView);

        //使得ViewHolder再次使用时，没有上一次的图片
//        imageView.setImageResource(R.drawable.test_image);

        String imageUrl = bookBriefDataBean.image;
        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.test_image)
                .error(R.drawable.test_image)
                .crossFade()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageView);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = bookBriefDataBean.id;
            }
        });

    }


}
