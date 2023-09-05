package com.example.myapplication.setting;

public class CustomerORM {
    private int customerNumber;
    private String customerName;
    private boolean representation;
    private String isOk;

    public CustomerORM(int customerNumber, String customerName, boolean representation, String isOk) {
        this.customerNumber = customerNumber;
        this.customerName = customerName;
        this.representation = representation;
        this.isOk = isOk;
    }

    public int getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(int customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public boolean isRepresentation() {
        return representation;
    }

    public void setRepresentation(boolean representation) {
        this.representation = representation;
    }

    public String getIsOk() {
        return isOk;
    }

    public void setIsOk(String isOk) {
        this.isOk = isOk;
    }
}
