package com.example.galaxynews.ui.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.galaxynews.R;
import com.example.galaxynews.databinding.ActivityMainBinding;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements NavController.OnDestinationChangedListener {

    private ActivityMainBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        NavHostFragment navHostFragment =
                (NavHostFragment) this.getSupportFragmentManager().findFragmentById(R.id.nav_host_main_fragment);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();

        setup();
    }

    private void setup() {
        navController.addOnDestinationChangedListener(this);

        binding.bottomNavigation.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.item_home) {
                navController.navigate(R.id.homeFragment);

            } else if (item.getItemId() == R.id.item_search) {
                navController.navigate(R.id.searchFragment);

            } else if (item.getItemId() == R.id.item_bookMark) {
                navController.navigate(R.id.bookMarkFragment);

            } else if (item.getItemId() == R.id.item_setting) {
                navController.navigate(R.id.settingFragment);

            }

            return true;
        });

    }

    @Override
    public void onDestinationChanged(
            @NonNull NavController navController,
            @NonNull NavDestination navDestination,
            @Nullable Bundle bundle) {

        if (navDestination.getId() == R.id.homeFragment) {

            if (Objects.requireNonNull(navController.getCurrentDestination()).getId() != R.id.homeFragment)
                binding.bottomNavigation.setSelectedItemId(R.id.item_home);

            visNavView(true);
        } else if (navDestination.getId() == R.id.searchFragment) {
            if (Objects.requireNonNull(navController.getCurrentDestination()).getId() != R.id.searchFragment)
                binding.bottomNavigation.setSelectedItemId(R.id.item_search);

            visNavView(true);
        } else if (navDestination.getId() == R.id.bookMarkFragment) {
            if (Objects.requireNonNull(navController.getCurrentDestination()).getId() != R.id.bookMarkFragment)
                binding.bottomNavigation.setSelectedItemId(R.id.item_bookMark);

            visNavView(true);
        } else if (navDestination.getId() == R.id.settingFragment) {
            if (Objects.requireNonNull(navController.getCurrentDestination()).getId() != R.id.settingFragment)
                binding.bottomNavigation.setSelectedItemId(R.id.item_setting);

            visNavView(true);
        } else {
            visNavView(false);
        }

    }


    private void visNavView(Boolean s) {

        if (s) {
            binding.bottomNavigation.setVisibility(View.VISIBLE);

        } else {
            binding.bottomNavigation.setVisibility(View.GONE);

        }
    }

    private void visibleProgress(Boolean state) {

        if (state) {
//            binding.progressLayout.setVisibility(View.VISIBLE);
        } else {
//            binding.progressLayout.setVisibility(View.GONE);
        }
    }
}