package com.example.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class SelectCurrency extends AppCompatActivity {
    private ArrayList<Currency> optionsCurrency;
    private SelectCurrencyAdapter selectCurrencyAdapter;
    ArrayList<Currency> curList = CurrencyList.getInstance().currencyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_currency);
        setListView();
    }

    private void setListView() {
        ListView selectCurList = (ListView)findViewById(R.id.listSelectCurrency);
        optionsCurrency = new ArrayList<Currency>();
        for(int i=0;i<curList.size();++i){
            if(!curList.get(i).isSelected()){
                optionsCurrency.add(curList.get(i));
            }
        }

        selectCurrencyAdapter = new SelectCurrencyAdapter(this,optionsCurrency);
        selectCurList.setAdapter(selectCurrencyAdapter);
        selectCurList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //send the chosen currency to main activity
                Currency choice = optionsCurrency.get(i);
                int index = curList.indexOf(choice);

                final Intent resultIntent = new Intent();
                resultIntent.putExtra("chosenIndex",index);
                setResult(Activity.RESULT_OK,resultIntent);

                //remove currency from selection list
                choice.setSelected(true);
                optionsCurrency.remove(i);
                selectCurrencyAdapter.notifyDataSetChanged();
                finish();
            }
        });
    }
}