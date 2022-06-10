package com.example.drrbnicompany.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.drrbnicompany.databinding.FragmentShowPostBinding;

public class ShowPostFragment extends Fragment {

    private FragmentShowPostBinding binding;
    public ShowPostFragment() {}

    public static ShowPostFragment newInstance() {
        ShowPostFragment fragment = new ShowPostFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentShowPostBinding
                .inflate(getLayoutInflater(),container,false);


        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}