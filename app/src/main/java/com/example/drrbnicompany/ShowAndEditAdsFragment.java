package com.example.drrbnicompany;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.drrbnicompany.Models.Ads;
import com.example.drrbnicompany.ViewModels.MyListener;
import com.example.drrbnicompany.ViewModels.ShowAndEditAdsViewModel;
import com.example.drrbnicompany.databinding.FragmentShowAndEditAdsBinding;


public class ShowAndEditAdsFragment extends Fragment {

    private FragmentShowAndEditAdsBinding binding;
    private ShowAndEditAdsViewModel adsViewModel;

    public ShowAndEditAdsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adsViewModel = new ViewModelProvider(this).get(ShowAndEditAdsViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentShowAndEditAdsBinding
                .inflate(getLayoutInflater(), container, false);

        String adsId = getArguments().getString("adsId");
        adsViewModel.getAdsById(adsId, new MyListener<Ads>() {
            @Override
            public void onValuePosted(Ads value) {
                Glide.with(requireActivity()).load(value.getImg()).placeholder(R.drawable.anim_progress).into(binding.adsImage);
                binding.adsTitle.setText(value.getAdsTitle());
                binding.adsDescription.setText(value.getAdsDescription());
                binding.requirements.setText(value.getAdsRequirements());
            }
        }, new MyListener<Boolean>() {
            @Override
            public void onValuePosted(Boolean value) {

            }
        });
        return binding.getRoot();
    }
}