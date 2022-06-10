package com.example.drrbnicompany.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.drrbnicompany.Models.Category;

import java.util.List;

public class CategoryViewHolder extends AndroidViewModel {

    private Repository repository;

    public CategoryViewHolder(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public void getCategories(MyListener<List<Category>> isSuccessful) {
        repository.getCategories(isSuccessful);
    }


}
