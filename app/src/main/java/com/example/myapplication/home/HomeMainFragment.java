package com.example.myapplication.home;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.MainActivity;
import com.example.myapplication.MainPage;
import com.example.myapplication.R;
import com.example.myapplication.helper.HttpHelper;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeMainFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeMainFragment() {
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
    public static HomeMainFragment newInstance(String param1, String param2) {
        HomeMainFragment fragment = new HomeMainFragment();
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

    public void suTest(){

        // 데이터 테스트
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                JsonObject result = HttpHelper.getInstance().get("/api/meter/list");//("/api/admin/meters");
                if(result != null) {
                    JsonArray t;
                    try {
                        t = result.get("data").getAsJsonArray();

                        //배열에 있는 제이슨 객체를 받을 임시 제이슨 객체
                        JsonObject tempJson = new JsonObject();
                        for (int i = 0; i < t.size(); i++) { //배열에 있는 제이슨 수많큼 반복한다.
                            tempJson = (JsonObject) t.get(i);
                            Log.d("Su-Test", tempJson.toString()); // 결과 가져오기.
                        }
                        toast("Get Test 완료");
                    } catch (Exception e) {

                    }
                }
            }

        });

        th.start();

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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        suTest();
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home_main, container, false);
        PieChart pieChart = v.findViewById(R.id.pieChart1);
        ArrayList NoOfEmp = new ArrayList();
        NoOfEmp.add(new Entry(945f, 0));
        NoOfEmp.add(new Entry(1040f, 1));
        NoOfEmp.add(new Entry(1133f, 2));
        NoOfEmp.add(new Entry(1240f, 3));
        NoOfEmp.add(new Entry(1369f, 4));
        NoOfEmp.add(new Entry(1487f, 5));
        NoOfEmp.add(new Entry(1501f, 6));
        NoOfEmp.add(new Entry(1645f, 7));
        NoOfEmp.add(new Entry(1578f, 8));
        NoOfEmp.add(new Entry(1695f, 9));
        PieData data = generatePieData();          // MPAndroidChart v3.X 오류 발생
        pieChart.setData(data);
        //pieChart.animateXY(5000, 5000);
        pieChart.getLegend().setEnabled(false);
        pieChart.getDescription().setEnabled(false);

        PieChart energyRateChart = v.findViewById(R.id.energyRateChart);
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
        data = generatePieData();          // MPAndroidChart v3.X 오류 발생
        energyRateChart.setData(data);
        //energyRateChart.animateXY(5000, 5000);
        energyRateChart.getLegend().setEnabled(false);
        energyRateChart.getDescription().setEnabled(false);

        return v;
    }

    protected PieData generatePieData() {

        int count = 4;

        ArrayList<PieEntry> entries1 = new ArrayList<>();

        entries1.add(new PieEntry((float) ((Math.random() * 60) + 40), "Morning"));
        entries1.add(new PieEntry((float) ((Math.random() * 60) + 40), "Afternoon"));
        entries1.add(new PieEntry((float) ((Math.random() * 60) + 40), "Evening"));
        entries1.add(new PieEntry((float) ((Math.random() * 60) + 40), "Midnight"));

        PieDataSet ds1 = new PieDataSet(entries1, "");
        ds1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        ds1.setSliceSpace(2f);
        ds1.setValueTextColor(Color.WHITE);
        ds1.setValueTextSize(12f);

        PieData d = new PieData(ds1);
        return d;
    }

}