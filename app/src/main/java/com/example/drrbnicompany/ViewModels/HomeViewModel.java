package com.example.drrbnicompany.ViewModels;

import static com.example.drrbnicompany.Constant.ACTIVATED;
import static com.example.drrbnicompany.Constant.COLLECTION_USERS_PROFILES;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private Repository repository;

    public HomeViewModel(@NonNull Application application) {
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
