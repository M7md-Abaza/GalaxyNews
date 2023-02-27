package com.example.galaxynews.ui.fragments.main.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.galaxynews.databinding.FragmentHomeBinding;
import com.example.galaxynews.pojo.Article;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;
    @Inject
    LatestNewsAdapter latestNewsAdapter;
    @Inject
    NewsSliderAdapter newsSliderAdapter;

    private Handler slideHandler = new Handler();

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        setup();
        data();
        observe();
    }

    private void setup() {
        binding.rvLatestNews.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvLatestNews.setAdapter(latestNewsAdapter);
        binding.homeSlider.setAdapter(newsSliderAdapter);


    }

    private void data() {
        viewModel.getNews();
        viewModel.getLatestNews();
    }

    private void observe() {
        viewModel.sliderNewsMutableLiveData.observe(requireActivity(), newsList -> {
            newsSliderAdapter.setList(newsList, binding.homeSlider);
            setupSlide();
        });

        viewModel.latestNewsMutableLiveData.observe(requireActivity(), (List<Article> newsList) ->
                {
                    if (newsList == null || newsList.isEmpty()) {
                        binding.rvLatestNews.setVisibility(View.INVISIBLE);
                        binding.tvNoData.setVisibility(View.VISIBLE);
                    } else {
                        latestNewsAdapter.setList(newsList);
                        binding.rvLatestNews.setVisibility(View.VISIBLE);
                        binding.tvNoData.setVisibility(View.GONE);
                    }
                }
        );
    }

    private void setupSlide() {
        binding.homeSlider.setClipToPadding(false);
        binding.homeSlider.setClipChildren(false);
        binding.homeSlider.setOffscreenPageLimit(3);
        binding.homeSlider.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.85f + r * 0.14f);
        });

        binding.homeSlider.setPageTransformer(compositePageTransformer);

        binding.homeSlider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                slideHandler.removeCallbacks(slideRunnable);
                slideHandler.postDelayed(slideRunnable,3000);
            }
        });
    }

    private Runnable slideRunnable = new Runnable() {
        @Override
        public void run() {
            binding.homeSlider.setCurrentItem(binding.homeSlider.getCurrentItem() + 1);
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        slideHandler.removeCallbacks(slideRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        slideHandler.postDelayed(slideRunnable,3000);
    }
}