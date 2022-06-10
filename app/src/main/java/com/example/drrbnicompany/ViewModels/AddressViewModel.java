package com.example.drrbnicompany.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class AddressViewModel extends AndroidViewModel {

    private Repository repository;

    public AddressViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public void storeData (String governorate , String address , MyListener<Boolean> isSuccessful){
        repository.storeAddressData(governorate , address , isSuccessful);
    }
}
