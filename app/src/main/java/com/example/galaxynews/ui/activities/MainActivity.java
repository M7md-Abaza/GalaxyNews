package com.example.galaxynews.ui.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.galaxynews.R;
import com.example.galaxynews.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity /*implements NavController.OnDestinationChangedListener*/ {

    private ActivityMainBinding binding;
    public NavController navController;
    public NavHostFragment navHostFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_main_fragment);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();

        setup();
    }

    @SuppressLint("NonConstantResourceId")
    private void setup() {
//        navController.addOnDestinationChangedListener(this);

        binding.bottomNavigation.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.item_home:
                    navController.navigate(R.id.homeFragment);

                    break;
                case R.id.item_search:
                    navController.navigate(R.id.searchFragment);

                    break;
                case R.id.item_bookMark:
                    navController.navigate(R.id.bookMarkFragment);

                    break;
                case R.id.item_setting:
                    navController.navigate(R.id.settingFragment);

                    break;
            }

            return true;
        });

    }



    private void visNavView(Boolean s) {

        if (s) {
            binding.bottomNavigation.setVisibility(View.VISIBLE);

        } else {
            binding.bottomNavigation.setVisibility(View.GONE);

        }
    }

}