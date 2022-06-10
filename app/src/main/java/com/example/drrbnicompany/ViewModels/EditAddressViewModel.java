package com.example.drrbnicompany.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.drrbnicompany.Models.Company;

public class EditAddressViewModel extends AndroidViewModel {

    private Repository repository;
    private MutableLiveData<Company> profileInfo;

    public EditAddressViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        profileInfo = repository.getProfileInfo();
    }

    public void requestProfileInfo(String uid){
        repository.requestProfileInfo(uid);
    }

    public MutableLiveData<Company> getProfileInfo() {
        return profileInfo;
    }

    public void EditAddress(String governorate, String address
            , MyListener<Boolean> isSuccessful , MyListener<Boolean> isFailure) {

        repository.editAddress(governorate, address, isSuccessful, isFailure);
    }

}