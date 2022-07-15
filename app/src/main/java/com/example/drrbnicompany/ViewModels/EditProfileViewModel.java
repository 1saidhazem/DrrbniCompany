package com.example.drrbnicompany.ViewModels;

import android.app.Application;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.drrbnicompany.Models.Company;

public class EditProfileViewModel extends AndroidViewModel {

    private Repository repository;
    private MutableLiveData<Company> profileInfo;

    public EditProfileViewModel(@NonNull Application application) {
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

    public void editProfileData(Uri image, String companyName, String category
            , MyListener<Boolean> isSuccessful, MyListener<Boolean> isFailure) {

        repository.editProfileData(image, companyName, category, isSuccessful, isFailure);
    }

    public void editProfileDataWithoutImage(String companyName, String category
            , MyListener<Boolean> isSuccessful, MyListener<Boolean> isFailure) {

        repository.editProfileDataWithoutImage(companyName, category, isSuccessful, isFailure);
    }

    public void getEmail(String uid, MyListener<String> isSuccessful
            , MyListener<Boolean> isFailure) {

        repository.getEmail(uid, isSuccessful, isFailure);
    }

    public void changeEmail(String currentPassword, String newEmail, String uid, MyListener<Boolean> isSuccessful
            , MyListener<String> isFailure) {

        repository.changeEmail(currentPassword, newEmail, uid, isSuccessful, isFailure);
    }

}
