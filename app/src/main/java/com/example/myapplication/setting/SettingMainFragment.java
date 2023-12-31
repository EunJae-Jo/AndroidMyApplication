package com.example.myapplication.setting;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.pattern.ViewPager2AdapterPattern;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingMainFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingMainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingMainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingMainFragment newInstance(String param1, String param2) {
        SettingMainFragment fragment = new SettingMainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_setting_main, container, false);
        ViewPager2AdapterSetting viewPager2Adapter
                = new ViewPager2AdapterSetting(getChildFragmentManager(), getLifecycle());
        ViewPager2 viewPager2 = v.findViewById(R.id.viewPager);
        viewPager2.setAdapter(viewPager2Adapter);
        viewPager2.setSaveEnabled(false);

        //=== TabLayout기능 추가 부분 ============================================
        TabLayout tabLayout = v.findViewById(R.id.tabLayout);
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if(position == 0)
                {
                    tab.setText("NOTICE");
                }  else {
                    tab.setText("SETTING");
                }
            }
        }).attach();
        return v;
    }
}