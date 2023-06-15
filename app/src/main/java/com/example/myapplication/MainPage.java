package com.example.myapplication;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.billing.BillingMainFragment;
import com.example.myapplication.home.HomeMainFragment;
import com.example.myapplication.meterdata.MeterDataMainFragment;
import com.example.myapplication.pattern.PatternMainFragment;
import com.example.myapplication.setting.SettingMainFragment;
import com.google.android.material.navigation.NavigationBarView;

public class MainPage extends AppCompatActivity {
    BillingMainFragment billingMainFragment;
    HomeMainFragment homeMainFragment;
    MeterDataMainFragment meterDataMainFragment;
    PatternMainFragment patternMainFragment;
    SettingMainFragment settingMainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        billingMainFragment = new BillingMainFragment();
        homeMainFragment = new HomeMainFragment();
        meterDataMainFragment = new MeterDataMainFragment();
        patternMainFragment = new PatternMainFragment();
        settingMainFragment = new SettingMainFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.containers, homeMainFragment).commit();

        NavigationBarView navigationBarView = findViewById(R.id.bottomNavigationView);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.containers, homeMainFragment).commit();
                    return true;
                } else if (item.getItemId() == R.id.meter) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.containers, meterDataMainFragment).commit();
                    return true;
                } else if (item.getItemId() == R.id.pattern) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.containers, patternMainFragment).commit();
                    return true;
                } else if (item.getItemId() == R.id.billing) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.containers, billingMainFragment).commit();
                    return true;
                } else if (item.getItemId() == R.id.setting) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.containers, settingMainFragment).commit();
                    return true;
                }
                return false;
            }
        });

    }
}
