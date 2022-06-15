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
import com.example.drrbnicompany.Fragments.Dialogs.FilterDialogFragment;
import com.example.drrbnicompany.Models.Filters;
import com.example.drrbnicompany.R;
import com.example.drrbnicompany.ViewModels.HomeViewModel;
import com.example.drrbnicompany.ViewModels.MyListener;
import com.example.drrbnicompany.databinding.FragmentHomeBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;
    private FirebaseAuth auth;
    private FilterDialogFragment filterDialog;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        auth = FirebaseAuth.getInstance();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding
                .inflate(inflater, container, false);

        try {
            homeViewModel.getStateActiveAccount(auth.getCurrentUser().getEmail(), new MyListener<Boolean>() {
                @Override
                public void onValuePosted(Boolean value) {
                    if (value) return;
                    Snackbar.make(container, "انتهت الجلسة", Snackbar.LENGTH_LONG).show();
                    homeViewModel.signOut();
//                    NavController navController = Navigation.findNavController(binding.getRoot());
//                    navController.navigate(R.id.action_mainFragment_to_loginFragment);
                }
            });
        } catch (Exception e) {
            Log.e("ttt", e.getMessage());
        }


        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void onFilterClicked() {
        filterDialog.show(getParentFragmentManager(), FilterDialogFragment.TAG);
    }

    public void onClearFilterClicked() {
        filterDialog.resetFilters();

        onFilter(Filters.getDefault());
    }

    public void onFilter(Filters filters) {
        /*
        // Construct query basic query
        Query query = mFirestore.collection("restaurants");

        // Category (equality filter)
        if (filters.hasCategory()) {
            query = query.whereEqualTo("category", filters.getCategory());
        }

        // City (equality filter)
        if (filters.hasCity()) {
            query = query.whereEqualTo("city", filters.getCity());
        }

        // Price (equality filter)
        if (filters.hasPrice()) {
            query = query.whereEqualTo("price", filters.getPrice());
        }

        // Sort by (orderBy with direction)
        if (filters.hasSortBy()) {
            query = query.orderBy(filters.getSortBy(), filters.getSortDirection());
        }

        // Limit items
        query = query.limit(LIMIT);

        // Update the query
        mQuery = query;
        mAdapter.setQuery(query);

        // Set header
        mCurrentSearchView.setText(Html.fromHtml(filters.getSearchDescription(this)));
        mCurrentSortByView.setText(filters.getOrderDescription(this));

        // Save filters
        // mViewModel.setFilters(filters);
         */
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.filter_bar_container:
                onFilterClicked();
//                onSearchClicked();
                break;
            case R.id.button_clear_filter:
//                onCancelClicked();
                onClearFilterClicked();
                break;
        }
    }

    public void onSearchClicked() {
        /*
        if (mFilterListener != null) {
            mFilterListener.onFilter(getFilters());
        }

        dismiss();
         */
    }

    public void onCancelClicked() {
//        dismiss();
    }

}