package com.example.drrbnicompany.ViewModels;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.drrbnicompany.Models.Ads;
import com.example.drrbnicompany.Models.Job;
import com.example.drrbnicompany.Models.Company;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;

import java.util.List;

public class ProfileViewModel extends AndroidViewModel {

    private Repository repository;
    private MutableLiveData<Company> profileInfo;
    private MutableLiveData<List<Ads>> adsData;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        profileInfo = repository.getProfileInfo();
        adsData = repository.getAdsData();
    }

    public void requestProfileInfo(String uid){
        repository.requestProfileInfo(uid);
    }

    public void storeAdsData(String uid, Uri image, String adsName, String major, String adsRequirements,
                             String adsDescription, MyListener<Boolean> isSuccessful, MyListener<Boolean> isFailure) {
        repository.storeAdsData(uid, image, adsName, major, adsRequirements, adsDescription, isSuccessful, isFailure);
    }
    public void requestAdsJobs(String uid) {
        repository.requestGetAds(uid);
    }

    public MutableLiveData<Company> getProfileInfo() {
        return profileInfo;
    }

    public MutableLiveData<List<Ads>> getAdsData() {
        return adsData;
    }

    public void changePassword(String currentPassword, String newPassword, String confPassword, MyListener<String> isSuccessful, MyListener<String> isFailure) {
        repository.changePassword(currentPassword, newPassword, confPassword, isSuccessful, isFailure);
    }

    public void getStateActiveAccount(String email, MyListener<Boolean> isSuccessful) {
        repository.getStateActiveAccount(email, isSuccessful);
    }

    public void signOut() {
        repository.signOut();
    }


}
