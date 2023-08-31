package com.example.myapplication.billing;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BillingMonthlyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BillingMonthlyFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BillingMonthlyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MeterDataMainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BillingMonthlyFragment newInstance(String param1, String param2) {
        BillingMonthlyFragment fragment = new BillingMonthlyFragment();
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

    BarChart barChart;
    ExpandableListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_billing_monthly, container, false);

        barChart = v.findViewById(R.id.barChart);
        barChart.getLegend().setEnabled(false);// Legend는 차트의 범례
        barChart.getDescription().setEnabled(false);// chart 밑에 description 표시 유무
        barChart.setTouchEnabled(false); // 터치 유무

        String[] date = {"2018년","2019년","2020년","2021년","2022년","2023년"};
        TableLayout[] tableLayout = new TableLayout[date.length];
        ArrayList<BillingDataGroup> dataList = new ArrayList<BillingDataGroup>();
        listView = (ExpandableListView)v.findViewById(R.id.expandableListView);
        for(int i=0;i<date.length;i++)
        {
            BillingDataGroup temp = new BillingDataGroup(date[i]);
            String[] tmp = {"a,b,c,d","e,f,g,h","i,j,k,l"};
            if(i==0)
                tmp = new String[] {"a,b,c,d"};
            else if (i==1) {
                tmp = new String[]{"a,b,c,d", "e,f,g,h"};
            }
            else if (i==2) {
                tmp = new String[]{"a,b,c,d", "e,f,g,h", "i,j,k,l"};
            }
            else
            {
                tmp = new String[]{"a,b,c,d", "e,f,g,h", "i,j,k,l", "m,n,o,p"};
            }
            temp.data = tmp;
            dataList.add(temp);
        }

        ExpandAdapter adapter = new ExpandAdapter(getActivity().getApplicationContext(),R.layout.billing_monthly_parent,R.layout.billing_monthly_child,dataList,tableLayout);
        //listView.setIndicatorBounds(width-50, width); //이 코드를 지우면 화살표 위치가 바뀐다.
        listView.setAdapter(adapter);

        initChart();
        makeChart();
        return v;
    }

    private String getCurrentDate()
    {
        Calendar calendar = Calendar.getInstance();
        int pYear = calendar.get(Calendar.YEAR);
        int pMonth = calendar.get(Calendar.MONTH) + 1;
        int pDay = calendar.get(Calendar.DAY_OF_MONTH);
        String monthS = ""+pMonth;
        String dayS = ""+pDay;
        if(pMonth<10)
            monthS = "0"+pMonth;
        if(pDay<10)
            dayS = "0"+pDay;
        return pYear + "-" + monthS + "-" + dayS;
    }
    private void initChart(){
        ArrayList<String> xAxisVals = new ArrayList<String>(Arrays.asList("Sun","Mon","Tue","Wed","Thu","Fri","Sat"));
        XAxis xAxis = barChart.getXAxis();
        xAxis.setDrawAxisLine(false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisVals));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        YAxis axisLeft = barChart.getAxisLeft();
        axisLeft.setDrawGridLines(false);
        axisLeft.setDrawAxisLine(false);
        YAxis axisRight = barChart.getAxisRight();
        axisRight.setDrawLabels(false); // label 삭제
        axisRight.setDrawGridLines(false);
        axisRight.setDrawAxisLine(false);
    }
    private void makeChart(){
        BarDataSet barDataSet = new BarDataSet(dataValue(),"data1");
        barDataSet.setColors(new int[] {Color.RED, Color.YELLOW, Color.YELLOW, Color.YELLOW, Color.YELLOW, Color.YELLOW, Color.YELLOW});
        barDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return (String.valueOf(value)) + "kWh";
            }
        });
        BarData barData = new BarData();
        barData.addDataSet(barDataSet);
        barChart.setData(barData);
    }
    private ArrayList<BarEntry> dataValue(){
        ArrayList<BarEntry> dataVals = new ArrayList<>();
        for(int i=0;i<7;i++)
        {
            dataVals.add(new BarEntry(i,i+1));
        }
        return  dataVals;
    }
}