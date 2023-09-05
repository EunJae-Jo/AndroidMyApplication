package com.example.myapplication.setting;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingNoticeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingNoticeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingNoticeFragment() {
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
    public static SettingNoticeFragment newInstance(String param1, String param2) {
        SettingNoticeFragment fragment = new SettingNoticeFragment();
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
    NoticeListAdapter listAdapter;
    TextView textViewCurrentPage;
    Button buttonPrevious,buttonNext;
    NoticePaginator p;
    private int currentPage = 0;
    private int totalPages = 0;
    ArrayList<NoticeORM> itemArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_setting_notice, container, false);

        listView = v.findViewById(R.id.listView);
        textViewCurrentPage = v.findViewById(R.id.textViewCurrentPage);
        buttonPrevious = v.findViewById(R.id.buttonPrevious);
        buttonNext = v.findViewById(R.id.buttonNext);

        itemArrayList = new ArrayList<NoticeORM>();
        for(int i=1;i<35;i++)
            itemArrayList.add(new NoticeORM("Notice"+i,"date"+i));

        p = new NoticePaginator(itemArrayList);
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
        listAdapter = new NoticeListAdapter(getActivity().getApplicationContext(),p.getCurrent(page));
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