package com.example.galaxynews.ui.fragments.main.search;



import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.galaxynews.R;
import com.example.galaxynews.databinding.FragmentSearchBinding;
import com.example.galaxynews.databinding.ItemLatestNewsBinding;
import com.example.galaxynews.pojo.Article;
import com.example.galaxynews.ui.fragments.main.home.adapter.LatestNewsAdapter;
import com.example.galaxynews.ui.fragments.main.home.interfaces.HomeLatestOnClickInterface;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SearchFragment extends Fragment implements HomeLatestOnClickInterface {

    private FragmentSearchBinding binding;

    private SearchViewModel viewModel;
    @Inject
    LatestNewsAdapter latestNewsAdapter;

    Boolean isLatestBookMark = false;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        setup();
        observe();
    }

    private void setup() {

        binding.rvLatestNews.setAdapter(latestNewsAdapter);

        binding.edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable != null && !editable.toString().equals("")) {
                    visProgress(true);
                    data(editable.toString());
                } else {
                    binding.rvLatestNews.setVisibility(View.INVISIBLE);
                    binding.tvNoData.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void data(String search) {
        if (viewModel.isOnline(requireContext())) {
            viewModel.getSearchResults(search);
        } else {
            Navigation.findNavController(requireView()).navigate(R.id.noNetworkFragment);
        }
    }

    private void observe() {

        viewModel.searchMutableLiveData.observe(requireActivity(), dataList -> {
            visProgress(false);
            if (!dataList.isEmpty())
                ui(dataList);
        });

    }

    private void ui(List<Article> dataList) {
        if (dataList == null || dataList.isEmpty()) {
            binding.rvLatestNews.setVisibility(View.INVISIBLE);
            binding.tvNoData.setVisibility(View.VISIBLE);
        } else {
            latestNewsAdapter.setList(dataList, this);
            binding.rvLatestNews.setVisibility(View.VISIBLE);
            binding.tvNoData.setVisibility(View.GONE);
        }
    }

    @Override
    public void homeLatestOnItemClick(Article article) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("para", article);
        Navigation.findNavController(requireView()).navigate(R.id.detailsFragment, bundle);
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

    private void visProgress(boolean b) {

        if (b) {
            binding.progressLayout.startShimmer();
            binding.progressLayout.setVisibility(View.VISIBLE);
            binding.rvLatestNews.setVisibility(View.INVISIBLE);
        } else {
            binding.progressLayout.stopShimmer();
            binding.progressLayout.setVisibility(View.GONE);
            binding.rvLatestNews.setVisibility(View.VISIBLE);
        }
    }
}