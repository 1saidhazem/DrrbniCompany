package com.example.drrbnicompany.Fragments;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.drrbnicompany.Models.Ads;
import com.example.drrbnicompany.Models.Company;
import com.example.drrbnicompany.R;
import com.example.drrbnicompany.SpinnerPosition;
import com.example.drrbnicompany.ViewModels.EditProfileViewModel;
import com.example.drrbnicompany.ViewModels.MyListener;
import com.example.drrbnicompany.ViewModels.ShowAndEditAdsViewModel;
import com.example.drrbnicompany.databinding.FragmentShowAndEditAdsBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;


public class ShowAndEditAdsFragment extends Fragment {

    private FragmentShowAndEditAdsBinding binding;
    private ShowAndEditAdsViewModel adsViewModel;
    private ActivityResultLauncher<String> getImg;
    private ActivityResultLauncher<String> permission;
    private Uri image;
    private SpinnerPosition spinnerPosition;

    public ShowAndEditAdsFragment newInstance() {
        return new ShowAndEditAdsFragment();
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentShowAndEditAdsBinding
                .inflate(getLayoutInflater(), container, false);
        load();

        adsViewModel = new ViewModelProvider(this).get(ShowAndEditAdsViewModel.class);
        spinnerPosition = new SpinnerPosition();

        String adsId = getArguments().getString("adsId");
        adsViewModel.getAdsById(adsId, new MyListener<Ads>() {
            @Override
            public void onValuePosted(Ads value) {
                Glide.with(requireActivity()).load(value.getImg()).placeholder(R.drawable.anim_progress).into(binding.adsImage);
                binding.adsTitle.setText(value.getAdsTitle());
                binding.adsDescription.setText(value.getAdsDescription());
                binding.major.setSelection(spinnerPosition.getMajorPosition(value.getMajor()));
                binding.requirements.setText(value.getAdsRequirements());
                stopLoad();
            }
        }, new MyListener<Boolean>() {
            @Override
            public void onValuePosted(Boolean value) {
                Snackbar.make(requireView(),"فشل التحمبل" , Snackbar.LENGTH_LONG).show();
                binding.shimmerView.stopShimmerAnimation();
            }
        });

        binding.adsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permission.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        });

        binding.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();

                String adsTitle = binding.adsTitle.getText().toString().trim();
                String adsMajor = binding.major.getSelectedItem().toString();
                String adsDescription = binding.adsDescription.getText().toString().trim();
                String adsRequirements= binding.requirements.getText().toString().trim();

                if (TextUtils.isEmpty(adsTitle)){
                    Snackbar.make(requireView() , "أدخل عنوان التدريب" , Snackbar.LENGTH_LONG).show();
                    return;
                }else  if (binding.major.getSelectedItemPosition() < 1){
                    Snackbar.make(requireView() , "أختر التخصص المطلوب" , Snackbar.LENGTH_LONG).show();
                    return;
                }else  if (TextUtils.isEmpty(adsDescription)){
                    Snackbar.make(requireView() , "أدخل وصف التدريب" , Snackbar.LENGTH_LONG).show();
                    return;
                }else  if (TextUtils.isEmpty(adsRequirements)){
                    Snackbar.make(requireView() , "أدخل متطلبات التدريب" , Snackbar.LENGTH_LONG).show();
                    return;
                }

                if (image == null){

                    adsViewModel.editAdsDataWithoutImage(adsId, adsTitle, adsMajor
                            , adsRequirements, adsDescription, new MyListener<Boolean>() {
                                @Override
                                public void onValuePosted(Boolean value) {
                                    if (value){
                                        stopUpdate();
                                        Snackbar.make(view , "تم التعديل بنجاح" , Snackbar.LENGTH_LONG).show();
                                        Navigation.findNavController(requireView()).popBackStack();
                                    }
                                }
                            }, new MyListener<Boolean>() {
                                @Override
                                public void onValuePosted(Boolean value) {
                                    if (value){
                                        stopUpdate();
                                        Snackbar.make(view , "فشل التعديل" , Snackbar.LENGTH_LONG).show();
                                    }
                                }
                            });

                }else {
                    adsViewModel.editAdsData(adsId, image, adsTitle, adsMajor, adsRequirements
                            , adsDescription, new MyListener<Boolean>() {
                                @Override
                                public void onValuePosted(Boolean value) {
                                    if (value){
                                        stopUpdate();
                                        Snackbar.make(view , "تم التعديل بنجاح" , Snackbar.LENGTH_LONG).show();
                                        Navigation.findNavController(requireView()).popBackStack();
                                    }
                                }
                            }, new MyListener<Boolean>() {
                                @Override
                                public void onValuePosted(Boolean value) {
                                    if (value){
                                        stopUpdate();
                                        Snackbar.make(view , "فشل التعديل" , Snackbar.LENGTH_LONG).show();
                                    }
                                }
                            });
                }


            }
        });

        return binding.getRoot();
    }

    public void load() {
        binding.shimmerView.setVisibility(View.VISIBLE);
        binding.shimmerView.startShimmerAnimation();
        binding.showAndEditAdsLayout.setVisibility(View.GONE);
    }

    public void stopLoad() {
        binding.shimmerView.setVisibility(View.GONE);
        binding.shimmerView.stopShimmerAnimation();
        binding.showAndEditAdsLayout.setVisibility(View.VISIBLE);
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