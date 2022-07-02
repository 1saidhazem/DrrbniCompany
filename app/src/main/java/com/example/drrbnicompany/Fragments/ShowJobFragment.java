package com.example.drrbnicompany.Fragments;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.drrbnicompany.Fragments.BottomNavigationScreens.HomeFragmentDirections;
import com.example.drrbnicompany.Models.Job;
import com.example.drrbnicompany.databinding.FragmentShowJobBinding;
public class ShowJobFragment extends Fragment {

    private FragmentShowJobBinding binding;
    private Job job;
    private boolean showBtn;
    public ShowJobFragment() {}

    public static ShowJobFragment newInstance() {
        return new ShowJobFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        job = (Job) getArguments().getSerializable("jobObject");
        showBtn = getArguments().getBoolean("showBtn");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentShowJobBinding
                .inflate(getLayoutInflater(),container,false);

        if (showBtn)
            binding.btnNavigate.setVisibility(View.GONE);

        binding.jobName.setText(job.getJobName());
        binding.jobDescription.setText(job.getJobDescription());
        binding.jobMajor.setText(job.getMajor());
        binding.jobLink.setText(job.getJobLink());
        binding.progressBar.setVisibility(View.VISIBLE);
        Glide.with(requireContext()).load(job.getImg()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                binding.progressBar.setVisibility(View.GONE);
                return false;
            }
        }).into(binding.jobImage);

        binding.btnNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(binding.getRoot());
                navController.navigate(ShowJobFragmentDirections
                .actionShowPostFragmentToStudentProfileFragment2(job.getUserId()));
            }
        });

        binding.jobLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String url = job.getJobLink();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                }catch(Exception e) {

                };
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