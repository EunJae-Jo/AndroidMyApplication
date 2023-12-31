package com.example.myapplication.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingCustomerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingCustomerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingCustomerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeMainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingCustomerFragment newInstance(String param1, String param2) {
        SettingCustomerFragment fragment = new SettingCustomerFragment();
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

    ListView listView;
    CustomerListAdapter listAdapter;
    TextView textViewCurrentPage;
    ArrayList<CustomerORM> itemArrayList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_setting_customer, container, false);

        listView = v.findViewById(R.id.listView);

        itemArrayList = new ArrayList<CustomerORM>();
        for(int i=1;i<35;i++)
            if(i<20)
                itemArrayList.add(new CustomerORM(i+1000000,"customer"+i,true,"OK"));
            else
                itemArrayList.add(new CustomerORM(i+1000000,"customer"+i,false,"NO"));

        listAdapter = new CustomerListAdapter(getActivity().getApplicationContext(),itemArrayList);
        listView.setAdapter(listAdapter);
        return v;
    }
}