package com.example.drrbnicompany.Fragments.Auth.SignUp;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.drrbnicompany.R;
import com.example.drrbnicompany.ViewModels.AddImfViewModel;
import com.example.drrbnicompany.ViewModels.MyListener;
import com.example.drrbnicompany.databinding.FragmentSignUpAddImgBinding;
import com.google.android.material.snackbar.Snackbar;

public class SignUpAddImgFragment extends Fragment {

    private FragmentSignUpAddImgBinding binding;
    private ActivityResultLauncher<String> getImg;
    private ActivityResultLauncher<String> permission;
    private AddImfViewModel addImfViewModel;
    private Uri image;

    public SignUpAddImgFragment() {
    }

    public static SignUpAddImgFragment newInstance() {
        return new SignUpAddImgFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addImfViewModel = new ViewModelProvider(this).get(AddImfViewModel.class);

            getImg = registerForActivityResult(
                    new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
                        @Override
                        public void onActivityResult(Uri result) {
                            if (result != null) {
                                binding.addImgLayoutBtn.setVisibility(View.VISIBLE);
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
                    }
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignUpAddImgBinding
                .inflate(getLayoutInflater(), container, false);


        binding.addImgBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permission.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        });

        binding.addImgBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load();

                addImfViewModel.storeData(image, new MyListener<Boolean>() {
                    @Override
                    public void onValuePosted(Boolean value) {
                        if (value) {
                            stopLoad();
                            NavController navController = Navigation.findNavController(binding.getRoot());
                            navController.navigate(R.id.action_signUpAddImgFragment_to_loginFragment);
                        }
                    }
                }, new MyListener<Boolean>() {
                    @Override
                    public void onValuePosted(Boolean value) {
                        if (value) {
                            stopLoad();
                            Snackbar.make(requireView(), "فشل تحميل الصورة", Snackbar.LENGTH_LONG).show();
                            return;
                        }
                    }
                });
            }
        });


        binding.addImgBtnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NavController navController = Navigation.findNavController(binding.getRoot());
                navController.navigate(R.id.action_signUpAddImgFragment_to_loginFragment);

            }
        });

        return binding.getRoot();
    }

    public void load() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.addImgBtnNext.setEnabled(false);
        binding.addImgBtnNext.setClickable(false);
    }

    public void stopLoad() {
        binding.progressBar.setVisibility(View.GONE);
        binding.addImgBtnNext.setEnabled(true);
        binding.addImgBtnNext.setClickable(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}