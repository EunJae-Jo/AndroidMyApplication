package com.example.myapplication.pattern;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPager2AdapterPattern extends FragmentStateAdapter {
    public ViewPager2AdapterPattern(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new PatternDayFragment();
            case 1:
                return new PatternWeekFragment();
            case 2:
                return new PatternMonthFragment();
            default:
                return null;
        }
    }
    @Override
    public int getItemCount() {
        return 3;       // 페이지 수
    }
}
