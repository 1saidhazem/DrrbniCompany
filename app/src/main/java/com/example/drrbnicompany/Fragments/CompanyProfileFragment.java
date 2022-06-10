package com.example.drrbnicompany.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.drrbnicompany.databinding.FragmentCompanyProfileBinding;


public class CompanyProfileFragment extends Fragment {

    private FragmentCompanyProfileBinding binding;
    public CompanyProfileFragment() {}

    public static CompanyProfileFragment newInstance() {
        CompanyProfileFragment fragment = new CompanyProfileFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCompanyProfileBinding
                .inflate(getLayoutInflater(),container,false);


        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}