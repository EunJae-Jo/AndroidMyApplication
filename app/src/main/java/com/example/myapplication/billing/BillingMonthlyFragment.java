package com.example.myapplication.billing;

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
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.model.MeterFetchData;
import com.example.myapplication.model.MeterFetchResult;
import com.example.myapplication.model.MeterFetchWeekData;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.gson.JsonObject;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

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

    BarChart mBarChart;
    ExpandableListView mListView;
    private BillingService mBillingService;

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
    @Override
    public void onPause() {
        super.onPause();
        //Fragment 가려질 때 처리
        Log.d("Su-Test", "onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        //Fragment 보여질 때 처리
        Log.d("Su-Test", "onResume");

        getBillingMonthFromHttp();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBillingService = new BillingService(getActivity());
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_billing_monthly, container, false);

        mBarChart = v.findViewById(R.id.barChart);
        mBarChart.getLegend().setEnabled(false);// Legend는 차트의 범례
        mBarChart.getDescription().setEnabled(false);// chart 밑에 description 표시 유무
        mBarChart.setTouchEnabled(false); // 터치 유무

        String[] date = {"2018년","2019년","2020년","2021년","2022년","2023년"};
        TableLayout[] tableLayout = new TableLayout[date.length];
        ArrayList<BillingDataGroup> dataList = new ArrayList<BillingDataGroup>();
        mListView = (ExpandableListView)v.findViewById(R.id.expandableListView);
        for(int i=0;i<date.length;i++)
        {
            BillingDataGroup temp = new BillingDataGroup(date[i]);
            String[] tmp = {"a,b,c,d","e,f,g,h","i,j,k,l"};
            if(i==0)
                tmp = new String[] {"Jan,2step,289,44320","Feb,2step,306,44920","Mar,2step,317,44320","Apr,2step,344,48320"};
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
        mListView.setAdapter(adapter);

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
        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setDrawAxisLine(false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisVals));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        YAxis axisLeft = mBarChart.getAxisLeft();
        axisLeft.setDrawGridLines(false);
        axisLeft.setDrawAxisLine(false);
        YAxis axisRight = mBarChart.getAxisRight();
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
        mBarChart.setData(barData);
    }
    private ArrayList<BarEntry> dataValue(){
        ArrayList<BarEntry> dataVals = new ArrayList<>();
        for(int i=0;i<7;i++)
        {
            dataVals.add(new BarEntry(i,i+1));
        }
        return  dataVals;
    }



    public void getBillingMonthFromHttp(){

        // TODO 테스트 끝나면 바꿀것.
        //mBillingService.getDayDatasThisWeekFromHttp((resultClass, dataClass)-> {
        mBillingService.getDayDatasThisWeekFromHttp("2023-11-06", "2023-11-12", (resultClass, dataClass)-> {
            MeterFetchResult result = resultClass;
            MeterFetchWeekData data = dataClass;
            if( data == null || data.data.size() == 0 )
                return;

            //배열에 있는 제이슨 객체를 받을 임시 제이슨 객체
            Map<String, MeterFetchData> map = data.data;

            getActivity().runOnUiThread(new Runnable() {
                public void run() {

                    // --------------------------------------
                    // Text Update
                    ArrayList<BillingDataGroup> dataList = new ArrayList<BillingDataGroup>();
                    for( Map.Entry<String, MeterFetchData> entry : map.entrySet() ){
                        String strKey = entry.getKey();
                        MeterFetchData value = entry.getValue();
                        String[] tmp = {String.format("%s,%s", value.kwh.setScale(1, BigDecimal.ROUND_HALF_UP), value.cost.setScale(1, BigDecimal.ROUND_HALF_UP))};
                        BillingDataGroup temp = new BillingDataGroup(strKey);
                        //String[] tmp = {"a,b","e,f","i,j"};

                        temp.data = tmp;
                        dataList.add(temp);
                    }

                    String[] date = map.keySet().toArray(new String[map.size()]);
                    TableLayout[] tableLayout = new TableLayout[date.length];

                    ExpandAdapter adapter = new ExpandAdapter(getActivity().getApplicationContext(),R.layout.billing_monthly_parent,R.layout.billing_monthly_child,dataList,tableLayout);
                    //listView.setIndicatorBounds(width-50, width); //이 코드를 지우면 화살표 위치가 바뀐다.
                    mListView.setAdapter(adapter);

                    // --------------------------------------
                    // Bar Chart Update
                    ArrayList<BarEntry> dataVals = new ArrayList<>();
                    int i = 0;
                    for( Map.Entry<String, MeterFetchData> entry : map.entrySet() ){
                        String strKey = entry.getKey();
                        MeterFetchData value = entry.getValue();
                        final long val = value.cost.setScale(0, BigDecimal.ROUND_HALF_UP).longValue()/1000;

                        dataVals.add(new BarEntry(i++, val));

                    }

                    BarDataSet barDataSet = new BarDataSet(dataVals,"data1");
                    barDataSet.setColors(new int[] {Color.RED, Color.YELLOW, Color.YELLOW, Color.YELLOW, Color.YELLOW, Color.YELLOW, Color.YELLOW});
                    barDataSet.setValueFormatter(new ValueFormatter() {
                        @Override
                        public String getFormattedValue(float value) {
                            DecimalFormat fomat = new DecimalFormat("###,###");
                            return String.valueOf(fomat.format((int)value)) + "천원";
                        }
                    });
                    BarData barData = new BarData();
                    barData.addDataSet(barDataSet);
                    mBarChart.setData(barData);

                    // bar chart refresh
                    mBarChart.notifyDataSetChanged();
                    mBarChart.invalidate();

                }
            });
        });
    }

}