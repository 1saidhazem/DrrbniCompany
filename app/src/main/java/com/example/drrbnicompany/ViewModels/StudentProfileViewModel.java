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

    public void getInfoStudentByUID(String UserId, MyListener<Student> isSuccessful, MyListener<String> isFailure) {
        repository.getInfoStudentByUID(UserId, isSuccessful, isFailure);
    }

    public void getJobsByUid(String uid, MyListener<List<Job>> isSuccessful, MyListener<String> isFailure) {
        repository.getJobsByUid(uid, isSuccessful, isFailure);
    }

}
