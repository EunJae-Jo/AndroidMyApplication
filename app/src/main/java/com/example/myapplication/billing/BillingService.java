package com.example.myapplication.billing;

import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.example.myapplication.helper.HttpHelper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;


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


    private void toast(String message) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
