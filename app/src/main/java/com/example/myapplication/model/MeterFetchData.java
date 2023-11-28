package com.example.myapplication.model;

import com.google.gson.JsonObject;

import java.math.BigDecimal;

public class MeterFetchData {
    public BigDecimal cost;
    public BigDecimal ampareA;
    public BigDecimal ampareB;
    public BigDecimal ampareC;
    public BigDecimal reactiveP;
    public BigDecimal ampare;
    public BigDecimal pfA;
    public BigDecimal activeP;
    public BigDecimal voltageThd;
    public BigDecimal pfC;
    public BigDecimal pfB;
    public BigDecimal voltage;
    public BigDecimal hz;
    public BigDecimal pf;
    public BigDecimal voltageA;
    public BigDecimal voltageB;
    public BigDecimal voltageC;
    public BigDecimal kwh;

    public MeterFetchData(JsonObject obj){
        cost = getData(obj,"cost");
        ampareA = getData(obj,"ampareA");
        ampareB = getData(obj, "ampareB");
        ampareC = getData(obj, "ampareC");
        reactiveP = getData(obj, "reactiveP");
        ampare = getData(obj, "ampare");
        pfA = getData(obj, "pfA");
        activeP = getData(obj, "activeP");
        voltageThd = getData(obj, "voltageThd");
        pfC = getData(obj, "pfC");
        voltage = getData(obj, "voltage");
        hz = getData(obj, "hz");
        pf = getData(obj, "pf");
        voltageA = getData(obj, "voltageA");
        voltageB = getData(obj, "voltageB");
        voltageC = getData(obj, "voltageC");
        kwh = getData(obj, "kwh");
    }

    private BigDecimal getData(JsonObject obj, String name){
        try{
            return obj.get(name).getAsBigDecimal();
        }catch(Exception e){

        }

        return null;
    }
}
