package com.example.drrbnicompany.Fragments.Auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.drrbnicompany.R;
import com.example.drrbnicompany.ViewModels.MyListener;
import com.example.drrbnicompany.ViewModels.SignInViewModel;
import com.example.drrbnicompany.databinding.FragmentForgotPasswordBinding;
import com.google.android.material.snackbar.Snackbar;

public class ForgotPasswordFragment extends Fragment {

    private FragmentForgotPasswordBinding binding;
    private SignInViewModel viewModel;

    public ForgotPasswordFragment() {
    }

    public static ForgotPasswordFragment newInstance() {
        return new ForgotPasswordFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentForgotPasswordBinding.inflate(getLayoutInflater(), container, false);

        viewModel = new ViewModelProvider(this).get(SignInViewModel.class);

        binding.forgotPasswordBtnGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.forgotPasswordEtEmail.getText().toString().trim();
                if (email.isEmpty()) {
                    Snackbar.make(view, "أدخل البريد الالكتروني", Snackbar.LENGTH_LONG).show();
                    binding.progressBar.setVisibility(View.GONE);
                } else {
                    viewModel.resetPassword(email, new MyListener<Boolean>() {
                        @Override
                        public void onValuePosted(Boolean value) {
                            Snackbar.make(view, "تم إرسال رابط إعادة تعيين كلمة المرور على بريدك الإلكتروني", Snackbar.LENGTH_LONG).show();
                            NavController navController = Navigation.findNavController(binding.getRoot());
                            navController.navigate(R.id.action_forgotPasswordFragment_to_loginFragment);
                        }
                    }, new MyListener<String>() {
                        @Override
                        public void onValuePosted(String value) {
                            Snackbar.make(view, value, Snackbar.LENGTH_LONG).show();
                        }
                    });
                    binding.progressBar.setVisibility(View.GONE);
                }
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}