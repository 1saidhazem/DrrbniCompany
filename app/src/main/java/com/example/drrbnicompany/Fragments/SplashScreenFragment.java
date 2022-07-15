package com.example.drrbnicompany.Fragments;

import static com.example.drrbnicompany.Constant.SPLASH_SCREEN_TIME_OUT;
import static com.example.drrbnicompany.Constant.STATE_AUTH;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.drrbnicompany.R;
import com.example.drrbnicompany.databinding.FragmentSplashScreenBinding;
import com.google.firebase.auth.FirebaseAuth;

public class SplashScreenFragment extends Fragment {

    private FragmentSplashScreenBinding binding;
    private SharedPreferences stateAuth;
    private boolean state;

    public SplashScreenFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSplashScreenBinding.inflate
                (getLayoutInflater(), container, false);

        stateAuth = requireContext().getSharedPreferences(STATE_AUTH, Context.MODE_PRIVATE);

//        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getActivity().setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                state = stateAuth.getBoolean(STATE_AUTH, false);
                NavController navController = Navigation.findNavController(binding.getRoot());

                if (state) {
                    navController.navigate(R.id.action_splashScreenFragment_to_mainFragment);
                } else {
                    navController.navigate(R.id.action_splashScreen_to_onboarding);
                }

            }
        }, SPLASH_SCREEN_TIME_OUT);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}