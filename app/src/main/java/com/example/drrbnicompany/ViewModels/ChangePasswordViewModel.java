package com.example.drrbnicompany.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class ChangePasswordViewModel extends AndroidViewModel {

    private Repository repository;

    public ChangePasswordViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public void changePassword(String currentPassword,String newPassword
            ,MyListener<Boolean > isSuccessful , MyListener<String> isFailure){
        repository.changePassword(currentPassword , newPassword  , isSuccessful , isFailure);
    }
}
