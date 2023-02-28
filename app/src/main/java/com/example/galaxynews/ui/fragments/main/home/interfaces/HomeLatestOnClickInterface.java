package com.example.galaxynews.ui.fragments.main.home.interfaces;

import com.example.galaxynews.databinding.ItemLatestNewsBinding;
import com.example.galaxynews.pojo.Article;

public interface HomeLatestOnClickInterface {

    void homeLatestOnItemClick(Article article);

    void homeLatestOnBookMarkClick(int position, ItemLatestNewsBinding itemLatestNewsBinding);
}
