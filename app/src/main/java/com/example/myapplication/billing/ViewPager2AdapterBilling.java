package com.example.myapplication.billing;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myapplication.pattern.PatternDayFragment;
import com.example.myapplication.pattern.PatternMonthFragment;
import com.example.myapplication.pattern.PatternWeekFragment;

public class ViewPager2AdapterBilling extends FragmentStateAdapter {
    public ViewPager2AdapterBilling(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new BillingRealtimeFragment();
            case 1:
                return new BillingMonthlyFragment();
            default:
                return null;
        }
    }
    @Override
    public int getItemCount() {
        return 2;       // 페이지 수
    }
}
