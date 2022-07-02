package com.example.drrbnicompany;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.drrbnicompany.ViewModels.MyListener;
import com.example.drrbnicompany.ViewModels.Repository;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        Repository repository = new Repository(getApplication());
        repository.getToken(new MyListener<String>() {
            @Override
            public void onValuePosted(String value) {
                repository.updateToken(value);
            }
        });

    }
}