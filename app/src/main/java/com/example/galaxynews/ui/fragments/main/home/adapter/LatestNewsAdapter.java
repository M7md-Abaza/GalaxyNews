package com.example.galaxynews.ui.fragments.main.home.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.galaxynews.databinding.ItemLatestNewsBinding;
import com.example.galaxynews.pojo.Article;
import com.example.galaxynews.ui.fragments.main.home.interfaces.HomeLatestOnClickInterface;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

public class LatestNewsAdapter extends RecyclerView.Adapter<LatestNewsAdapter.ViewHolder> {

    private HomeLatestOnClickInterface homeLatestOnClickInterface;

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

        String[] data = NewsList.get(position).getPublishedAt().split("T");
        holder.binding.itemTime.setText(data[data.length - 2]);

        String imageUrl = NewsList.get(position).getUrlToImage();
        Picasso.get().load(imageUrl).into(holder.binding.itemImage);

        holder.binding.getRoot().setOnClickListener(v -> {
            if (homeLatestOnClickInterface != null) {
                int pos = holder.getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    homeLatestOnClickInterface.homeLatestOnItemClick(NewsList.get(pos));
                }
            }
        });

        holder.binding.itemBookMark.setOnClickListener(v -> {
            if (homeLatestOnClickInterface != null) {
                int pos = holder.getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    homeLatestOnClickInterface.homeLatestOnBookMarkClick(position, holder.binding);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return NewsList.size();
    }

    // setList Function data pass to it then it pass data to PostsList on the Holder
    @SuppressLint("NotifyDataSetChanged")
    public void setList(List<Article> NewsList, HomeLatestOnClickInterface homeLatestOnClickInterface) {
        this.NewsList = NewsList;
        this.homeLatestOnClickInterface = homeLatestOnClickInterface;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemLatestNewsBinding binding;

        public ViewHolder(@NonNull ItemLatestNewsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }



/*    private void printDifference(Date lastDate , Date currentDate) {
        //milliseconds
        long different = currentDate.getTime() - lastDate.getTime();
        int secondsInMilli = 1000;
        int minutesInMilli = secondsInMilli * 60;
        int hoursInMilli = minutesInMilli * 60;
        int daysInMilli = hoursInMilli * 24;
        long elapsedDays = different / daysInMilli;
        different %= daysInMilli;
        long elapsedHours = different / hoursInMilli;
        different %= hoursInMilli;
        long elapsedMinutes = different / minutesInMilli;
        different %= minutesInMilli;
        long elapsedSeconds = different / secondsInMilli;

    }*/
}
