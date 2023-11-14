package com.example.myapplication.setting;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingSettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingSettingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingSettingFragment() {
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
    public static SettingSettingFragment newInstance(String param1, String param2) {
        SettingSettingFragment fragment = new SettingSettingFragment();
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
    LabeledSwitch toggle1;
    CheckBox checkBox1,checkBox2,checkBox3,checkBox4;
    ArrayList<CheckBox> checkBoxes = new ArrayList<CheckBox>();
    Spinner spinner;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_setting_setting, container, false);
        toggle1 = v.findViewById(R.id.toogleNotification);
        toggle1.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if(isOn)
                {

                }
            }
        });

        checkBox1 = v.findViewById(R.id.checkBox1);
        checkBox2 = v.findViewById(R.id.checkBox2);
        checkBox3 = v.findViewById(R.id.checkBox3);
        checkBox4 = v.findViewById(R.id.checkBox4);
        checkBoxes.add(checkBox1);checkBoxes.add(checkBox2);checkBoxes.add(checkBox3);checkBoxes.add(checkBox4);

        for(int i=0;i<checkBoxes.size();i++)
        {
            checkBoxes.get(i).setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    if(((CheckBox) view).isChecked())
                    {
                        for(int i=0;i<checkBoxes.size();i++)
                        {
                            if(checkBoxes.get(i) != view)
                            {
                                checkBoxes.get(i).setChecked(false);
                            }
                        }
                    }
                }
            });
        }
        String[] str = getResources().getStringArray(R.array.step);
        spinner = v.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,str);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), str);
        //adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(2);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(spinner.getSelectedItemPosition() > 0){
                    //선택된 항목
                    Log.v("알림",spinner.getSelectedItem().toString()+ "is selected");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        return v;
    }
}