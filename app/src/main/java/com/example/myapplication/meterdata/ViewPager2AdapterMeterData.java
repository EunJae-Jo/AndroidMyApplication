package com.example.myapplication.meterdata;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPager2AdapterMeterData extends FragmentStateAdapter {
    public ViewPager2AdapterMeterData(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
           // case 0:
           //     return new MeterDataDayFragment();
            case 0:
                return new MeterDataWeekFragment();
            case 1:
                return new MeterDataMonthFragment();
            default:
                return null;
        }
    }
    @Override
    public int getItemCount() {
        return 2;       // 페이지 수
    }
}
