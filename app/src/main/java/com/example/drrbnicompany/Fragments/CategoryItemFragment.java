package com.example.drrbnicompany.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.drrbnicompany.Adapters.StudentAdapter;
import com.example.drrbnicompany.Models.Student;
import com.example.drrbnicompany.ViewModels.CategoryItemViewModel;
import com.example.drrbnicompany.ViewModels.MyListener;
import com.example.drrbnicompany.databinding.FragmentCategoryItemsBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class CategoryItemFragment extends Fragment {

    private FragmentCategoryItemsBinding binding;
    private StudentAdapter studentAdapter;
    private CategoryItemViewModel categoryItemViewModel;

    public CategoryItemFragment() {}

    public static CategoryItemFragment newInstance() {
        return new CategoryItemFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCategoryItemsBinding
                .inflate(getLayoutInflater(),container,false);


        String majorName = getArguments().getString("majorName").trim();
        binding.title.setText(majorName);

        categoryItemViewModel = new ViewModelProvider(this).get(CategoryItemViewModel.class);

        load();
        categoryItemViewModel.getAllStudentByMajor(majorName, new MyListener<List<Student>>() {
            @Override
            public void onValuePosted(List<Student> value) {
                if (getActivity() == null) return;
                stopLoad();
                if (value.isEmpty()) {
                    binding.emptyImg.setVisibility(View.VISIBLE);
                    binding.rvCategoryItems.setVisibility(View.GONE);
                }else {
                    studentAdapter = new StudentAdapter(value, new MyListener<String>() {
                        @Override
                        public void onValuePosted(String value) {
                            NavController navController = Navigation.findNavController(binding.getRoot());
                            navController.navigate(CategoryItemFragmentDirections
                                    .actionCategoryItemFragmentToStudentProfileFragment(value));
                        }
                    });
                    initRV();
                }
            }
        }, new MyListener<Boolean>() {
            @Override
            public void onValuePosted(Boolean value) {
                if (value){
                    stopLoad();
                    Snackbar.make(requireView() , "فشل التحميل" , Snackbar.LENGTH_LONG).show();
                }

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
        binding.rvCategoryItems.setAdapter(studentAdapter);
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