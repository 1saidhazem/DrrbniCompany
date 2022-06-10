package com.example.drrbnicompany.ViewModels;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class AddImfViewModel extends AndroidViewModel {

    private Repository repository;

    public AddImfViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public void storeData (Uri image , MyListener<Boolean> isSuccessful , MyListener<Boolean> isFailure){
        repository.storeImg(image,isSuccessful,isFailure);
    }
}
