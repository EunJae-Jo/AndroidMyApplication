package com.example.myapplication.helper;

import android.util.Log;

import com.example.myapplication.model.MeterFetchDayData;
import com.example.myapplication.model.MeterFetchMonthData;
import com.example.myapplication.model.MeterFetchResult;
import com.example.myapplication.model.MeterFetchWeekData;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpHelper {

    private String id;
    private String pw;

    private final String HOST = "http://193.123.236.54:80";


    private static HttpHelper instance = null;

    //MovieDatabaseManager 싱글톤 패턴으로 구현
    public static HttpHelper getInstance()
    {
        if(instance == null)
        {
            instance = new HttpHelper();
        }

        return instance;
    }

    //==========================================
    // 기본.
    public JsonObject post(String suffixUrl, Map<String, String> params){

        if( id == null && pw == null)
            return null;

        return post(suffixUrl, login( id, pw), params);
    }

    public JsonObject get(String suffixUrl){

        if( id == null && pw == null)
            return null;

        JsonObject result = null;
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();
        String token = login( id, pw);
        Request request;

        if(token == null)
            return result;

        request = new Request.Builder()
                .url(HOST+suffixUrl)
                .addHeader("Authorization", token)
                .get()
                .build();




        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            //Headers responseHeaders = response.headers();
            //for (int i = 0; i < responseHeaders.size(); i++) {
            //String name = responseHeaders.name(i);
            //String value = responseHeaders.value(i);
            //}

            String e = response.body().string();
            result = new Gson().fromJson(e, JsonObject.class);

        }catch(Exception e){
            System.out.println(e);

        }

        return result;
    }

    private JsonObject post(String suffixUrl, String token, Map<String, String> params){
        JsonObject result = null;
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();
        String json = gson.toJson(params);

        Request request;

        if(token == null) {
            request = new Request.Builder()
                    .url(HOST+suffixUrl)
                    .post(RequestBody.create(MediaType.parse("application/json"), json))
                    .build();
        }else{
            request = new Request.Builder()
                    .url(HOST+suffixUrl)
                    .addHeader("Authorization", token)
                    .post(RequestBody.create(MediaType.parse("application/json"), json))
                    .build();

        }


        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            //Headers responseHeaders = response.headers();
            //for (int i = 0; i < responseHeaders.size(); i++) {
            //String name = responseHeaders.name(i);
            //String value = responseHeaders.value(i);
            //}

            String e = response.body().string();
            result = new Gson().fromJson(e, JsonObject.class);

        }catch(Exception e){
            System.out.println(e);

        }

        return result;
    }

    //==========================================
    // 기능

    public String login( String id, String pw){
        Map<String, String> map = new HashMap<>();
        map.put("user_id", id);
        map.put("user_pw", pw);

        //map.put("user_id", "admin");
        //map.put("user_pw", "passw0rd7!");
        JsonObject jobj = post("/api/user/login", null, map);
        if(jobj != null) {
            JsonObject t;
            try {
                t = jobj.get("data").getAsJsonObject();
                this.id = id;
                this.pw = pw;
                return t.get("token").toString().replace("\"","");
            } catch (Exception e) {

            }
        }
        return null;
    }


    /**
     *  TODO : 구현해야합니다.
     * @param date  "2023-11-01"
     * @param func  반환함수.
     */
    public void getMeterFetchDay(String date, Consumer<MeterFetchDayData> func){
        final String suffixUrl = "/api/meter/fetch/week";

        Map<String, String> map = new HashMap<>();
        map.put("date", date);

        JsonObject result = post(suffixUrl, map);
        if(result != null) {
            JsonArray t;
            try {
                t = result.get("data").getAsJsonArray();
                MeterFetchResult rr = new MeterFetchResult(result);
                MeterFetchDayData day = new MeterFetchDayData(result.get("data").getAsJsonArray());

                if (func != null)
                    func.accept(day);

            } catch (Exception e) {
                Log.d("su-test", e.getMessage());
            }
        }
    }

    /**
     *
     * @param startDate "2023-11-01"
     * @param endDate   "2023-11-01"
     * @param func      반환 함수
     */
    public void getMeterFetchWeek(String startDate, String endDate, BiConsumer<MeterFetchResult, MeterFetchWeekData> func){
        final String suffixUrl = "/api/meter/fetch/week";

        Map<String, String> map = new HashMap<>();
        map.put("startDate", startDate);
        map.put("endDate", endDate);

        JsonObject result = post(suffixUrl, map);
        if(result != null) {
            JsonArray t;
            try {
                t = result.get("data").getAsJsonArray();
                MeterFetchResult rr = new MeterFetchResult(result);
                MeterFetchWeekData week = new MeterFetchWeekData(result.get("data").getAsJsonArray());

                if (func != null)
                    func.accept(rr, week);

            } catch (Exception e) {
                Log.d("su-test", e.getMessage());
            }
        }
    }

    /**
     * 
     * @param year      "2023"
     * @param month     "3"
     * @param func      반환함수
     */
    public void getMeterFetchMonth(String year, String month, BiConsumer<MeterFetchResult, MeterFetchMonthData> func){
        final String suffixUrl = "/api/meter/fetch/month";
        Map<String, String> map = new HashMap<>();
        map.put("year", year);
        map.put("month", month);

        JsonObject result = HttpHelper.getInstance().post(suffixUrl, map);
        if (result != null) {
            JsonArray t;
            try {
                t = result.get("data").getAsJsonArray();
                MeterFetchResult rr = new MeterFetchResult(result);
                MeterFetchMonthData monthData = new MeterFetchMonthData(result.get("data").getAsJsonArray());

                if (func != null)
                    func.accept(rr, monthData);
            } catch (Exception e) {

            }
        }
    }



}
