package com.example.myapplication.billing;

import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.example.myapplication.helper.HttpHelper;
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
import java.util.function.Consumer;
import java.util.stream.Collectors;


public class BillingService {
    private FragmentActivity mActivity;

    public BillingService(FragmentActivity activity){
        mActivity = activity;
        //HttpHelper.getInstance();
    }

    /**
     * @param year
     * @param month
     * @param func      반환 값.
     *                   https://hbase.tistory.com/78 참고
     */
    public void getBillingMonthFromHttp(String year, String month, Consumer<JsonArray> func){
        String suffixUrl = "/api/meter/fetch/month";

        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String, String> map = new HashMap<>();
                map.put("year", year);
                map.put("month", month);
                JsonObject result = HttpHelper.getInstance().post(suffixUrl, map);
                if(result != null) {
                    JsonArray t;
                    try {
                        t = result.get("data").getAsJsonArray();

                        func.accept(t);

                        toast("/api/meter/fetch/month 완료");
                    } catch (Exception e) {

                    }
                }
            }

        });

        th.start();

    }

    /**
     * @param year
     * @param month
     * @param func      반환 값.
     *                   https://hbase.tistory.com/78 참고
     */
    public void getMeterMonthsFromHttp(String year, String month, Consumer<JsonArray[]> func){
        String suffixUrl = "/api/meter/fetch/month";

        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                JsonArray[] funcArgs = new JsonArray[2];
                Map<String, String> map = new HashMap<>();
                map.put("year", year);
                map.put("month", month);
                JsonObject result = HttpHelper.getInstance().post(suffixUrl, map);
                if(result != null) {
                    JsonArray t;
                    try {
                        funcArgs[0] = result.get("data").getAsJsonArray();

                        //func.accept(t);

                        toast("/api/meter/fetch/month 완료");
                    } catch (Exception e) {

                    }
                }

                Map<String, String> map2 = new HashMap<>();
                if(Integer.parseInt(month) -1 < 1) {
                    map2.put("year", (Integer.parseInt(year)-1) + "" );
                    map2.put("month", "12");
                }else{
                    map2.put("year", year );
                    map2.put("month", (Integer.parseInt(month)-1) + "" );
                }
                JsonObject result2 = HttpHelper.getInstance().post(suffixUrl, map2);
                if(result2 != null) {
                    JsonArray t;
                    try {
                        funcArgs[1] = result2.get("data").getAsJsonArray();

                        //func.accept(t);

                        toast("/api/meter/fetch/month 완료");
                    } catch (Exception e) {

                    }
                }

                func.accept(funcArgs);



            }

        });

        th.start();

    }

    /**
     * BillingMonthlyFragment 사용
     * 
     * @param func
     */
    public void getDayDatasThisWeekFromHttp(Consumer<Map<String, HashMap<String, BigDecimal>>> func){
        String suffixUrl = "/api/meter/fetch/week";

        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, HashMap<String, BigDecimal>> resultData = new HashMap<>();

                String[] days = getDaysOfWeek();
                if(days.length < 1)
                    return;

                String start = days[0];
                String end = days[days.length-1];

                getDayDatasThisWeekFromHttp(start, end, func);
            }

        });

        th.start();

    }


    public void getDayDatasThisWeekFromHttp(String startDate, String endDate, Consumer<Map<String, HashMap<String, BigDecimal>>> func){
        String suffixUrl = "/api/meter/fetch/week";

        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, HashMap<String, BigDecimal>> resultData = new HashMap<>();



                Map<String, String> map = new HashMap<>();
                //map.put("startDate", start);
                //map.put("endDate", end);
                map.put("startDate", startDate);
                map.put("endDate", endDate);
                JsonObject result = HttpHelper.getInstance().post(suffixUrl, map);
                if(result != null) {
                    JsonArray t;
                    try {
                        t = result.get("data").getAsJsonArray();

                        for (int i = 0; i < t.size(); i++) { //배열에 있는 제이슨 수많큼 반복한다.
                            JsonObject tempJson = (JsonObject) t.get(i);


                            // Coast
                            JsonObject dataObj = tempJson.get("data").getAsJsonObject();
                            Iterator<String> keys = dataObj.keySet().iterator();

                            while (keys.hasNext()) {
                                String key = keys.next();
                                if (dataObj.get(key) instanceof JsonObject) {
                                    // do something with jsonObject here
                                    //Log.d("Su-Test", dataObj.get(key).toString()); // 결과 가져오기.


                                    // Data 입력
                                    HashMap<String, BigDecimal> insertData = new HashMap<>();
                                    insertData.put("cost", dataObj.get(key).getAsJsonObject().get("cost").getAsBigDecimal());
                                    insertData.put("kwh", dataObj.get(key).getAsJsonObject().get("kwh").getAsBigDecimal());

                                    String[] splitData = key.split("-");

                                    resultData.put(String.format("%s-%s-%s", splitData[0], String.format("%02d", Integer.parseInt(splitData[1])), String.format("%02d", Integer.parseInt(splitData[2]))), insertData);
                                }
                            }
                        }
                        toast("/api/meter/fetch/week 완료");
                    } catch (Exception e) {

                    }
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


    /**
     * BillingRealtimeFragment 사용.
     * @param func 결과.
     */
    public void getMonthDatasThisYearFromHttp(Consumer<Map<String, HashMap<String, BigDecimal>>> func){
        String suffixUrl = "/api/meter/fetch/month";

        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, HashMap<String, BigDecimal>> resultData = new HashMap<>();

                Calendar cal = Calendar.getInstance();
                int month = cal.get(Calendar.MONTH);
                final int year = cal.get(Calendar.YEAR);
                for (int currentMonth = 0; currentMonth < month+1; currentMonth++) {
                    Map<String, String> map = new HashMap<>();
                    map.put("year", ""+year);
                    map.put("month", ""+(currentMonth+1));
                    JsonObject result = HttpHelper.getInstance().post(suffixUrl, map);
                    if (result != null) {
                        JsonArray t;
                        try {
                            t = result.get("data").getAsJsonArray();

                            for (int i = 0; i < t.size(); i++) { //배열에 있는 제이슨 수많큼 반복한다.
                                JsonObject tempJson = (JsonObject) t.get(i);


                                // Coast
                                BigDecimal totalCost = new BigDecimal(0);
                                JsonObject dataObj = tempJson.get("data").getAsJsonObject();
                                Iterator<String> keys = dataObj.keySet().iterator();

                                while(keys.hasNext()) {
                                    String key = keys.next();
                                    if (dataObj.get(key) instanceof JsonObject) {
                                        // do something with jsonObject here
                                        totalCost =  totalCost.add(dataObj.get(key).getAsJsonObject().get("cost").getAsBigDecimal());
                                        //Log.d("Su-Test", dataObj.get(key).toString()); // 결과 가져오기.
                                    }
                                }

                                // Data 입력
                                HashMap<String, BigDecimal> insertData = new HashMap<>();
                                insertData.put("thisMonthcost", totalCost);
                                insertData.put("thisMonthKwh", tempJson.get("thisMonthKwh").getAsBigDecimal());

                                resultData.put(String.format("%s-%s", year, String.format("%02d", (currentMonth+1))), insertData );

                            }
                        } catch (Exception e) {

                        }
                    }
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
    public  String[] getDaysOfWeek() {
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
