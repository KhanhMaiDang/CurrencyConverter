package com.example.currencyconverter;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;

public class Currency  {
    private String name;
    private double convertRate;
    private String description;
    private int imgRes_id;
    private double amount;
    private boolean isSelected;

    public Currency(String name,double convertRate, String description, int imgResID, boolean isSelected)
    {
        this.name=name;
        this.convertRate = convertRate;
        this.description = description;
        this.imgRes_id = imgResID;
        this.amount=0;
        this.isSelected=isSelected;
    }

    public String getName(){
        return name;
    }

    public double getConvertRate(){
        return convertRate;
    }

    public String getDescription(){
        return description;
    }

    public int getImgRes_id(){
        return imgRes_id;
    }

    public double getAmountMoney(){
        return amount;
    }

    public void setAmountMoney(double inputMoney)
    {

        amount=inputMoney/convertRate;
    }

    public void setSelected(boolean value){
        isSelected = value;
    }

    public boolean isSelected() {
        return isSelected;
    }


}
