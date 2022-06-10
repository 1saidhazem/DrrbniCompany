package com.example.drrbnicompany.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class ContactInformationViewModel extends AndroidViewModel {

    private Repository repository;

    public ContactInformationViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public void storeData(String whatsapp  , MyListener<Boolean> isSuccessful){
        repository.storeContactData(whatsapp , isSuccessful);
    }
}
