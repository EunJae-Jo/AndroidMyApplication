package com.example.myapplication.meterdata;

import androidx.fragment.app.FragmentActivity;

import com.example.myapplication.helper.HttpHelper;
import com.example.myapplication.model.MeterFetchMonthData;
import com.example.myapplication.model.MeterFetchResult;
import com.example.myapplication.model.MeterFetchWeekData;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class MeterDataService {
    private FragmentActivity mActivity;

    public MeterDataService(FragmentActivity activity){
        mActivity = activity;
        //HttpHelper.getInstance();
    }


    /**
     * @param year
     * @param month
     * @param func      반환 값.
     *                   https://hbase.tistory.com/78 참고
     */
    public void getMeterMonthsFromHttp(String year, String month, BiConsumer<MeterFetchMonthData, MeterFetchMonthData> func){
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                final MeterFetchMonthData[] datas = new MeterFetchMonthData[2];

                // 현재
                HttpHelper.getInstance().getMeterFetchMonth(year, month, (resultClass, dataClass)->{
                    datas[0] = dataClass;
                });

                // 이전
                String argYear;
                String argMonth;
                if(Integer.parseInt(month) -1 < 1) {
                    argYear = (Integer.parseInt(year)-1) + "" ;
                    argMonth = "12";
                }else{
                    argYear = year;
                    argMonth = (Integer.parseInt(month)-1) + "" ;
                }
                HttpHelper.getInstance().getMeterFetchMonth(argYear, argMonth, (resultClass, dataClass)->{
                    datas[1] = dataClass;
                });


                func.accept(datas[1], datas[0]);



            }

        });

        th.start();

    }
}
