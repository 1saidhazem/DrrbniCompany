package com.example.drrbnicompany.Fragments.EditProfile;

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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.drrbnicompany.Models.Company;
import com.example.drrbnicompany.R;
import com.example.drrbnicompany.SpinnerPosition;
import com.example.drrbnicompany.ViewModels.EditProfileViewModel;
import com.example.drrbnicompany.ViewModels.MyListener;
import com.example.drrbnicompany.databinding.FragmentEditProfileBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class EditProfileFragment extends Fragment {

    private FragmentEditProfileBinding binding;
    private FirebaseAuth auth;
    private EditProfileViewModel editProfileViewModel;
    private ActivityResultLauncher<String> getImg;
    private ActivityResultLauncher<String> permission;
    private Uri image;
    private Company thiCompany;
    private SpinnerPosition spinnerPosition;

    public EditProfileFragment() {
    }

    public static EditProfileFragment newInstance() {
        return new EditProfileFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getImg = registerForActivityResult(
                new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        if (result != null) {
                            binding.profileImage.setImageURI(result);
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
        binding = FragmentEditProfileBinding.inflate(getLayoutInflater(), container, false);

        load();

        auth = FirebaseAuth.getInstance();
        editProfileViewModel = new ViewModelProvider(this).get(EditProfileViewModel.class);
        editProfileViewModel.requestProfileInfo(auth.getCurrentUser().getUid());
        spinnerPosition = SpinnerPosition.getInstance();

        editProfileViewModel.getProfileInfo().observe(requireActivity(), new Observer<Company>() {
            @Override
            public void onChanged(Company company) {
                if (getActivity() == null) return;
                thiCompany =company ;
                if (company.getImg() == null) {
                    binding.profileImage.setImageResource(R.drawable.company_default_image);
                } else {
                    Glide.with(getActivity()).load(company.getImg()).placeholder(R.drawable.anim_progress).into(binding.profileImage);
                }
                binding.editProfileEtName.setText(company.getName());
                binding.editProfileEtEmail.setText(company.getEmail());
                binding.editProfileSpCategories.setSelection(spinnerPosition.getCategoryPosition(company.getCategory()));
                stopLoad();
            }
        });

        binding.tvAddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permission.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        });


        binding.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();

                String name = binding.editProfileEtName.getText().toString().trim();
                String email = binding.editProfileEtEmail.getText().toString().trim();
                //TODO change ET to spinnerPosition
                String category = binding.editProfileSpCategories.getSelectedItem().toString();

                if (TextUtils.isEmpty(name))
                    name = thiCompany.getName();
                else if (TextUtils.isEmpty(email))
                    email = thiCompany.getEmail();
                else if (binding.editProfileSpCategories.getSelectedItemPosition() < 1)
                    category = thiCompany.getCategory();

                if (image == null ){

                    editProfileViewModel.editProfileDataWithoutImage(name, email, category, new MyListener<Boolean>() {
                        @Override
                        public void onValuePosted(Boolean value) {
                            if (value){
                                stopUpdate();
                                Snackbar.make(view , "تم التعديل بنجاح" , Snackbar.LENGTH_LONG).show();
                                Navigation.findNavController(binding.getRoot()).popBackStack();
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

                    editProfileViewModel.editProfileData(image, name, email, category, new MyListener<Boolean>() {
                        @Override
                        public void onValuePosted(Boolean value) {
                            if (value){
                                stopUpdate();
                                Snackbar.make(view , "تم التعديل بنجاح" , Snackbar.LENGTH_LONG).show();
                                Navigation.findNavController(binding.getRoot()).popBackStack();
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

        binding.tvEditContactInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(binding.getRoot());
                navController.navigate(R.id.action_editProfileFragment_to_editContactInformationFragment);
            }
        });
        binding.tvEditAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(binding.getRoot());
                navController.navigate(R.id.action_editProfileFragment_to_editAddressFragment);
            }
        });
        binding.tvChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(binding.getRoot());
                navController.navigate(R.id.action_editProfileFragment_to_changePasswordFragment);
            }
        });


        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public void load() {
        binding.shimmerView.setVisibility(View.VISIBLE);
        binding.shimmerView.startShimmerAnimation();
        binding.editProfileLayout.setVisibility(View.GONE);
    }

    public void stopLoad() {
        binding.shimmerView.setVisibility(View.GONE);
        binding.shimmerView.stopShimmerAnimation();
        binding.editProfileLayout.setVisibility(View.VISIBLE);
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