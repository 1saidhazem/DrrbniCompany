package com.example.drrbnicompany.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class NotificationViewModel extends AndroidViewModel {

    private Repository repository;

    public NotificationViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public void getStateActiveAccount(String email, MyListener<Boolean> isSuccessful) {
        repository.getStateActiveAccount(email, isSuccessful);
    }

    public void signOut() {
        repository.signOut();
    }


}
