package com.example.drrbnicompany.ViewModels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import com.example.drrbnicompany.Models.Category;
import com.example.drrbnicompany.Models.Student;
import java.util.List;

public class CategoryViewModel extends AndroidViewModel {

    private Repository repository;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public void getCategories(MyListener<List<Category>> isSuccessful) {
        repository.getCategories(isSuccessful);
    }

    public void getStudentsByMajor(String major, MyListener<List<Student>> isSuccessful, MyListener<String> isFailure) {
        repository.getStudentsByMajor(major, isSuccessful, isFailure);
    }

    public void getStateActiveAccount(String email, MyListener<Boolean> isSuccessful) {
        repository.getStateActiveAccount(email, isSuccessful);
    }

    public void signOut() {
        repository.signOut();
    }


}
