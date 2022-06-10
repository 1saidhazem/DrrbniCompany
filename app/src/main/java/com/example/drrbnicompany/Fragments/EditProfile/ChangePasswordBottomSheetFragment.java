package com.example.drrbnicompany.Fragments.EditProfile;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.drrbnicompany.R;
import com.example.drrbnicompany.ViewModels.MyListener;
import com.example.drrbnicompany.ViewModels.ProfileViewModel;
import com.example.drrbnicompany.databinding.FragmentChangePasswordBottomSheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;

public class ChangePasswordBottomSheetFragment extends BottomSheetDialogFragment {


    private FragmentChangePasswordBottomSheetBinding binding;
    private ProfileViewModel profileViewModel;

    public ChangePasswordBottomSheetFragment() {
    }

    public static ChangePasswordBottomSheetFragment newInstance() {
        return new ChangePasswordBottomSheetFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.bottomSheet);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChangePasswordBottomSheetBinding
                .inflate(getLayoutInflater(), container, false);

        binding.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentPassword = binding.etCurrentPassword.getText().toString();
                String newPassword = binding.etNewPassword.getText().toString();
                String confPassword = binding.etConfirmNewPassword.getText().toString();
                if (TextUtils.isEmpty(currentPassword)) {
                    Toast.makeText(requireContext(), "أدخل كلمة المرور الحالية", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(newPassword)) {
                    Toast.makeText(requireContext(), "أدخل كلمة المرور الجديدة", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(confPassword)) {
                    Toast.makeText(requireContext(), "أدخل تأكيد كلمة المرور الجديدة", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!newPassword.equals(confPassword)) {
                    Toast.makeText(requireContext(), "كلمة المرور غير متطابقة", Toast.LENGTH_SHORT).show();
                    return;
                }

//                if (newPassword.length() < 6 && confPassword.length() < 6) {
//                    Toast.makeText(requireContext(), "يجب أن تكون كلمة المرور الجديدة أكبر من 6 أحرف", Toast.LENGTH_SHORT).show();
//                    return;
//                } else {
//                    profileViewModel.changePassword(currentPassword, newPassword, confPassword, new MyListener<String>() {
//                        @Override
//                        public void onValuePosted(String value) {
//                            dismiss();
//                        }
//                    }, new MyListener<String>() {
//                        @Override
//                        public void onValuePosted(String value) {
//                            dismiss();
//                            Snackbar.make(view, value, Snackbar.LENGTH_LONG).show();
//                        }
//                    });
//                }
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