package com.hello.doc;

import android.Manifest;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hello.doc.medicine.MedicinesPage;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Navigation navigation = findViewById(R.id.navigation);
        navigation.inflateMenu(R.menu.menu);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MedicinesPage()).commit();

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFragment = null;

                switch (menuItem.getItemId()) {
                    case R.id.page1:
                        selectedFragment = new MedicinesPage();
                        break;
                    case R.id.page2:
                        selectedFragment = new CalendarPage();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                return true;
            }
        });


        floatingActionButton = findViewById(R.id.floatingActionButton);
        frameLayout = findViewById(R.id.fragment_container);
        floatingActionButton.bringToFront();
        floatingActionButton.invalidate();

        floatingActionButton.setZ(99);
        navigation.setZ(5);
        frameLayout.setZ(1);


        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 2);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Scanner.class));
            }
        });


    }


}
