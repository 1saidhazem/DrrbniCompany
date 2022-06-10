package com.example.drrbnicompany.Fragments.Auth.SignIn;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.drrbnicompany.Adapters.PagerFragmentsAdapter;
import com.example.drrbnicompany.Fragments.Auth.SignUp.SignUpFragment;
import com.example.drrbnicompany.databinding.FragmentLoginBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    public LoginFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding
                .inflate(getLayoutInflater(),container,false);

        String [] tabs = {"تسجيل الدخول","التسجيل"};
        ArrayList<Fragment> list = new ArrayList<>();
        list.add(SignInFragment.newInstance());
        list.add(SignUpFragment.newInstance());

        PagerFragmentsAdapter adapter = new PagerFragmentsAdapter(requireActivity(),list);
        binding.loginViewPager.setAdapter(adapter);

        new TabLayoutMediator(binding.tabLayout,binding.loginViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabs[position]);
            }
        }).attach();


        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
