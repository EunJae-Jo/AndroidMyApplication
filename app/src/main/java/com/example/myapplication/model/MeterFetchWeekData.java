package com.example.myapplication.model;

import com.github.mikephil.charting.data.BarEntry;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class MeterFetchWeekData {
    public BigDecimal lastMonthUsage;
    public BigDecimal totalCost;
    public BigDecimal totalKWh;
    public Map<String, MeterFetchData> data;

    public MeterFetchWeekData(JsonArray obj){
        data = new HashMap<>();

        if(obj.size() < 1)
            return;

        int idx = 0;
        JsonObject tempJson = (JsonObject) obj.get(idx);

        lastMonthUsage = tempJson.get("lastMonthUsage").getAsBigDecimal();

        JsonObject dataObj = tempJson.get("data").getAsJsonObject();
        Iterator<String> keys = dataObj.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            if (dataObj.get(key) instanceof JsonObject) {
                // do something with jsonObject here

                // Data 입력
                String[] splitData = key.split("-");
                String insertKey = String.format("%s-%s-%s", splitData[0], String.format("%02d", Integer.parseInt(splitData[1])), String.format("%02d", Integer.parseInt(splitData[2])));
                MeterFetchData insertValue = new MeterFetchData(dataObj.get(key).getAsJsonObject());

                data.put(insertKey, insertValue);

            }
        }

        // 정렬.
        Map<String, MeterFetchData> sortedMap = data.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> { throw new AssertionError(); },
                        LinkedHashMap::new
                ));


        // 다 더해야하는 요소
        totalCost = new BigDecimal(0);
        totalKWh = new BigDecimal(0);
        for( Map.Entry<String, MeterFetchData> entry : data.entrySet() ){
            String strKey = entry.getKey();
            MeterFetchData value = entry.getValue();
            totalCost =  totalCost.add(value.cost);
            totalKWh= totalKWh.add(value.kwh);
        }
    }
}
