package com.example.drrbnicompany.ViewModels;

import android.app.Application;
import android.net.Uri;

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

    public void editAdsData(String adsId, Uri image, String adsTitle, String major, String adsRequirements,
                            String adsDescription, MyListener<Boolean> isSuccessful, MyListener<Boolean> isFailure) {
        repository.editAdsData(adsId, image, adsTitle, major, adsRequirements, adsDescription, isSuccessful, isFailure);
    }

    public void editAdsDataWithoutImage(String adsId, String adsTitle, String major, String adsRequirements,
               String adsDescription, MyListener<Boolean> isSuccessful, MyListener<Boolean> isFailure) {
        repository.editAdsDataWithoutImage(adsId, adsTitle, major, adsRequirements, adsDescription, isSuccessful, isFailure);
    }

    public void deleteAds(String adsId, MyListener<Boolean> isSuccessful, MyListener<Boolean> isFailure) {
        repository.deleteAds(adsId, isSuccessful, isFailure);
    }
}
