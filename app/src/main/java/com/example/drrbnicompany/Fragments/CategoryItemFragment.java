package com.example.drrbnicompany.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.drrbnicompany.databinding.FragmentCategoryItemsBinding;

public class CategoryItemFragment extends Fragment {

    private FragmentCategoryItemsBinding binding;
    public CategoryItemFragment() {}

    public static CategoryItemFragment newInstance() {
        return new CategoryItemFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCategoryItemsBinding
                .inflate(getLayoutInflater(),container,false);

     //   String categoryId = CategoryItemFragmentArgs.fromBundle(requireArguments()).getCategoryId();
      //  String name = CategoryItemFragmentArgs.fromBundle(requireArguments()).getName();



       // binding.title.setText(name);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}