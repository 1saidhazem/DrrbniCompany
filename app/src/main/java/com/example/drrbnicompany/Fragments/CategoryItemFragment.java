package com.example.drrbnicompany.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drrbnicompany.Adapters.CategoryStudentAdapter;
import com.example.drrbnicompany.Fragments.BottomNavigationScreens.ProfileFragmentDirections;
import com.example.drrbnicompany.Models.Student;
import com.example.drrbnicompany.ViewModels.CategoryViewModel;
import com.example.drrbnicompany.ViewModels.MyListener;
import com.example.drrbnicompany.databinding.FragmentCategoryItemsBinding;

import java.util.List;

public class CategoryItemFragment extends Fragment {

    private FragmentCategoryItemsBinding binding;
    private CategoryStudentAdapter adapter;
    private CategoryViewModel categoryViewModel;
    public CategoryItemFragment() {}

    public static CategoryItemFragment newInstance() {
        return new CategoryItemFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCategoryItemsBinding
                .inflate(getLayoutInflater(),container,false);

        load();

        String categoryId = CategoryItemFragmentArgs.fromBundle(requireArguments()).getCategoryId();
        String major = CategoryItemFragmentArgs.fromBundle(requireArguments()).getName();
        binding.title.setText(major);


        categoryViewModel.getStudentsByMajor(major,new MyListener<List<Student>>() {
            @Override
            public void onValuePosted(List<Student> value) {
                stopLoad();
                adapter = new CategoryStudentAdapter(value, new MyListener<String>() {
                    @Override
                    public void onValuePosted(String value) {
                        NavController navController = Navigation.findNavController(binding.getRoot());
                        navController.navigate(CategoryItemFragmentDirections
                                .actionCategoryItemFragmentToStudentProfileFragment(value));
                    }
                });
                initRV();
            }
        }, new MyListener<String>() {
            @Override
            public void onValuePosted(String value) {

            }
        });



        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    void initRV(){
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getActivity());
        binding.rvCategoryItems.setLayoutManager(lm);
        binding.rvCategoryItems.setHasFixedSize(true);
        binding.rvCategoryItems.setAdapter(adapter);
    }

    public void load() {
        binding.shimmerView.setVisibility(View.VISIBLE);
        binding.shimmerView.startShimmerAnimation();
        binding.rvCategoryItems.setVisibility(View.GONE);
    }

    public void stopLoad() {
        binding.shimmerView.setVisibility(View.GONE);
        binding.shimmerView.stopShimmerAnimation();
        binding.rvCategoryItems.setVisibility(View.VISIBLE);
    }

}