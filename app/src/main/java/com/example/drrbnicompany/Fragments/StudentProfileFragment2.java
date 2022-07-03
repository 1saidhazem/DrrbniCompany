package com.example.drrbnicompany.Fragments;

import static com.example.drrbnicompany.Constant.STUDENT_DEFAULT_IMAGE_PROFILE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.drrbnicompany.Adapters.JobAdapter;
import com.example.drrbnicompany.Models.Job;
import com.example.drrbnicompany.Models.Student;
import com.example.drrbnicompany.R;
import com.example.drrbnicompany.ViewModels.MyListener;
import com.example.drrbnicompany.ViewModels.StudentProfileViewModel;
import com.example.drrbnicompany.databinding.FragmentStudentProfileBinding;
import java.util.List;

public class StudentProfileFragment2 extends Fragment {

    private FragmentStudentProfileBinding binding;
    private JobAdapter jobAdapter;
    private StudentProfileViewModel studentProfileViewModel;

    public StudentProfileFragment2() {}

    public static StudentProfileFragment2 newInstance() {
        return new StudentProfileFragment2();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStudentProfileBinding
                .inflate(getLayoutInflater(), container, false);

        String studentId = getArguments().getString("userId").trim();

        studentProfileViewModel = new ViewModelProvider(this).get(StudentProfileViewModel.class);
        load();

        studentProfileViewModel.getStudentById(studentId, new MyListener<Student>() {
            @Override
            public void onValuePosted(Student value) {
                if (getActivity() == null) return;

                if (value.getImg() == null) {
                    Glide.with(getActivity()).load(STUDENT_DEFAULT_IMAGE_PROFILE).placeholder(R.drawable.anim_progress).into(binding.studentImage);
                } else {
                    Glide.with(getActivity()).load(value.getImg()).placeholder(R.drawable.anim_progress).into(binding.studentImage);
                }
                binding.studentCollage.setText(value.getCollege());
                binding.studentName.setText(value.getName());
                binding.studentMajor.setText(value.getMajor());
                binding.studentEmail.setText(value.getEmail());
                binding.studentWhatsapp.setText(value.getWhatsApp());
                stopLoad();
            }
        }, new MyListener<Boolean>() {
            @Override
            public void onValuePosted(Boolean value) {

            }
        });

        studentProfileViewModel.getStudentJobsById(studentId, new MyListener<List<Job>>() {
            @Override
            public void onValuePosted(List<Job> value) {
                if (getActivity() == null) return;

                jobAdapter = new JobAdapter(value, new MyListener<Job>() {
                    @Override
                    public void onValuePosted(Job value) {
                        NavController navController = Navigation.findNavController(binding.getRoot());
                        navController.navigate(StudentProfileFragment2Directions
                                .actionStudentProfileFragment2ToShowJobFragment22(value));
                    }
                });
                initRV();
            }
        }, new MyListener<Boolean>() {
            @Override
            public void onValuePosted(Boolean value) {

            }
        });


        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    void initRV() {
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getActivity());
        binding.studentRv.setLayoutManager(lm);
        binding.studentRv.setHasFixedSize(true);
        binding.studentRv.setAdapter(jobAdapter);
    }

    public void load() {
        binding.shimmerView.setVisibility(View.VISIBLE);
        binding.shimmerView.startShimmerAnimation();
        binding.studentProfileLayout.setVisibility(View.GONE);
    }

    public void stopLoad() {
        binding.shimmerView.setVisibility(View.GONE);
        binding.shimmerView.stopShimmerAnimation();
        binding.studentProfileLayout.setVisibility(View.VISIBLE);
    }

}