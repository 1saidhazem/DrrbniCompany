package com.example.drrbnicompany.Fragments;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.drrbnicompany.ViewModels.MyListener;
import com.example.drrbnicompany.ViewModels.ProfileViewModel;
import com.example.drrbnicompany.databinding.FragmentAddAdsBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class AddAdsFragment extends Fragment {

    private FragmentAddAdsBinding binding;
    private FirebaseAuth auth;
    private ProfileViewModel profileViewModel;
    private ActivityResultLauncher<String> getImg;
    private ActivityResultLauncher<String> permission;
    private Uri image;

    public AddAdsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        getImg = registerForActivityResult(
                new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        if (result != null) {
                            binding.adsImage.setImageURI(result);
                            image = result;
                        }
                    }
                });

        permission = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
                    @Override
                    public void onActivityResult(Boolean result) {
                        if (result)
                            getImg.launch("image/*");
                    }
                }
        );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddAdsBinding
                .inflate(getLayoutInflater(), container, false);

        binding.adsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permission.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        });

        binding.btnAddAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load();

                String adsName = binding.adsTitle.getText().toString().trim();
                String major = binding.major.getSelectedItem().toString();
                String adsRequirements = binding.requirements.getText().toString().trim();
                String adsDescription = binding.adsDescription.getText().toString().trim();

                if (image == null) {
                    stopLoad();
                    Snackbar.make(view, "أختر صورة", Snackbar.LENGTH_LONG).show();
                    return;
                } else if (TextUtils.isEmpty(adsName)) {
                    stopLoad();
                    Snackbar.make(view, "أدخل عنوان الإعلان", Snackbar.LENGTH_LONG).show();
                    return;
                } else if (binding.major.getSelectedItemPosition() < 1) {
                    stopLoad();
                    Snackbar.make(view, "أدخل التخصص المطلوب", Snackbar.LENGTH_LONG).show();
                    return;
                } else if (TextUtils.isEmpty(adsDescription)) {
                    stopLoad();
                    Snackbar.make(view, "أدخل وصف الإعلان", Snackbar.LENGTH_LONG).show();
                    return;
                } else if (TextUtils.isEmpty(adsRequirements)) {
                    stopLoad();
                    Snackbar.make(view, "أدخل متطلبات التدريب", Snackbar.LENGTH_LONG).show();
                    return;
                }

                profileViewModel.storeAdsData(auth.getCurrentUser().getUid(), image, adsName, major,
                        adsRequirements, adsDescription, new MyListener<Boolean>() {
                            @Override
                            public void onValuePosted(Boolean value) {
                                if (value) {
                                    stopLoad();
                                    requireActivity().getSupportFragmentManager().popBackStack();
                                }
                            }
                        }, new MyListener<Boolean>() {
                            @Override
                            public void onValuePosted(Boolean value) {
                                if (value) {
                                    stopLoad();
                                    Snackbar.make(view, "فشل التحميل", Snackbar.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });


        return binding.getRoot();
    }

    public void load() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.btnAddAds.setEnabled(false);
        binding.btnAddAds.setClickable(false);
    }

    public void stopLoad() {
        binding.progressBar.setVisibility(View.GONE);
        binding.btnAddAds.setEnabled(true);
        binding.btnAddAds.setClickable(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}