package com.example.drrbnicompany.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.bumptech.glide.Glide;
import com.example.drrbnicompany.Models.Ads;
import com.example.drrbnicompany.R;
import com.example.drrbnicompany.ViewModels.MyListener;
import com.example.drrbnicompany.ViewModels.ShowAndEditAdsViewModel;
import com.example.drrbnicompany.databinding.FragmentShowAndEditAdsBinding;


public class ShowAndEditAdsFragment extends Fragment {

    private FragmentShowAndEditAdsBinding binding;
    private ShowAndEditAdsViewModel adsViewModel;

    public ShowAndEditAdsFragment() {}

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
        loadShimmer();

        String adsId = ShowAndEditAdsFragmentArgs.fromBundle(requireArguments()).getAdsId();
//        String adsId = getArguments().getString("adsId");
        adsViewModel.getAdsById(adsId, new MyListener<Ads>() {
            @Override
            public void onValuePosted(Ads value) {
                stopLoadShimmer();
                Glide.with(requireActivity()).load(value.getImg()).placeholder(R.drawable.anim_progress).into(binding.adsImage);

                int position = 0;
                checkMajorSpinnerData(position, value);

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

    private void checkMajorSpinnerData(int position, Ads value) {
        if (value.getMajor().equals("التخصص المطلوب")){
            position = 0;
            binding.major.setSelection(position);
        } else if (value.getMajor().equals("تكنولوجيا الوسائط المتعددة")){
            position = 1;
            binding.major.setSelection(position);
        } else if (value.getMajor().equals("البرمجيات وقواعد البيانات")){
            position = 2;
            binding.major.setSelection(position);
        } else if (value.getMajor().equals("تصميم وتطوير مواقع الويب")){
            position = 3;
            binding.major.setSelection(position);
        } else if (value.getMajor().equals("أمن المعلومات السيبراني")){
            position = 4;
            binding.major.setSelection(position);
        } else if (value.getMajor().equals("علم البيانات والذكاء الإصطناعي")){
            position = 5;
            binding.major.setSelection(position);
        } else if (value.getMajor().equals("شبكات الحاسوب والإنترنت")){
            position = 6;
            binding.major.setSelection(position);
        } else if (value.getMajor().equals("الهندسة المعمارية")){
            position = 7;
            binding.major.setSelection(position);
        } else if (value.getMajor().equals("الهندسة المدنية")){
            position = 8;
            binding.major.setSelection(position);
        } else if (value.getMajor().equals("هندسة المساحة")){
            position = 9;
            binding.major.setSelection(position);
        } else if (value.getMajor().equals("هندسة التشييد وإدارة المشاريع")){
            position = 10;
            binding.major.setSelection(position);
        } else if (value.getMajor().equals("نظم المعلومات الجغرافية")){
            position = 11;
            binding.major.setSelection(position);
        } else if (value.getMajor().equals("المحاسبة")){
            position = 12;
            binding.major.setSelection(position);
        } else if (value.getMajor().equals("إداراة أتمتة المكاتب")){
            position = 13;
            binding.major.setSelection(position);
        } else if (value.getMajor().equals("التسويق الإلكتروني")){
            position = 14;
            binding.major.setSelection(position);
        } else if (value.getMajor().equals("الإعلام الرقمي")){
            position = 15;
            binding.major.setSelection(position);
        } else if (value.getMajor().equals("العلاقات العامة والإعلان")){
            position = 16;
            binding.major.setSelection(position);
        }
    }

    public void loadShimmer() {
        binding.shimmerView.setVisibility(View.VISIBLE);
        binding.shimmerView.startShimmerAnimation();
        binding.layoutShowAndEditAds.setVisibility(View.GONE);
    }

    public void stopLoadShimmer() {
        binding.shimmerView.setVisibility(View.GONE);
        binding.shimmerView.stopShimmerAnimation();
        binding.layoutShowAndEditAds.setVisibility(View.VISIBLE);
    }

}