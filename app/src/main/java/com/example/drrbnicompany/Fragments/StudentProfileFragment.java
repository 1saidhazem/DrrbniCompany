package com.example.drrbnicompany.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.drrbnicompany.Adapters.JobAdapter;
import com.example.drrbnicompany.Models.Job;
import com.example.drrbnicompany.Models.Student;
import com.example.drrbnicompany.R;
import com.example.drrbnicompany.ViewModels.MyListener;
import com.example.drrbnicompany.ViewModels.ProfileViewModel;
import com.example.drrbnicompany.ViewModels.StudentProfileViewModel;
import com.example.drrbnicompany.databinding.FragmentStudentProfileBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class StudentProfileFragment extends Fragment {

    private FragmentStudentProfileBinding binding;
    private JobAdapter jobAdapter;
    private StudentProfileViewModel studentProfileViewModel;
    public StudentProfileFragment() {}

    public static StudentProfileFragment newInstance() {
        return new StudentProfileFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        studentProfileViewModel = new ViewModelProvider(this).get(StudentProfileViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStudentProfileBinding
                .inflate(getLayoutInflater(),container,false);

        load();
        String userId = StudentProfileFragmentArgs.fromBundle(requireArguments()).getUserId();

        studentProfileViewModel.getInfoStudentByUID(userId, new MyListener<Student>() {
            @Override
            public void onValuePosted(Student value) {
                stopLoad();
                if (value.getImg() == null) {
                    binding.appBarImage.setImageResource(R.drawable.defult_img_student);
                }else {
                    Glide.with(requireActivity()).load(value.getImg()).placeholder(R.drawable.anim_progress).into(binding.appBarImage);
                }
                binding.studentName.setText(value.getName());
                binding.tvUniversityName.setText(value.getCollege());
                binding.tvSpecializationName.setText(value.getMajor());
                binding.whatsapp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String url = "https://api.whatsapp.com/send?phone="+value.getWhatsApp();
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                    }
                });
                binding.Gmail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        requireActivity().startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"+value.getEmail())));
                    }
                });
            }
        }, new MyListener<String>() {
            @Override
            public void onValuePosted(String value) {

            }
        });

        studentProfileViewModel.getJobsByUid(userId, new MyListener<List<Job>>() {
            @Override
            public void onValuePosted(List<Job> value) {
                stopLoad();
                jobAdapter = new JobAdapter(value, new MyListener<String>() {
                    @Override
                    public void onValuePosted(String value) {

                    }
                });
                initRV();
            }
        }, new MyListener<String>() {
            @Override
            public void onValuePosted(String value) {

            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    void initRV(){
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getActivity());
        binding.rvJobs.setLayoutManager(lm);
        binding.rvJobs.setHasFixedSize(true);
        binding.rvJobs.setAdapter(jobAdapter);
    }

    public void load() {
        binding.shimmerView.setVisibility(View.VISIBLE);
        binding.shimmerView.startShimmerAnimation();
        binding.profileLayout.setVisibility(View.GONE);
    }

    public void stopLoad() {
        binding.shimmerView.setVisibility(View.GONE);
        binding.shimmerView.stopShimmerAnimation();
        binding.profileLayout.setVisibility(View.VISIBLE);
    }

}