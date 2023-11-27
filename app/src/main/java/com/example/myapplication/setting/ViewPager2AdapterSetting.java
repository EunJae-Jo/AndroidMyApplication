package com.example.myapplication.setting;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myapplication.billing.BillingMonthlyFragment;
import com.example.myapplication.billing.BillingNeighborFragment;
import com.example.myapplication.billing.BillingRealtimeFragment;

public class ViewPager2AdapterSetting extends FragmentStateAdapter {
    public ViewPager2AdapterSetting(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new SettingNoticeFragment();
            //case 1:
            //    return new SettingUseGoalFragment();
            //case 2:
            //    return new SettingProgressiveFragment();
            //case 3:
            //    return new SettingCustomerFragment();
            case 1:
                return new SettingSettingFragment();
            default:
                return null;
        }
    }
    @Override
    public int getItemCount() {
        return 2;       // 페이지 수
    }
}
