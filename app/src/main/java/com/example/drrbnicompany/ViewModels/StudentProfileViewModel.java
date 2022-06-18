package com.example.drrbnicompany.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.drrbnicompany.Models.Job;
import com.example.drrbnicompany.Models.Student;

import java.util.List;

public class StudentProfileViewModel extends AndroidViewModel {

    private Repository repository;

    public StudentProfileViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public void getStudentById(String studentId ,MyListener<Student> isSuccessful
            , MyListener<Boolean> isFailure){
        repository.getStudentById(studentId , isSuccessful ,isFailure);
    }

    public void getStudentJobsById(String uid , MyListener<List<Job>> isSuccessful
            , MyListener<Boolean> isFailure){
        repository.getStudentJobsById(uid, isSuccessful, isFailure);
    }
}
