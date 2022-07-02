package com.example.drrbnicompany.Fragments.BottomNavigationScreens;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.drrbnicompany.Adapters.AdsAdapter;
import com.example.drrbnicompany.Models.Ads;
import com.example.drrbnicompany.Models.Company;
import com.example.drrbnicompany.R;
import com.example.drrbnicompany.ViewModels.ProfileViewModel;
import com.example.drrbnicompany.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private FirebaseAuth auth;
    private ProfileViewModel profileViewModel;
    private AdsAdapter adsAdapter;

    public ProfileFragment() {
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding
                .inflate(getLayoutInflater(), container, false);

        load();

        auth = FirebaseAuth.getInstance();
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        profileViewModel.requestProfileInfo(auth.getCurrentUser().getUid());
        profileViewModel.requestAdsJobs(auth.getCurrentUser().getUid());

        profileViewModel.getProfileInfo().observe(requireActivity(), new Observer<Company>() {
            @Override
            public void onChanged(Company company) {
                if (getActivity() == null)
                    return;

                if (company.getImg() == null) {
                    binding.appBarImage.setImageResource(R.drawable.company_default_image);
                } else {
                    Glide.with(getActivity()).load(company.getImg()).placeholder(R.drawable.anim_progress).into(binding.appBarImage);
                }
                binding.companyName.setText(company.getName());
                binding.companyEmail.setText(company.getEmail());
                binding.companyWhatsapp.setText(company.getWhatsApp());
                if (company.isVerified())
                    binding.verified.setVisibility(View.VISIBLE);
                binding.address.setText(company.getGovernorate() + " _ " +company.getAddress());
                stopLoad();

                binding.companyEmail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                        Uri email = Uri.fromParts("mailto" , company.getEmail() , null);
                        intent.setData(email);
                        startActivity(intent);
                    }
                });

                binding.companyWhatsapp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String url = "https://api.whatsapp.com/send?phone="+company.getWhatsApp();
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                    }
                });
            }
        });

        profileViewModel.getAdsData().observe(requireActivity(), new Observer<List<Ads>>() {
            @Override
            public void onChanged(List<Ads> ads) {
                if (getActivity() == null) return;
                adsAdapter = new AdsAdapter(ads,profileViewModel,auth.getCurrentUser().getUid() );
                initRV();
            }
        });

        binding.companyBtnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(binding.getRoot());
                navController.navigate(R.id.action_profileFragment_to_editProfileFragment);
            }
        });

        binding.addAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(binding.getRoot());
                navController.navigate(R.id.action_profileFragment_to_addAdsFragment);
            }
        });


        return binding.getRoot();
    }

    public void load() {
        binding.shimmerView.setVisibility(View.VISIBLE);
        binding.shimmerView.startShimmerAnimation();
        binding.profileLayout.setVisibility(View.GONE);
    }

    public void stopLoad() {
        binding.shimmerView.setVisibility(View.GONE);
        binding.shimmerView.stopShimmerAnimation();
        binding.profileLayout.setVisibility(View.VISIBLE);
    }

    void initRV(){
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getActivity());
        binding.rvAds.setLayoutManager(lm);
        binding.rvAds.setHasFixedSize(true);
        binding.rvAds.setAdapter(adsAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}