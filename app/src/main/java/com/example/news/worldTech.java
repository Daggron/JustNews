package com.example.news;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class worldTech extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recyclerView, new worldTechFragment())
                .commit();


    }
}
