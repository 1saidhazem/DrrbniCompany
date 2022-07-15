package com.example.drrbnicompany.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.drrbnicompany.Models.Company;
import com.example.drrbnicompany.Models.Notification;
import com.example.drrbnicompany.Models.Student;

import java.util.List;

public class NotificationViewModel extends AndroidViewModel {

    private Repository repository;

    public NotificationViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public void getNotificationByUid(String uid , MyListener<List<Notification>> isSuccessful
            , MyListener<Boolean> isFailure){
        repository.getNotificationByUid(uid, isSuccessful, isFailure);
    }

    public void getSender(String senderUid , MyListener<Student> student){
        repository.getSender(senderUid , student);
    }

    public void getNameByUid(String Uid , MyListener<String> isSuccessful){
        repository.getNameByUid(Uid, isSuccessful);
    }

    public void getTokenByStudentId(String studentId, MyListener<String> isSuccessful){
        repository.getTokenByStudentId(studentId , isSuccessful);
    }
}
