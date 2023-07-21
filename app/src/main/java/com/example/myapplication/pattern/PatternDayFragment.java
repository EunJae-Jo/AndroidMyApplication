package com.example.myapplication.pattern;

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
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PatternDayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PatternDayFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PatternDayFragment() {
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
    public static PatternDayFragment newInstance(String param1, String param2) {
        PatternDayFragment fragment = new PatternDayFragment();
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
    TextView editTextDate;
    DatePickerDialog datePickerDialog;
    Switch generalSwitch;
    PieChart pieChart;

    LineChart lineChart;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_pattern_day, container, false);

        editTextDate = v.findViewById(R.id.editTextDate);
        editTextDate.setText(getCurrentDate());

        editTextDate.setOnClickListener(new View.OnClickListener(){
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
                        editTextDate.setText(date);
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

        pieChart = v.findViewById(R.id.pieChart);
        ArrayList energyRateChartList = new ArrayList();
        energyRateChartList.add(new Entry(945f, 0));
        energyRateChartList.add(new Entry(1040f, 1));
        energyRateChartList.add(new Entry(1133f, 2));
        energyRateChartList.add(new Entry(1240f, 3));
        energyRateChartList.add(new Entry(1369f, 4));
        energyRateChartList.add(new Entry(1487f, 5));
        energyRateChartList.add(new Entry(1501f, 6));
        energyRateChartList.add(new Entry(1645f, 7));
        energyRateChartList.add(new Entry(1578f, 8));
        energyRateChartList.add(new Entry(1695f, 9));
        PieData data = generatePieData();          // MPAndroidChart v3.X 오류 발생
        pieChart.setData(data);
        //energyRateChart.animateXY(5000, 5000);
        pieChart.getLegend().setEnabled(false);
        pieChart.getDescription().setEnabled(false);

        lineChart = v.findViewById(R.id.lineChart);
        lineChart.getLegend().setEnabled(false);// Legend는 차트의 범례
        lineChart.getDescription().setEnabled(false);// chart 밑에 description 표시 유무
        lineChart.setTouchEnabled(false); // 터치 유무

        initChart_line();
        makeChart_line();

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

    protected PieData generatePieData() {

        int count = 4;

        ArrayList<PieEntry> entries1 = new ArrayList<>();

        for(int i = 0; i < count; i++) {
            entries1.add(new PieEntry((float) ((Math.random() * 60) + 40), "Quarter " + (i+1)));
        }

        PieDataSet ds1 = new PieDataSet(entries1, "Quarterly Revenues 2015");
        ds1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        ds1.setSliceSpace(2f);
        ds1.setValueTextColor(Color.WHITE);
        ds1.setValueTextSize(12f);

        PieData d = new PieData(ds1);
        return d;
    }

    private void initChart_line(){
        ArrayList<String> xAxisVals = new ArrayList<String>(Arrays.asList("07/02","07/03","07/04","07/05","07/06","07/08","07/09"));
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setDrawAxisLine(false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisVals));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        YAxis axisLeft = lineChart.getAxisLeft();
        axisLeft.setDrawGridLines(false);
        axisLeft.setDrawAxisLine(false);
        YAxis axisRight = lineChart.getAxisRight();
        axisRight.setDrawLabels(false); // label 삭제
        axisRight.setDrawGridLines(false);
        axisRight.setDrawAxisLine(false);
    }
    private void makeChart_line(){
        LineDataSet[] dataSet = new LineDataSet[2];
        LineData data = new LineData();
        for(int i=0;i<dataSet.length;i++)
        {
            dataSet[i] = new LineDataSet(dataValue_line(),"data"+i);
            if(i==0)
                dataSet[i].setColor(Color.rgb(255, 155, 155));
            else
                dataSet[i].setColor(Color.rgb(178, 223, 138));
            dataSet[i].setDrawCircles(true);
            dataSet[i].setDrawValues(false);
            //dataSet.setColors(new int[] {Color.YELLOW, Color.YELLOW, Color.YELLOW, Color.YELLOW, Color.YELLOW, Color.YELLOW, Color.YELLOW});

            data.addDataSet(dataSet[i]);
            lineChart.setData(data);
        }
    }
    private ArrayList<Entry> dataValue_line(){
        ArrayList<Entry> dataVals = new ArrayList<>();
        for(int i=0;i<7;i++)
        {
            float val1 = (float) (Math.random()); // 앱1 값
            dataVals.add(new BarEntry(i,val1));
        }
        return  dataVals;
    }
}