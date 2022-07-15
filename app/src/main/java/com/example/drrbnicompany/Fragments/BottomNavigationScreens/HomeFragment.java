package com.example.drrbnicompany.Fragments.BottomNavigationScreens;

import static com.example.drrbnicompany.Constant.COLLECTION_JOBS;
import static com.example.drrbnicompany.Constant.MAJOR;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.drrbnicompany.Adapters.HomeAdapter;
import com.example.drrbnicompany.Fragments.Dialogs.FilterDialogFragment;
import com.example.drrbnicompany.Models.Filters;
import com.example.drrbnicompany.Models.Job;
import com.example.drrbnicompany.R;
import com.example.drrbnicompany.ViewModels.SignInViewModel;
import com.example.drrbnicompany.databinding.FragmentHomeBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

public class HomeFragment extends Fragment implements FilterDialogFragment.FilterListener
        , HomeAdapter.OnJobSelectedListener {

    private FragmentHomeBinding binding;
    private SignInViewModel signInViewModel;
    private FilterDialogFragment filter;
    private FirebaseFirestore mFirestore;
    private Query mQuery;
    private HomeAdapter homeAdapter;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding
                .inflate(inflater, container, false);

        load();
        FirebaseFirestore.setLoggingEnabled(true);
        signInViewModel = new ViewModelProvider(this).get(SignInViewModel.class);
        initFirestore();
        initRecyclerView();

        filter = new FilterDialogFragment(this);

        binding.filterBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter.show(getParentFragmentManager(), FilterDialogFragment.TAG);
            }
        });

        binding.buttonClearFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClearFilter();
                binding.buttonClearFilter.setVisibility(View.GONE);
            }
        });

        return binding.getRoot();
    }

    private void initFirestore() {
        mFirestore = FirebaseFirestore.getInstance();
        mQuery = mFirestore.collection(COLLECTION_JOBS);
    }

    private void initRecyclerView() {
        homeAdapter = new HomeAdapter(mQuery, this) {
            @Override
            protected void onDataChanged() {
                // Show/hide content if the query returns empty.
                stopLoad();
                if (getItemCount() == 0) {
                    binding.rvJobItems.setVisibility(View.GONE);
                    binding.noData.setVisibility(View.VISIBLE);
                } else {
                    binding.rvJobItems.setVisibility(View.VISIBLE);
                    binding.noData.setVisibility(View.GONE);
                }
            }

            @Override
            protected void onError(FirebaseFirestoreException e) {
                Log.d("sssssssss", e.toString());
            }

        };

        binding.rvJobItems.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvJobItems.setHasFixedSize(true);
        binding.rvJobItems.setAdapter(homeAdapter);

        homeAdapter.startListening();
    }

    @Override
    public void onFilter(Filters filters) {

        Query query = mFirestore.collection(COLLECTION_JOBS);

        if (filters.hasMajor()) {
            query = query.whereEqualTo(MAJOR, filters.getMajor());
            binding.textCurrentSortBy.setVisibility(View.VISIBLE);
            binding.textCurrentSortBy.setText(filters.getMajor());
            binding.buttonClearFilter.setVisibility(View.VISIBLE);
        }

        mQuery = query;
        homeAdapter.setQuery(query);

    }

    public void load() {
        binding.shimmerView.setVisibility(View.VISIBLE);
        binding.shimmerView.startShimmerAnimation();
        binding.homeLayout.setVisibility(View.GONE);
    }

    public void stopLoad() {
        binding.shimmerView.setVisibility(View.GONE);
        binding.shimmerView.stopShimmerAnimation();
        binding.homeLayout.setVisibility(View.VISIBLE);
    }

    public void onClearFilter() {
        filter.resetFilters();
        mQuery = mFirestore.collection(COLLECTION_JOBS);
        homeAdapter.setQuery(mQuery);
        binding.textCurrentSortBy.setVisibility(View.GONE);
    }

    @Override
    public void onJobSelected(Job job) {
        NavController navController = Navigation.findNavController(binding.getRoot());
        navController.navigate(HomeFragmentDirections
                .actionHomeFragmentToShowPostFragment(job));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.home_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.homeMenuLogout) {
            signInViewModel.SignOut(getActivity());
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStop() {
        super.onStop();
        homeAdapter.stopListening();
    }

    @Override
    public void onResume() {
        super.onResume();
        homeAdapter.startListening();
    }
}