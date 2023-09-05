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
 * Use the {@link SettingUseGoalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingUseGoalFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingUseGoalFragment() {
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
    public static SettingUseGoalFragment newInstance(String param1, String param2) {
        SettingUseGoalFragment fragment = new SettingUseGoalFragment();
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
    UseGoalListAdapter listAdapter;
    TextView textViewCurrentPage;
    Button buttonPrevious,buttonNext;
    UseGoalPaginator p;
    private int currentPage = 0;
    private int totalPages = 0;
    ArrayList<UseGoalORM> itemArrayList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_setting_usegoal, container, false);

        listView = v.findViewById(R.id.listView);
        textViewCurrentPage = v.findViewById(R.id.textViewCurrentPage);
        buttonPrevious = v.findViewById(R.id.buttonPrevious);
        buttonNext = v.findViewById(R.id.buttonNext);

        itemArrayList = new ArrayList<UseGoalORM>();
        for(int i=1;i<35;i++)
            itemArrayList.add(new UseGoalORM("UseGoal"+i,"content"+i,"date"+i));

        p = new UseGoalPaginator(itemArrayList);
        totalPages = p.getTotalPages();

        bindData(currentPage);
        buttonPrevious.setEnabled(false);

        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPage -= 1 ;
                bindData(currentPage);
                toggleButtons();
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPage += 1 ;
                bindData(currentPage);
                toggleButtons();
            }
        });
        return v;
    }
    private void bindData(int page){
        listAdapter = new UseGoalListAdapter(getActivity().getApplicationContext(),p.getCurrent(page));
        listView.setAdapter(listAdapter);
        textViewCurrentPage.setText((String.valueOf(currentPage+1)));
    }
    private void toggleButtons() {
        if (totalPages <= 1)
        {
            buttonPrevious.setEnabled(false);
            buttonNext.setEnabled(false);
        } else if (currentPage == totalPages) {
            buttonPrevious.setEnabled(true);
            buttonNext.setEnabled(false);
        } else if (currentPage == 0) {
            buttonPrevious.setEnabled(false);
            buttonNext.setEnabled(true);
        } else if (currentPage >=1 && currentPage <= totalPages) {
            buttonPrevious.setEnabled(true);
            buttonNext.setEnabled(true);
        }
    }
}