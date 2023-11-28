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
        cost = obj.get("cost").getAsBigDecimal();
        ampareA = obj.get("ampareA").getAsBigDecimal();
        ampareB = obj.get("ampareB").getAsBigDecimal();
        ampareC = obj.get("ampareC").getAsBigDecimal();
        reactiveP = obj.get("reactiveP").getAsBigDecimal();
        ampare = obj.get("ampare").getAsBigDecimal();
        pfA = obj.get("pfA").getAsBigDecimal();
        activeP = obj.get("activeP").getAsBigDecimal();
        voltageThd = obj.get("voltageThd").getAsBigDecimal();
        pfC = obj.get("pfC").getAsBigDecimal();
        voltage = obj.get("voltage").getAsBigDecimal();
        hz = obj.get("hz").getAsBigDecimal();
        pf = obj.get("pf").getAsBigDecimal();
        voltageA = obj.get("voltageA").getAsBigDecimal();
        voltageB = obj.get("voltageB").getAsBigDecimal();
        voltageC = obj.get("voltageC").getAsBigDecimal();
        kwh = obj.get("kwh").getAsBigDecimal();
    }
}
