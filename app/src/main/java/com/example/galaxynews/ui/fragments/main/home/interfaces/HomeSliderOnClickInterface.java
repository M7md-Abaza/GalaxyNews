package com.example.galaxynews.ui.fragments.main.home.interfaces;

import com.example.galaxynews.databinding.ItemSliderHomeBinding;
import com.example.galaxynews.pojo.Article;

public interface HomeSliderOnClickInterface {

    void homeSliderOnItemClick(Article article);

    void homeSliderOnBookMarkClick(int position, ItemSliderHomeBinding itemSliderHomeBinding);
}
