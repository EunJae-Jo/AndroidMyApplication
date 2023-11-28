package com.example.myapplication.model;

import com.google.gson.JsonObject;

public class MeterFetchResult {
    String code;
    String message;

    public MeterFetchResult(JsonObject result){
        code = result.get("code").toString();
        message = result.get("message").toString();
    }
}
