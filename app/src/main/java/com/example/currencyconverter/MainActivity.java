package com.example.currencyconverter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private int nextId;
    private String currentInput;
    private ArrayList<Currency> targetCurrency;
    private mainCurrencyListAdapter mainCurListAdapter;
    private int REQUEST_RESULT = 1;
    private ArrayList<Currency> originalCurList = CurrencyList.getInstance().currencyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null){
           currentInput="0";
        }
        else{
            currentInput= savedInstanceState.getString("current input");
        }
        TextView inputField = (TextView) findViewById(R.id.inputField);
        TextView VND=(TextView)findViewById(R.id.editTextVND);

        inputField.setText(currentInput);
        VND.setText(currentInput);
        setMainListView();
        setButtonAdd();
        createManyButtons();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        outState.putString("current input",currentInput);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode== REQUEST_RESULT)
        {
            if(resultCode==RESULT_OK)
            {
                int index = data.getIntExtra("chosenIndex",-2);
                if(index<0) {
                    Log.d("Error","error");
                }
                else
                {
                    Currency currency = originalCurList.get(index);
                    targetCurrency.add(currency);
                    TextView inputField = (TextView)findViewById(R.id.inputField);
                    String currentInputString = inputField.getText().toString();
                    if(checkInputCurrency(currentInputString)){
                        double amount = Double.parseDouble(currentInputString);
                        currency.setAmountMoney(amount);
                    }
                    mainCurListAdapter.notifyDataSetChanged();
                }

            }
        }
    }

    private void setMainListView() {
        final ListView mainCurList = (ListView)findViewById(R.id.listMainCurrency);
        targetCurrency = new ArrayList<Currency>();


        for(int i=0;i<originalCurList.size();++i){
            if(originalCurList.get(i).isSelected()){
                targetCurrency.add(originalCurList.get(i));
            }
        }

        mainCurListAdapter = new mainCurrencyListAdapter(this,targetCurrency);
        mainCurList.setAdapter(mainCurListAdapter);
        mainCurList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                targetCurrency.get(i).setSelected(false);
                targetCurrency.remove(i);
                mainCurListAdapter.notifyDataSetChanged();
            }
        });
    }

    private void setButtonAdd() {
        Button button = (Button)findViewById(R.id.buttonAdd);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,SelectCurrency.class);
                startActivityForResult(intent,REQUEST_RESULT);

            }
        });
    }

    private void createManyButtons() {
        GridLayout gridNum = (GridLayout)findViewById(R.id.gridNumber) ;
        GridLayout gridOp = (GridLayout)findViewById(R.id.gridOp);
        String[] labelsNum =  {
                "7", "8", "9",
                "4","5","6",
                "1","2","3",
                ".", "0","="};
        String[] labelsOp = {"Bksp","%","*","-","+"};
        for(int i=0;i<labelsNum.length;++i)
        {
            Button button = createButton(labelsNum[i]);
            gridNum.addView(button);
        }
        for(int i=0;i<labelsOp.length;++i)
        {
            Button button = createButton(labelsOp[i]);
            gridOp.addView(button);
        }
    }

    private View.OnClickListener input = new View.OnClickListener(){
        String operator="";
        @Override
        public void onClick(View view) {
            int id = view.getId();
            TextView inputField = (TextView) findViewById(R.id.inputField);
            TextView VND=(TextView)findViewById(R.id.editTextVND);
            //TextView USD=(TextView)findViewById(R.id.editTextUSD);
            inputField.setTextColor(Color.parseColor("#000000"));
            //USD.setTextColor(Color.parseColor("#000000"));
            VND.setTextColor(Color.parseColor("#000000"));
            Button v = (Button)view;

            String currentString = inputField.getText().toString();
            //String convertedCurrency ="";
            String buttonClickedString = v.getText().toString();


            if(buttonClickedString=="Bksp")
            {
                if(currentString.isEmpty())
                    currentString="";
                else
                    currentString = currentString.substring(0,currentString.length()-1);
            }
            else
            {
                if(buttonClickedString=="0" && currentString.isEmpty())
                {
                    currentString = "";
                }
                else {
                    if(buttonClickedString=="=") {
                        Log.d("a",operator);
                            currentString = processEquation(currentString, operator);
                            operator="";
                            VND.setText(currentString);
                            currentInput=currentString;
                            boolean inputValid = checkInputCurrency(currentString);
                            if(!currentString.isEmpty() && inputValid)
                                updateTargetCurrency(currentString);
                    }
                    //if(buttonClickedString =="+" || buttonClickedString=="-"||buttonClickedString=="*"||buttonClickedString=="/")
                    else {
                        if(currentString=="0"){
                            currentString="";
                        }
                        currentString += buttonClickedString;
                        if(buttonClickedString =="+" || buttonClickedString=="-"||buttonClickedString=="*"||buttonClickedString=="%")
                            operator = buttonClickedString;
                    }
                }
            }
            if(currentString == "Invalid operation")
            {
                inputField.setTextColor(Color.parseColor("#FF0000"));
                VND.setTextColor(Color.parseColor("#FF0000"));
            }
            inputField.setText(currentString);

        }
    };

    private void updateTargetCurrency(String currentString) {

            Double inputMoney = Double.parseDouble(currentString);
            for (int i = 0; i < targetCurrency.size(); ++i) {
                targetCurrency.get(i).setAmountMoney(inputMoney);
            }
            mainCurListAdapter.notifyDataSetChanged();
    }

    private Button createButton(String label) {
        Button button = new Button(this);
        button.setText(label);
        button.setId(getNextAvailableId());
        button.setOnClickListener(input);
        return button;
    }

    private int getNextAvailableId() {
        return ++nextId;
    }

    private boolean checkInputCurrency(String currentString) {
        try {
            Double tmp = Double.parseDouble(currentString);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    private String processEquation(String currentString,String operator) {

        if(operator.isEmpty())
            return currentString;

        Double op1=0.0, op2=0.0;
        int pos1=0, pos2 = 0;
        for(int i=0;i<currentString.length();++i)
        {
            if(currentString.charAt(i) >= '0' && currentString.charAt(i)<='9' || currentString.charAt(i)=='.')
                ++pos1;
            else
                break;
        }
        pos2=pos1+1;
        String tmp1 = currentString.substring(0,pos1);
        String tmp2 = currentString.substring(pos2,currentString.length());
        try {
            op1 = Double.parseDouble(tmp1);
            op2 = Double.parseDouble(tmp2);
        }
        catch (Exception e)
        {
            return "Invalid operation";
        }
        Log.d("pos1",String.valueOf(pos1));
        Log.d("op1",String.valueOf(op1));
        Log.d("op2",String.valueOf(op2));
        if(operator=="+")
            return String.valueOf(op1+op2);
        if(operator=="-")
            return String.valueOf(op1-op2);
        if(operator=="*")
            return String.valueOf(op1*op2);
        if(operator=="%" && op2!=0.0)
            return String.valueOf(op1/op2);
        else
            return "Invalid operation";
    }
}