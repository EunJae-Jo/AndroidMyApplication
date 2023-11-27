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
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.helper.HttpHelper;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BillingRealtimeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BillingRealtimeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    BillingService mBillingService ;

    LinearLayout mLinearLayoutV;

    public BillingRealtimeFragment() {
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
    public static BillingRealtimeFragment newInstance(String param1, String param2) {
        BillingRealtimeFragment fragment = new BillingRealtimeFragment();
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
        View v = inflater.inflate(R.layout.fragment_billing_realtime, container, false);

        PieChart pieChart = v.findViewById(R.id.pieChart);

        PieData data = generatePieData();          // MPAndroidChart v3.X 오류 발생
        pieChart.setData(data);
        //pieChart.animateXY(5000, 5000);
        pieChart.getLegend().setEnabled(false);
        pieChart.getDescription().setEnabled(false);

        pieChart.setRotationEnabled(false);
        pieChart.setHoleRadius(50f);        // increase hole radius
        pieChart.setMaxAngle(180.0f);
        pieChart.setRotationAngle(180.0f);
        //pieChart.setCenterTextSize(30);
        //pieChart.setCenterText("Reward Points: 150");//new line

        LinearLayout linearLayoutV = (LinearLayout) v.findViewById(R.id.linearLayoutV);
        mLinearLayoutV = linearLayoutV;

        for(int i=0;i<4;i++)
        {
            LinearLayout linearLayoutH = new LinearLayout(this.getContext());
            TextView month = new TextView(linearLayoutH.getContext());
            month.setText((i+1)+" Month");

            TextView value = new TextView(linearLayoutH.getContext());
            value.setText("₩ 123,123");
            linearLayoutH.addView(month);
            linearLayoutH.addView(value);
            linearLayoutV.addView(linearLayoutH);
        }
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

        int count = 3;

        ArrayList<PieEntry> entries1 = new ArrayList<>();

        for(int i = 0; i < count; i++) {
            //entries1.add(new PieEntry((float) ((Math.random() * 60) + 40), (i+1) + "step\r\n ₩ " + (i+100)));
            entries1.add(new PieEntry((float) ((Math.random() * 60) + 40), (i+1) + "step"));
        }

        PieDataSet ds1 = new PieDataSet(entries1, "");
        ds1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        ds1.setSliceSpace(2f);
        ds1.setValueTextColor(Color.WHITE);
        ds1.setValueTextSize(12f);
        ds1.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                String sValue = "₩" + String.valueOf(value);
                return sValue;
            }
        });
        PieData d = new PieData(ds1);
        return d;
    }

    public void getBillingMonthFromHttp(){
        mBillingService.getMonthDatasThisYearFromHttp((hashMap)-> {

            Map<String, HashMap<String, BigDecimal>> map = hashMap;
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    mLinearLayoutV.removeAllViews();

                    for( Map.Entry<String, HashMap<String, BigDecimal>> entry : map.entrySet() ){
                        String strKey = entry.getKey();
                        HashMap<String, BigDecimal> values = entry.getValue();

                        LinearLayout linearLayoutH = new LinearLayout(getContext());
                        TextView month = new TextView(linearLayoutH.getContext());
                        month.setText(strKey+"\t\t\t");

                        TextView value = new TextView(linearLayoutH.getContext());
                        DecimalFormat fomat = new DecimalFormat("###,###");
                        value.setText(String.format( "%15s 원" , fomat.format(values.get("thisMonthcost"))));
                        //value.setText(String.format("%.2f", values.get("thisMonthcost")));
                        linearLayoutH.addView(month);
                        linearLayoutH.addView(value);

                        mLinearLayoutV.addView(linearLayoutH);
                    }

                }
            });


        });
    }


    private void toast(String message) {
        FragmentActivity activity = getActivity();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

}