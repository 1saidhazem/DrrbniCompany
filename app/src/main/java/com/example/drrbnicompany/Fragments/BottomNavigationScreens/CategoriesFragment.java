package com.example.drrbnicompany.Fragments.BottomNavigationScreens;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.drrbnicompany.Adapters.CategoryAdapter;
import com.example.drrbnicompany.Models.Category;
import com.example.drrbnicompany.ViewModels.CategoryViewModel;
import com.example.drrbnicompany.ViewModels.MyListener;
import com.example.drrbnicompany.databinding.FragmentCategoriesBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class CategoriesFragment extends Fragment {

    private FragmentCategoriesBinding binding;
    private FirebaseAuth auth;
    private CategoryAdapter adapter;
    private CategoryViewModel categoryViewHolder;

    public CategoriesFragment() {
    }

    public static CategoriesFragment newInstance() {
        return new CategoriesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryViewHolder = new ViewModelProvider(this).get(CategoryViewModel.class);
        auth = FirebaseAuth.getInstance();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCategoriesBinding
                .inflate(inflater, container, false);

        load();

        // Check state active account
        try {
            categoryViewHolder.getStateActiveAccount(auth.getCurrentUser().getEmail(), new MyListener<Boolean>() {
                @Override
                public void onValuePosted(Boolean value) {
                    if (value) return;
                    Snackbar.make(container, "انتهت الجلسة", Snackbar.LENGTH_LONG).show();
                    categoryViewHolder.signOut();
//                    NavController navController = Navigation.findNavController(binding.getRoot());
//                    navController.navigate(R.id.action_mainFragment_to_loginFragment);
                }
            });
        }
        catch (Exception e) {
            Log.e("ttt", e.getMessage());
        }


        categoryViewHolder.getCategories(new MyListener<List<Category>>() {
            @Override
            public void onValuePosted(List<Category> values) {
                if (getActivity() == null) return;
                adapter = new CategoryAdapter(values, new MyListener<Integer>() {
                    @Override
                    public void onValuePosted(Integer value) {
                        NavController navController = Navigation.findNavController(binding.getRoot());
                        navController.navigate(CategoriesFragmentDirections
                                .actionCategoriesFragmentToCategoryItemFragment
                                        (values.get(value).getCategory_Id(), values.get(value).getName()));
                    }
                });
                stopLoad();
                initRV();
            }
        });


        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    void initRV() {
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getActivity());
        binding.rvCategories.setLayoutManager(lm);
        binding.rvCategories.setHasFixedSize(true);
        binding.rvCategories.setAdapter(adapter);
    }

    public void load() {
        binding.shimmerView.setVisibility(View.VISIBLE);
        binding.shimmerView.startShimmerAnimation();
        binding.linear.setVisibility(View.GONE);
    }

    public void stopLoad() {
        binding.shimmerView.setVisibility(View.GONE);
        binding.shimmerView.stopShimmerAnimation();
        binding.linear.setVisibility(View.VISIBLE);
    }

}