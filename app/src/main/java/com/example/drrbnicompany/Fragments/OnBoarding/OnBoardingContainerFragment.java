package com.example.drrbnicompany.Fragments.OnBoarding;

import static com.example.drrbnicompany.Constant.SCREEN_IMG;
import static com.example.drrbnicompany.Constant.SUB_TITLE;
import static com.example.drrbnicompany.Constant.TITLE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.drrbnicompany.databinding.FragmentOnboardingContainerBinding;

public class OnBoardingContainerFragment extends Fragment {

    private FragmentOnboardingContainerBinding binding;
    private int screen_img;
    private int title;
    private int sub_title;

    public OnBoardingContainerFragment() {}


    public static OnBoardingContainerFragment newInstance(int screen_img, int title , int sub_title) {
        OnBoardingContainerFragment fragment = new OnBoardingContainerFragment();
        Bundle args = new Bundle();
        args.putInt(SCREEN_IMG, screen_img);
        args.putInt(TITLE, title);
        args.putInt(SUB_TITLE, sub_title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            screen_img = getArguments().getInt(SCREEN_IMG);
            title = getArguments().getInt(TITLE);
            sub_title = getArguments().getInt(SUB_TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOnboardingContainerBinding
                .inflate(getLayoutInflater(),container,false);

        binding.onboardingImage.setImageResource(screen_img);
        binding.onboardingTitle.setText(title);
        binding.onboardingSubtitle.setText(sub_title);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}