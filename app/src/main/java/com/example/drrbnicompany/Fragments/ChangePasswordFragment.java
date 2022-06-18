package com.example.drrbnicompany.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.drrbnicompany.R;
import com.example.drrbnicompany.ViewModels.ChangePasswordViewModel;
import com.example.drrbnicompany.ViewModels.MyListener;
import com.example.drrbnicompany.databinding.FragmentChangePasswordBinding;
import com.example.drrbnicompany.databinding.FragmentSignInBinding;
import com.google.android.material.snackbar.Snackbar;


public class ChangePasswordFragment extends Fragment {

    private FragmentChangePasswordBinding binding;
    private ChangePasswordViewModel changePasswordViewModel;

    public ChangePasswordFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChangePasswordBinding
                .inflate(getLayoutInflater(), container, false);

        changePasswordViewModel = new ViewModelProvider(this).get(ChangePasswordViewModel.class);



       binding.btnOk.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               load();
               String oldPass = binding.oldPassword.getText().toString().trim();
               String newPass = binding.newPassword.getText().toString().trim();
               String confPass = binding.confirmPassword.getText().toString().trim();

               if (TextUtils.isEmpty(oldPass)) {
                   Snackbar.make(requireView(), "أدخل كلمة المرور القديمة", Snackbar.LENGTH_LONG).show();
                   return;
               }else if (TextUtils.isEmpty(newPass)){
                   Snackbar.make(requireView(), "أدخل كلمة المرور الجديدة", Snackbar.LENGTH_LONG).show();
                   return;
               }else if (TextUtils.isEmpty(confPass)){
                   Snackbar.make(requireView(), "أدخل تأكيد كلمة المرور", Snackbar.LENGTH_LONG).show();
                   return;
               }else if (!newPass.equals(confPass)){
                   Snackbar.make(requireView(), "كلمة المرور الجديدة غير متطابقة", Snackbar.LENGTH_LONG).show();
                   return;
               }

               changePasswordViewModel.changePassword(oldPass, newPass, new MyListener<Boolean>() {
                   @Override
                   public void onValuePosted(Boolean value) {
                       stopLoad();
                       Snackbar.make(requireView() , "تم تغيير كملة المرور بنجاح"  , Snackbar.LENGTH_LONG).show();
                       Navigation.findNavController(binding.getRoot()).popBackStack();
                   }
               }, new MyListener<String>() {
                   @Override
                   public void onValuePosted(String value) {
                       stopLoad();
                       if (value.contains("The password is invalid or the user does not have a password")){
                           Snackbar.make(requireView() , "كلمة المرور غير صحيحة"  , Snackbar.LENGTH_LONG).show();
                           return;
                       }else if (value.contains("Password should be at least 6 characters")){
                           Snackbar.make(requireView() , "كلمة المرور يجب أن تكون أكبر من 6 أحرف"  , Snackbar.LENGTH_LONG).show();
                           return;
                       }
                   }
               });
           }
       });


        return binding.getRoot();
    }

    public void load(){
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.btnOk.setEnabled(false);
        binding.btnOk.setClickable(false);
    }

    public void stopLoad(){
        binding.progressBar.setVisibility(View.GONE);
        binding.btnOk.setEnabled(true);
        binding.btnOk.setClickable(true);
    }
}