package com.example.drrbnicompany.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.drrbnicompany.Models.Ads;

public class ShowAndEditAdsViewModel extends AndroidViewModel {

    private Repository repository;
    public ShowAndEditAdsViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }


    public void getAdsById(String adsId ,MyListener<Ads> isSuccessful
            , MyListener<Boolean> isFailure){
        repository.getAdsById(adsId, isSuccessful, isFailure);
    }
}
