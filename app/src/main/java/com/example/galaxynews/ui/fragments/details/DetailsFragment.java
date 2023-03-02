package com.example.galaxynews.ui.fragments.details;

import android.annotation.SuppressLint;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.galaxynews.R;
import com.example.galaxynews.databinding.FragmentDetailsBinding;
import com.example.galaxynews.pojo.Article;
import com.squareup.picasso.Picasso;


public class DetailsFragment extends Fragment {

    FragmentDetailsBinding binding;

    private Article newsDetails;

    Boolean isBookMark = false;

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setup();
    }

    private void setup() {

        retrieveNewsData();

        binding.ivBack.setOnClickListener(view -> Navigation.findNavController(view).popBackStack());

        binding.ivBookMark.setOnClickListener(view -> bookMarkToggle());

        binding.ivShare.setOnClickListener(view -> shareNews());
    }

    private void shareNews() {
        String txtShare = "Check out this news";
        String shareLink = newsDetails.getUrl();

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, txtShare + "\n" + shareLink);
        startActivity(share);

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void bookMarkToggle() {
        if (isBookMark) {
            binding.ivBookMark.setImageDrawable(getResources().getDrawable(R.drawable.ic_bookmark_white, null));
            isBookMark = false;
        } else {
            binding.ivBookMark.setImageDrawable(getResources().getDrawable(R.drawable.ic_bookmark_yellow, null));
            isBookMark = true;
        }
    }

    private void retrieveNewsData() {
        newsDetails = DetailsFragmentArgs.fromBundle(requireArguments()).getNewsDetailsArgs();

//        newsDetails = requireArguments().getParcelable("para");

        binding.tvTitle.setText(newsDetails.getTitle());
        binding.tvDetails.setText(newsDetails.getContent());

        String[] data = newsDetails.getPublishedAt().split("T");
        binding.tvTime.setText(data[data.length - 2]);

        String imageUrl = newsDetails.getUrlToImage();
        Picasso.get().load(imageUrl).into(binding.imageDetails);
    }
}