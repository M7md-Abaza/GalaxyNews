package com.example.galaxynews.ui.fragments.main.home;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.galaxynews.databinding.ItemLatestNewsBinding;
import com.example.galaxynews.pojo.Article;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import kotlin.Unit;

public class LatestNewsAdapter extends RecyclerView.Adapter<LatestNewsAdapter.ViewHolder> {

    private List<Article> NewsList = new ArrayList<>();

    @Inject
    public LatestNewsAdapter() {
        //Empty
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLatestNewsBinding view = ItemLatestNewsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.binding.itemTitle.setText(NewsList.get(position).getTitle());
        holder.binding.itemTime.setText(NewsList.get(position).getPublishedAt());

        String imageUrl = NewsList.get(position).getUrlToImage();
        Picasso.get().load(imageUrl).into(holder.binding.itemImage);

        holder.binding.getRoot().setOnClickListener(v -> {

        });

    }

    @Override
    public int getItemCount() {
        return NewsList.size();
    }

    // setList Function data pass to it then it pass data to PostsList on the Holder
    @SuppressLint("NotifyDataSetChanged")
    void setList(List<Article> NewsList) {
        this.NewsList = NewsList;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemLatestNewsBinding binding;

        public ViewHolder(@NonNull ItemLatestNewsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
