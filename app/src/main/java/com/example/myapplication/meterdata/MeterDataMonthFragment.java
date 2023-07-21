package com.example.myapplication.meterdata;

import static android.icu.number.NumberRangeFormatter.RangeIdentityFallback.RANGE;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.MyYearMonthPickerDialog;
import com.example.myapplication.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MeterDataMonthFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeterDataMonthFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MeterDataMonthFragment() {
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
    public static MeterDataMonthFragment newInstance(String param1, String param2) {
        MeterDataMonthFragment fragment = new MeterDataMonthFragment();
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
    TextView picker_year_month;
    Switch generalSwitch;
    BarChart barChart;
    LineChart lineChart;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_meter_data_month, container, false);

        picker_year_month = v.findViewById(R.id.picker_year_month);
        picker_year_month.setText(getCurrentDate());

        picker_year_month.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                MyYearMonthPickerDialog pd = new MyYearMonthPickerDialog();
                pd.setListener(d);
                pd.show(getParentFragmentManager(), "YearMonthPickerTest");
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

        lineChart = v.findViewById(R.id.lineChart);
        lineChart.getLegend().setEnabled(false);// Legend는 차트의 범례
        lineChart.getDescription().setEnabled(false);// chart 밑에 description 표시 유무
        lineChart.setTouchEnabled(false); // 터치 유무

        initChart_up();
        makeChart_up();

        initChart_down();
        makeChart_down();
        return v;
    }
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
            //Log.d("YearMonthPickerTest", "year = " + year + ", month = " + monthOfYear + ", day = " + dayOfMonth);
            String monthS = ""+monthOfYear;
            if(monthOfYear<10)
                monthS = "0"+monthOfYear;
            picker_year_month.setText(year + "-" + monthS);
        }
    };
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
        return pYear + "-" + monthS;
    }
    private void initChart_up(){
        ArrayList<String> xAxisVals = new ArrayList<String>(Arrays.asList("07/02","07/03","07/04","07/05","07/06","07/08","07/09"));
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
    private void makeChart_up(){
        BarDataSet barDataSet = new BarDataSet(dataValue(),"data1");
        barDataSet.setDrawValues(false);
        barDataSet.setColors(new int[] {Color.YELLOW, Color.YELLOW, Color.YELLOW, Color.YELLOW, Color.YELLOW, Color.YELLOW, Color.YELLOW});
        BarData barData = new BarData();
        barData.addDataSet(barDataSet);
        barChart.setData(barData);
    }
    private ArrayList<BarEntry> dataValue(){
        ArrayList<BarEntry> dataVals = new ArrayList<>();
        for(int i=0;i<7;i++)
        {
            float val1 = (float) (Math.random()); // 앱1 값
            dataVals.add(new BarEntry(i,val1));
        }
        return  dataVals;
    }

    private void initChart_down(){
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
    private void makeChart_down(){
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