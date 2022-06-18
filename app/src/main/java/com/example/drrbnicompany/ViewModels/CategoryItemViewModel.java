package com.example.drrbnicompany.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.drrbnicompany.Models.Job;
import com.example.drrbnicompany.Models.Student;

import java.util.List;

public class CategoryItemViewModel extends AndroidViewModel {

    private Repository repository;

    public CategoryItemViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public void getAllStudentByMajor(String majorName , MyListener<List<Student>> isSuccessful
            , MyListener<Boolean> isFailure){
        repository.getAllStudentByMajor(majorName, isSuccessful, isFailure);
    }
}
