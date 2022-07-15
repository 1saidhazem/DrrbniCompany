package com.example.drrbnicompany.Fragments.EditProfile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.drrbnicompany.Models.Company;
import com.example.drrbnicompany.ViewModels.ChangePasswordViewModel;
import com.example.drrbnicompany.ViewModels.EditProfileViewModel;
import com.example.drrbnicompany.ViewModels.MyListener;
import com.example.drrbnicompany.databinding.FragmentEditEmailBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class EditEmailFragment extends Fragment {

    private FragmentEditEmailBinding binding;
    private FirebaseAuth auth;
    private String Email;
    private EditProfileViewModel editProfileViewModel;


    public EditEmailFragment() {
    }


    public static EditEmailFragment newInstance() {
        return new EditEmailFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditEmailBinding.inflate(getLayoutInflater(), container, false);

        load();
        editProfileViewModel = new ViewModelProvider(this).get(EditProfileViewModel.class);
        auth = FirebaseAuth.getInstance();


        editProfileViewModel.getEmail(auth.getCurrentUser().getUid(), new MyListener<String>() {
            @Override
            public void onValuePosted(String value) {
                Email = value;
                binding.etEmail.setText(Email);
                stopLoad();
            }
        }, new MyListener<Boolean>() {
            @Override
            public void onValuePosted(Boolean value) {

            }
        });

        binding.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentPassword = binding.currentPassword.getText().toString().trim();
                String email = binding.etEmail.getText().toString().trim();

                if (TextUtils.isEmpty(email))
                    email = Email;

                editProfileViewModel.changeEmail(currentPassword, email, auth.getCurrentUser().getUid(), new MyListener<Boolean>() {
                    @Override
                    public void onValuePosted(Boolean value) {
                        if (value = true)
                            Snackbar.make(view, "تم التعديل بنجاح", Snackbar.LENGTH_LONG).show();

                    }
                }, new MyListener<String>() {
                    @Override
                    public void onValuePosted(String value) {
                        if (value != null)
                            Snackbar.make(view , "أدخل الكلمة المرور بشكل صحيح" , Snackbar.LENGTH_LONG).show();
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
        binding.editEmailLayout.setVisibility(View.GONE);
    }

    public void stopLoad() {
        binding.shimmerView.setVisibility(View.GONE);
        binding.shimmerView.stopShimmerAnimation();
        binding.editEmailLayout.setVisibility(View.VISIBLE);
    }

}