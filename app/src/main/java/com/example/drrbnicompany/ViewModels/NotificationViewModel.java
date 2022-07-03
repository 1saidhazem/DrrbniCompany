package com.example.drrbnicompany.ViewModels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import com.example.drrbnicompany.Models.Notification;
import com.example.drrbnicompany.Models.Student;
import java.util.List;

public class NotificationViewModel extends AndroidViewModel {

    private Repository repository;

    public NotificationViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public void getNotificationsByUid(String uid , MyListener<List<Notification>> isSuccessful
            , MyListener<Boolean> isFailure){
        repository.getNotificationsByUid(uid, isSuccessful, isFailure);
    }

    public void getStudentNameAndImageByUid(String uid , MyListener<Student> listener){
        repository.getStudentNameAndImageByUid(uid, listener);
    }

    public void getTokenByStudentId(String studentId, MyListener<String> isSuccessful){
        repository.getTokenByStudentId(studentId , isSuccessful);
    }

    public void getNameByUid(String Uid , MyListener<String> isSuccessful){
        repository.getNameByUid(Uid , isSuccessful);
    }

}
