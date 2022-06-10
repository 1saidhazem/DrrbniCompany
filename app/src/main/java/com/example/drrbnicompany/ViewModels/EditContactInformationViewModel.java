package com.example.drrbnicompany.ViewModels;

import static com.example.drrbnicompany.Constant.COLLECTION_USERS_PROFILES;
import static com.example.drrbnicompany.Constant.WHATSAPP;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.drrbnicompany.Models.Company;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.HashMap;

public class EditContactInformationViewModel extends AndroidViewModel {

    private Repository repository;
    private MutableLiveData<Company> profileInfo;

    public EditContactInformationViewModel(@NonNull Application application) {
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

    public void editContactInformation(String whatsapp, MyListener<Boolean> isSuccessful
            , MyListener<Boolean> isFailure) {
       repository.editContactInformation(whatsapp, isSuccessful, isFailure);
    }

}
