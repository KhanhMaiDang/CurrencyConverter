package com.example.currencyconverter;

import java.util.ArrayList;

public class CurrencyList {
    private static CurrencyList instance = null;
    public ArrayList<Currency> currencyList;

   private CurrencyList(){
        currencyList = new ArrayList<Currency>();
        currencyList.add(new Currency("USD",23000,"US DOLLAR",R.drawable.us,true));
        currencyList.add(new Currency("EUR",25.890,"EURO",R.drawable.euro,true));
        currencyList.add(new Currency("AUD",15.886,"AUSTRALIAN DOLLAR",R.drawable.aud,false));
        currencyList.add(new Currency("CAD",16.726,"CANADIAN DOLLAR",R.drawable.canada,false));
        currencyList.add(new Currency("CHF",24.082,"SWISS FRANC",R.drawable.swiss,false));
        currencyList.add(new Currency("CNY",3.253,"YUAN RENMINBI",R.drawable.china,false));
        currencyList.add(new Currency("HKD",2.916,"HONGKONG DOLLAR",R.drawable.hongkong,false));
        currencyList.add(new Currency("JPY",211,"YEN",R.drawable.japan,false));
        currencyList.add(new Currency("KRW",16,"KOREAN WON",R.drawable.korea,false));
        currencyList.add(new Currency("SGD",16.285,"SINGAPORE DOLLAR",R.drawable.singapore,false));
    }

    public static CurrencyList getInstance(){
       if(instance==null)
           instance = new CurrencyList();
       return instance;
    }
}



