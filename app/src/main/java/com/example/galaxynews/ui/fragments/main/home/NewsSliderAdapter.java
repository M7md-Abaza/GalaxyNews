package com.example.galaxynews.ui.fragments.main.home;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.galaxynews.R;
import com.example.galaxynews.databinding.ItemLatestNewsBinding;
import com.example.galaxynews.databinding.ItemSliderHomeBinding;
import com.example.galaxynews.pojo.Article;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class NewsSliderAdapter extends RecyclerView.Adapter<NewsSliderAdapter.NewsSliderViewHolder> {

    private List<Article> latestNewsList = new ArrayList<>();
    private ViewPager2 viewPager2;

    @Inject
    public NewsSliderAdapter() {
        //Empty
    }


    @NonNull
    @Override
    public NewsSliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSliderHomeBinding view = ItemSliderHomeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NewsSliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsSliderViewHolder holder, int position) {

        holder.binding.sliderTitle.setText(latestNewsList.get(position).getTitle());
        holder.binding.sliderTime.setText(latestNewsList.get(position).getPublishedAt());
        String imageUrl = latestNewsList.get(position).getUrlToImage();
        Picasso.get().load(imageUrl).into(holder.binding.sliderImage);

        if (position == latestNewsList.size()-2){
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return latestNewsList.size();
    }

    static class NewsSliderViewHolder extends RecyclerView.ViewHolder {

        ItemSliderHomeBinding binding;

        NewsSliderViewHolder(@NonNull ItemSliderHomeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    void setList(List<Article> NewsList, ViewPager2 viewPager2) {
        this.latestNewsList = NewsList;
        this.viewPager2 = viewPager2;
        Log.d(TAG, "setList observe:" + NewsList);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    private final Runnable runnable = () -> {
        latestNewsList.addAll(latestNewsList);
        notifyDataSetChanged();
    };
}
