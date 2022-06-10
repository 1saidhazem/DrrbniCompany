package com.example.drrbnicompany.Fragments.OnBoarding;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.drrbnicompany.Adapters.PagerFragmentsAdapter;
import com.example.drrbnicompany.R;
import com.example.drrbnicompany.databinding.FragmentOnboardingBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class OnBoardingFragment extends Fragment {

    private FragmentOnboardingBinding binding ;
    private int position = 0;
    private Animation btnAnimation;
    public OnBoardingFragment() {}

    public static OnBoardingFragment newInstance() {
        OnBoardingFragment fragment = new OnBoardingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (restorePrefData()){
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_onboarding_to_login);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         binding = FragmentOnboardingBinding.inflate(getLayoutInflater());

        ArrayList<Fragment> fragments = new ArrayList<>();

        fragments.add(OnBoardingContainerFragment.newInstance(R.drawable.onboarding1 ,
                R.string.onboarding1_title , R.string.onboarding1_subtitle ));

        fragments.add(OnBoardingContainerFragment.newInstance(R.drawable.onboarding2 ,
                R.string.onboarding2_title , R.string.onboarding2_subtitle ));

        fragments.add(OnBoardingContainerFragment.newInstance(R.drawable.onboarding3 ,
                R.string.onboarding3_title , R.string.onboarding3_subtitle ));

        fragments.add(OnBoardingContainerFragment.newInstance(R.drawable.onboarding1 ,
                R.string.onboarding4_title , R.string.onboarding4_subtitle ));

        PagerFragmentsAdapter pagerFragmentsAdapter = new PagerFragmentsAdapter(requireActivity() , fragments);

        binding.onboardingSlider.setAdapter(pagerFragmentsAdapter);

        new TabLayoutMediator(binding.dotSlider, binding.onboardingSlider, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

            }
        }).attach();

        btnAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.button_animation);

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position = binding.onboardingSlider.getCurrentItem();
                if (position < fragments.size()) {
                    position++;
                    binding.onboardingSlider.setCurrentItem(position);
                }
                if (position == fragments.size()-1) {
                    loadedLastScreen();
                }
            }
        });


        binding.getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(binding.getRoot());
                navController.navigate(R.id.action_onboarding_to_login);
                savePrefsData();
            }
        });

        binding.dotSlider.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == fragments.size()-1) {
                    loadedLastScreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private Boolean restorePrefData() {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(requireContext());
        Boolean isOpened = sharedPreferences.getBoolean("isIntroOpened", false);

        return isOpened;
    }

    private void savePrefsData() {
        SharedPreferences.Editor sharedPreferencesEditor =
                PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
        sharedPreferencesEditor.putBoolean("isIntroOpened", true);
        sharedPreferencesEditor.apply();
    }

    private void loadedLastScreen() {
        binding.btnNext.setVisibility(View.INVISIBLE);
        binding.getStarted.setVisibility(View.VISIBLE);
        binding.dotSlider.setVisibility(View.INVISIBLE);
        binding.getStarted.setAnimation(btnAnimation);
    }

}