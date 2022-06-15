package com.example.drrbnicompany.Fragments.EditProfile;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.drrbnicompany.Models.Company;
import com.example.drrbnicompany.ViewModels.EditAddressViewModel;
import com.example.drrbnicompany.ViewModels.MyListener;
import com.example.drrbnicompany.ViewModels.ProfileViewModel;
import com.example.drrbnicompany.databinding.FragmentEditAddressBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class EditAddressFragment extends Fragment {

    private FragmentEditAddressBinding binding;
    private FirebaseAuth auth;
    private EditAddressViewModel editAddressViewModel;
    private Company thisCompany;

    public EditAddressFragment() {
    }

    public static EditAddressFragment newInstance() {
        return new EditAddressFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        editAddressViewModel = new ViewModelProvider(this).get(EditAddressViewModel.class);
        editAddressViewModel.requestProfileInfo(auth.getCurrentUser().getUid());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditAddressBinding
                .inflate(getLayoutInflater(), container, false);

        load();

        editAddressViewModel.getProfileInfo().observe(requireActivity(), new Observer<Company>() {
            @Override
            public void onChanged(Company company) {
                if (getActivity() == null) return;
                thisCompany = company;
                int position;
                if (company.getGovernorate().equals("أختر المحافظة")) {
                    position = 0;
                    binding.spGovernorate.setSelection(position);
                } else if (company.getGovernorate().equals("شمال غزة")) {
                    position = 1;
                    binding.spGovernorate.setSelection(position);
                } else if (company.getGovernorate().equals("غزة")) {
                    position = 2;
                    binding.spGovernorate.setSelection(position);
                } else if (company.getGovernorate().equals("الوسطى")) {
                    position = 3;
                    binding.spGovernorate.setSelection(position);
                } else if (company.getGovernorate().equals("خانيونس")) {
                    position = 4;
                    binding.spGovernorate.setSelection(position);
                } else if (company.getGovernorate().equals("رفح")) {
                    position = 5;
                    binding.spGovernorate.setSelection(position);
                }

                binding.etAddress.setText(company.getAddress());
                stopLoad();
            }
        });

        binding.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
                String governorate = (String) binding.spGovernorate.getSelectedItem();
                String address = binding.etAddress.getText().toString().trim();

                if (TextUtils.isEmpty(governorate))
                    governorate = thisCompany.getGovernorate();
                else if (TextUtils.isEmpty(address))
                    address = thisCompany.getAddress();

                editAddressViewModel.EditAddress(governorate, address, new MyListener<Boolean>() {
                    @Override
                    public void onValuePosted(Boolean value) {
                        if (value) {
                            stopUpdate();
                            requireActivity().getSupportFragmentManager().popBackStack();
                            Snackbar.make(view, "تم التعديل بنجاح", Snackbar.LENGTH_LONG).show();
                        }
                    }
                }, new MyListener<Boolean>() {
                    @Override
                    public void onValuePosted(Boolean value) {
                        if (value) {
                            stopUpdate();
                            requireActivity().getSupportFragmentManager().popBackStack();
                            Snackbar.make(view, "فشل التعديل", Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    public void load() {
        binding.shimmerView.setVisibility(View.VISIBLE);
        binding.shimmerView.startShimmerAnimation();
        binding.editProfileAddressLayout.setVisibility(View.GONE);
    }

    public void stopLoad() {
        binding.shimmerView.setVisibility(View.GONE);
        binding.shimmerView.stopShimmerAnimation();
        binding.editProfileAddressLayout.setVisibility(View.VISIBLE);
    }

    public void update() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.btnOk.setEnabled(false);
        binding.btnOk.setClickable(false);
    }

    public void stopUpdate() {
        binding.progressBar.setVisibility(View.GONE);
        binding.btnOk.setEnabled(true);
        binding.btnOk.setClickable(true);
    }
}