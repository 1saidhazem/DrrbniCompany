package com.example.drrbnicompany.Fragments.Auth.SignUp;

import android.os.Bundle;
import android.text.TextUtils;
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
import com.example.drrbnicompany.ViewModels.SignUpViewModel;
import com.example.drrbnicompany.databinding.FragmentSignUpBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignUpFragment extends Fragment {

    private FragmentSignUpBinding binding;
    private SignUpViewModel signUpViewModel;
    private FirebaseAuth firebaseAuth;
    private Fragment fragment;
    public SignUpFragment() {}

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        signUpViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
        firebaseAuth = FirebaseAuth.getInstance();
        fragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignUpBinding
                .inflate(getLayoutInflater(),container,false);


        binding.signUpBtnCompleteTheRegistrationProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.signUpEtName.getText().toString().trim();
                String email = binding.signUpEtEmail.getText().toString().trim();
                String password = binding.signUpEtPassword.getText().toString().trim();
                String category = binding.signUpCategory.getSelectedItem().toString();;

                // فحص المدخلات
                if (TextUtils.isEmpty(name)) {
                    Snackbar.make(view, "أدخل الاسم", Snackbar.LENGTH_LONG).show();
                    return;
                } else if (TextUtils.isEmpty(email)) {
                    Snackbar.make(view, "أدخل الايميل", Snackbar.LENGTH_LONG).show();
                    return;
                } else if (TextUtils.isEmpty(password)) {
                    Snackbar.make(view, "أدخل كلمة المرور", Snackbar.LENGTH_LONG).show();
                    return;
                }else if (binding.signUpCategory.getSelectedItemPosition() < 1) {
                    Snackbar.make(view, "أختر التصنيف", Snackbar.LENGTH_LONG).show();
                    return;
                }

                load();

                if (firebaseAuth.getCurrentUser() != null){
                    NavController navController = Navigation.findNavController(binding.getRoot());
                    navController.navigate(R.id.action_loginFragment_to_signUpAddressFragment);
                }

                signUpViewModel.signUp(email, password, new MyListener<FirebaseUser>() {
                    @Override
                    public void onValuePosted(FirebaseUser value) {

                        //في حالة النجاح يرفع الداتا وينتقل للواجهة التالية
                        signUpViewModel.storeData(value, name, category ,new MyListener<Boolean>() {
                            @Override
                            public void onValuePosted(Boolean value) {
                               if (value){
                                   stopLoad();
                                   NavController navController = Navigation.findNavController(binding.getRoot());
                                   navController.navigate(R.id.action_loginFragment_to_signUpAddressFragment);
                               }
                            }
                        });

                    }
                }, new MyListener<String>() {
                    @Override
                    public void onValuePosted(String value) {

                        //في حالة الفشل يرفع الداتا يظهر الخطأ
                        if (!TextUtils.isEmpty(value)) {
                            if (value.contains("password is invalid"))
                                Snackbar.make(view, "كلمة مرور غير صالحة", Snackbar.LENGTH_LONG).show();
                            else if (value.contains("badly formatted"))
                                Snackbar.make(view, "البريد الألكتروني غير صالح", Snackbar.LENGTH_LONG).show();
                            else if (value.contains("use by another account"))
                                Snackbar.make(view, "البريد الألكتروني مستخدم", Snackbar.LENGTH_LONG).show();
                            stopLoad();
                        }
                    }
                });
            }
        });


        return binding.getRoot();
    }

    public void load(){
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.signUpBtnCompleteTheRegistrationProcess.setEnabled(false);
        binding.signUpBtnCompleteTheRegistrationProcess.setClickable(false);
    }

    public void stopLoad(){
        binding.progressBar.setVisibility(View.GONE);
        binding.signUpBtnCompleteTheRegistrationProcess.setEnabled(true);
        binding.signUpBtnCompleteTheRegistrationProcess.setClickable(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}