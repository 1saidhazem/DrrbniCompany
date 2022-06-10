package com.example.drrbnicompany;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.drrbnicompany.Fragments.BottomNavigationScreens.CategoriesFragment;
import com.example.drrbnicompany.Fragments.BottomNavigationScreens.HomeFragment;
import com.example.drrbnicompany.Fragments.BottomNavigationScreens.NotificationsFragment;
import com.example.drrbnicompany.Fragments.BottomNavigationScreens.ProfileFragment;
import com.example.drrbnicompany.databinding.FragmentMainBinding;

import java.util.ArrayList;

public class MainFragment extends Fragment {

    public MainFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentMainBinding binding = FragmentMainBinding.inflate(getLayoutInflater());

        ArrayList<Fragment> list = new ArrayList<>();
        list.add(HomeFragment.newInstance());
        list.add(CategoriesFragment.newInstance());
        list.add(NotificationsFragment.newInstance());
        list.add(ProfileFragment.newInstance());

        NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.mainFragmentContainerView);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.navView, navController);

        return binding.getRoot();
    }
}