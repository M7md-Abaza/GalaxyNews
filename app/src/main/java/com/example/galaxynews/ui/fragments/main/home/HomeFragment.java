package com.example.galaxynews.ui.fragments.main.home;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.galaxynews.R;
import com.example.galaxynews.databinding.FragmentHomeBinding;
import com.example.galaxynews.databinding.ItemLatestNewsBinding;
import com.example.galaxynews.databinding.ItemSliderHomeBinding;
import com.example.galaxynews.pojo.Article;
import com.example.galaxynews.ui.fragments.main.home.interfaces.HomeLatestOnClickInterface;
import com.example.galaxynews.ui.fragments.main.home.interfaces.HomeSliderOnClickInterface;
import com.example.galaxynews.ui.fragments.main.home.adapter.LatestNewsAdapter;
import com.example.galaxynews.ui.fragments.main.home.adapter.NewsSliderAdapter;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends Fragment implements HomeSliderOnClickInterface, HomeLatestOnClickInterface {

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;
    @Inject
    LatestNewsAdapter latestNewsAdapter;
    @Inject
    NewsSliderAdapter newsSliderAdapter;

    Boolean isSliderBookMark = false;
    Boolean isLatestBookMark = false;

    private final Handler slideHandler = new Handler();

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
        visProgress(true);
        binding.rvLatestNews.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvLatestNews.setAdapter(latestNewsAdapter);
        binding.homeSlider.setAdapter(newsSliderAdapter);


    }

    private void data() {
        if (viewModel.isOnline(requireContext())) {
            viewModel.getNews();
            viewModel.getLatestNews();
        } else {
            Navigation.findNavController(requireView()).navigate(R.id.noNetworkFragment);
        }
    }

    private void observe() {
        viewModel.sliderNewsMutableLiveData.observe(requireActivity(), newsList -> {
            newsSliderAdapter.setList(newsList, binding.homeSlider, this);
            setupSlide();
            visProgress(false);
        });

        viewModel.latestNewsMutableLiveData.observe(requireActivity(), (List<Article> newsList) ->
                {
                    visProgress(false);

                    if (newsList == null || newsList.isEmpty()) {
                        binding.rvLatestNews.setVisibility(View.INVISIBLE);
                        binding.tvNoData.setVisibility(View.VISIBLE);
                    } else {
                        latestNewsAdapter.setList(newsList, this);
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
                slideHandler.postDelayed(slideRunnable, 3000);
            }
        });
    }

    private final Runnable slideRunnable = new Runnable() {
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
        slideHandler.postDelayed(slideRunnable, 3000);
    }

    private void visProgress(boolean b) {

        if (b) {
            binding.progressLayout.startShimmer();
            binding.progressLayout.setVisibility(View.VISIBLE);
            binding.homeSlider.setVisibility(View.INVISIBLE);
            binding.rvLatestNews.setVisibility(View.INVISIBLE);
        } else {
            binding.progressLayout.stopShimmer();
            binding.progressLayout.setVisibility(View.GONE);
            binding.homeSlider.setVisibility(View.VISIBLE);
            binding.rvLatestNews.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void homeSliderOnItemClick(Article article) {
        gotoDetails(article);
    }

    @Override
    public void homeLatestOnItemClick(Article article) {
        gotoDetails(article);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void homeSliderOnBookMarkClick(int position, ItemSliderHomeBinding itemSliderHomeBinding) {
        if (isSliderBookMark) {
            itemSliderHomeBinding.sliderBookMark.setImageDrawable(getResources().getDrawable(R.drawable.ic_bookmark_white, null));
            isSliderBookMark = false;
        } else {
            itemSliderHomeBinding.sliderBookMark.setImageDrawable(getResources().getDrawable(R.drawable.ic_bookmark_yellow, null));
            isSliderBookMark = true;
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void homeLatestOnBookMarkClick(int position, ItemLatestNewsBinding itemLatestNewsBinding) {
        if (isLatestBookMark) {
            itemLatestNewsBinding.itemBookMark.setImageDrawable(getResources().getDrawable(R.drawable.ic_bookmark, null));
            isLatestBookMark = false;
        } else {
            itemLatestNewsBinding.itemBookMark.setImageDrawable(getResources().getDrawable(R.drawable.ic_bookmark_yellow, null));
            isLatestBookMark = true;
        }
    }


    private void gotoDetails(Article article) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("para", article);
        Navigation.findNavController(requireView()).navigate(R.id.detailsFragment, bundle);
    }
}