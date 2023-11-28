package com.example.myapplication.billing;

import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.example.myapplication.helper.HttpHelper;
import com.example.myapplication.model.MeterFetchMonthData;
import com.example.myapplication.model.MeterFetchResult;
import com.example.myapplication.model.MeterFetchWeekData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;


public class BillingService {
    private FragmentActivity mActivity;

    public BillingService(FragmentActivity activity){
        mActivity = activity;
        //HttpHelper.getInstance();
    }

    /**
     * BillingMonthlyFragment 사용
     * 오늘을 기준으로 이번주
     * @param func
     */
    public void getDayDatasThisWeekFromHttp(BiConsumer<MeterFetchResult, MeterFetchWeekData> func){

        HashMap<String, HashMap<String, BigDecimal>> resultData = new HashMap<>();

        String[] days = getDaysOfWeek();
        if(days.length < 1)
            return;

        String start = days[0];
        String end = days[days.length-1];

        getDayDatasThisWeekFromHttp(start, end, func);
        //getDayDatasThisWeekFromHttp("2023-11-06", "2023-11-12", func);

    }


    public void getDayDatasThisWeekFromHttp(String startDate, String endDate, BiConsumer<MeterFetchResult, MeterFetchWeekData>  func){
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, HashMap<String, BigDecimal>> resultData = new HashMap<>();

                HttpHelper.getInstance().getMeterFetchWeek(startDate, endDate,  (resultClass, dataClass)->{
                    if (func != null)
                        func.accept(resultClass, dataClass);
                });
            }

        });

        th.start();

    }


    /**
     * BillingRealtimeFragment 사용.
     *
     * 결과 값.
     * {
     *     {
     *          key     : "2023-01"
     *          value   : HashMap<String, BigDecimal>
     *                      ㄴ   thisMonthcost
     *                      ㄴ   thisMonthKwh
     *     },{
     *           key     : "2023-02"
     *           value   : HashMap<String, BigDecimal>
     *                       ㄴ   thisMonthcost
     *                       ㄴ   thisMonthKwh
     *     },
     *
     * }
     *
     * @param func 결과.
     */
    public void getMonthDatasThisYearFromHttp(Consumer<Map<String, HashMap<String, BigDecimal>>> func){

        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, HashMap<String, BigDecimal>> resultData = new HashMap<>();

                Calendar cal = Calendar.getInstance();
                int month = cal.get(Calendar.MONTH);
                final int year = cal.get(Calendar.YEAR);
                for (int currentMonth = 0; currentMonth < month+1; currentMonth++) {
                    final int thisMonth = (currentMonth+1);

                    HttpHelper.getInstance().getMeterFetchMonth(""+year, ""+thisMonth, (resultClass, dataClass)->{
                        // Data 입력
                        HashMap<String, BigDecimal> insertData = new HashMap<>();
                        insertData.put("thisMonthcost", dataClass.totalCost);
                        insertData.put("thisMonthKwh", dataClass.thisMonthKwh);

                        resultData.put(String.format("%s-%s", year, String.format("%02d", thisMonth)), insertData );
                    });
                }

                // 정렬.
                Map<String, HashMap<String, BigDecimal>> sortedMap = resultData.entrySet().stream()
                        .sorted(Map.Entry.comparingByKey())
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (a, b) -> { throw new AssertionError(); },
                                LinkedHashMap::new
                        ));

                try {
                    if (func != null)
                        func.accept(sortedMap);
                }catch(Exception e){
                    Log.d("su-test", e.getMessage());
                }
            }

        });

        th.start();

    }


    private void toast(String message) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * https://living-only-today.tistory.com/102
     * 이번 주 날짜들 return
     * @param dateStr
     * @return
     */
    private  String[] getDaysOfWeek() {
        String[] arrYMD = new String[7];

        Calendar cal = Calendar.getInstance();

        int inYear = cal.get(cal.YEAR);
        int inMonth = cal.get(cal.MONTH);
        int inDay = cal.get(cal.DAY_OF_MONTH);

        int yoil = cal.get(cal.DAY_OF_WEEK); //요일나오게하기(숫자로)
        if(yoil != 1){   //해당요일이 일요일이 아닌경우
            yoil = yoil-2;
        }else{           //해당요일이 일요일인경우
            yoil = 7;
        }
        inDay = inDay-yoil;

        for(int i = 0; i < 7;i++){
            cal.set(inYear, inMonth, inDay+i);  //
            String y = Integer.toString(cal.get(cal.YEAR));
            String m = Integer.toString(cal.get(cal.MONTH)+1);
            String d = Integer.toString(cal.get(cal.DAY_OF_MONTH));
            if(m.length() == 1) m = "0" + m;
            if(d.length() == 1) d = "0" + d;

            arrYMD[i] = String.format("%s-%s-%s",y, m, d);
            //arrYMD[i] = m +"."+d;
        }

        return arrYMD;
    }
}
