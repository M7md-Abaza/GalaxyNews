package com.example.galaxynews.ui.fragments.no_network;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.galaxynews.R;
import com.example.galaxynews.databinding.FragmentHomeBinding;
import com.example.galaxynews.databinding.FragmentNoNetworkBinding;
import com.example.galaxynews.ui.fragments.main.home.HomeViewModel;

public class NoNetworkFragment extends Fragment {

    private FragmentNoNetworkBinding binding;

    private HomeViewModel viewModel;

    public NoNetworkFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNoNetworkBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        setup();

    }

    private void setup() {

        binding.tvTryAgain.setOnClickListener(view -> {
            visProgress(true);
            if (viewModel.isOnline(requireContext())) {
                Navigation.findNavController(view).popBackStack();
            } else {
                visProgress(false);
            }
        });
    }

    private void visProgress(boolean b) {

        if (b) {
            binding.progressLayout.startShimmer();
            binding.progressLayout.setVisibility(View.VISIBLE);
            binding.tvTryAgain.setVisibility(View.INVISIBLE);
        } else {
            binding.progressLayout.stopShimmer();
            binding.progressLayout.setVisibility(View.GONE);
            binding.tvTryAgain.setVisibility(View.VISIBLE);
        }
    }
}