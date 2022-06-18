package com.example.drrbnicompany.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.drrbnicompany.Models.Major;

import java.util.List;

public class MajorViewModel extends AndroidViewModel {

    private Repository repository;

    public MajorViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public void getMajors(MyListener<List<Major>> isSuccessful) {
        repository.getMajors(isSuccessful);
    }


}
