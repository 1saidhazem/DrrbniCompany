package com.example.drrbnicompany.Fragments.BottomNavigationScreens;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.drrbnicompany.ViewModels.MyListener;
import com.example.drrbnicompany.ViewModels.NotificationViewModel;
import com.example.drrbnicompany.databinding.FragmentNotificationsBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private FirebaseAuth auth;
    private NotificationViewModel notificationViewModel;

    public NotificationsFragment() {
    }

    public static NotificationsFragment newInstance() {
        return new NotificationsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notificationViewModel = new ViewModelProvider(this).get(NotificationViewModel.class);
        auth = FirebaseAuth.getInstance();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);

        try {
            notificationViewModel.getStateActiveAccount(auth.getCurrentUser().getEmail(), new MyListener<Boolean>() {
                @Override
                public void onValuePosted(Boolean value) {
                    if (value) return;
                    Snackbar.make(container, "انتهت الجلسة", Snackbar.LENGTH_LONG).show();
                    notificationViewModel.signOut();
//                    NavController navController = Navigation.findNavController(binding.getRoot());
//                    navController.navigate(R.id.action_mainFragment_to_loginFragment);
                }
            });
        } catch (Exception e) {
            Log.e("ttt", e.getMessage());
        }

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}