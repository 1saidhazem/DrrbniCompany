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
import com.example.drrbnicompany.ViewModels.EditContactInformationViewModel;
import com.example.drrbnicompany.ViewModels.MyListener;
import com.example.drrbnicompany.ViewModels.ProfileViewModel;
import com.example.drrbnicompany.databinding.FragmentEditContactInformationBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class EditContactInformationFragment extends Fragment {

    private FragmentEditContactInformationBinding binding;
    private FirebaseAuth auth;
    private EditContactInformationViewModel contactInformationViewModel;
    private Company thiCompany;

    public EditContactInformationFragment() {}

    public static EditContactInformationFragment newInstance() {
        return new EditContactInformationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        contactInformationViewModel = new ViewModelProvider(this).get(EditContactInformationViewModel.class);
        contactInformationViewModel.requestProfileInfo(auth.getCurrentUser().getUid());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditContactInformationBinding
                .inflate(getLayoutInflater(),container,false);

        load();

        contactInformationViewModel.getProfileInfo().observe(requireActivity(), new Observer<Company>() {
            @Override
            public void onChanged(Company company) {
                if (getActivity() == null) return;
                thiCompany = company;
                binding.etWhatsapp.setText(company.getWhatsApp());
                stopLoad();
            }
        });

        binding.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();

                String whatsapp = binding.etWhatsapp.getText().toString().trim();

                if (TextUtils.isEmpty(whatsapp))
                    whatsapp = thiCompany.getWhatsApp();

                contactInformationViewModel.editContactInformation(whatsapp, new MyListener<Boolean>() {
                    @Override
                    public void onValuePosted(Boolean value) {
                        if (value = true)
                            Snackbar.make(view , "تم التعديل بنجاح" , Snackbar.LENGTH_LONG).show();

                        stopUpdate();
                    }
                }, new MyListener<Boolean>() {
                    @Override
                    public void onValuePosted(Boolean value) {
                        if (value = true)
                            Snackbar.make(view , "فشل التعديل" , Snackbar.LENGTH_LONG).show();

                        stopUpdate();
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
        binding.editProfileContactInformationLayout.setVisibility(View.GONE);
    }

    public void stopLoad() {
        binding.shimmerView.setVisibility(View.GONE);
        binding.shimmerView.stopShimmerAnimation();
        binding.editProfileContactInformationLayout.setVisibility(View.VISIBLE);
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