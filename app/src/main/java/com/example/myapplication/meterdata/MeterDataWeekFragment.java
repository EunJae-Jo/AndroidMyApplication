package com.example.myapplication.meterdata;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MeterDataWeekFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeterDataWeekFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MeterDataWeekFragment() {
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
    public static MeterDataWeekFragment newInstance(String param1, String param2) {
        MeterDataWeekFragment fragment = new MeterDataWeekFragment();
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
    TextView editTextStartDate;
    TextView editTextEndDate;
    DatePickerDialog datePickerDialog;
    Switch generalSwitch;
    BarChart barChart;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_meter_data_week, container, false);

        editTextStartDate = v.findViewById(R.id.editTextStartDate);
        editTextStartDate.setText(getCurrentDate());

        editTextStartDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int pYear = calendar.get(Calendar.YEAR);
                int pMonth = calendar.get(Calendar.MONTH);
                int pDay = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String monthS = ""+month;
                        String dayS = ""+day;
                        if(month<10)
                            monthS = "0"+month;
                        if(day<10)
                            dayS = "0"+day;
                        String date = year + "-" + monthS + "-" + dayS;
                        editTextStartDate.setText(date);
                    }
                }, pYear, pMonth, pDay);
                datePickerDialog.show();
            }
        });

        editTextEndDate = v.findViewById(R.id.editTextEndDate);
        editTextEndDate.setText(getCurrentDate());

        editTextEndDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int pYear = calendar.get(Calendar.YEAR);
                int pMonth = calendar.get(Calendar.MONTH);
                int pDay = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String monthS = ""+month;
                        String dayS = ""+day;
                        if(month<10)
                            monthS = "0"+month;
                        if(day<10)
                            dayS = "0"+day;
                        String date = year + "-" + monthS + "-" + dayS;
                        editTextEndDate.setText(date);
                    }
                }, pYear, pMonth, pDay);
                datePickerDialog.show();
            }
        });

        generalSwitch = v.findViewById(R.id.generalSwitch);
        generalSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    generalSwitch.setTypeface(null, Typeface.BOLD);
                else
                    generalSwitch.setTypeface(null, Typeface.NORMAL);
            }
        });

        barChart = v.findViewById(R.id.barChart);
        barChart.getLegend().setEnabled(false);// Legend는 차트의 범례
        barChart.getDescription().setEnabled(false);// chart 밑에 description 표시 유무
        barChart.setTouchEnabled(false); // 터치 유무

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