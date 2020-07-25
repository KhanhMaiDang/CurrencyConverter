package com.example.currencyconverter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class mainCurrencyListAdapter extends ArrayAdapter<Currency> {
    public mainCurrencyListAdapter(Context context, ArrayList<Currency> currencies) {
        super(context,0 ,currencies);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
        {
            convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.item_layout, null);
        }
        updateRow(convertView,position,(ListView)parent);
        return  convertView;
    }

    private void updateRow(View convertView, int position, ListView parent) {
        Currency fCurrencyInfo = getItem(position);
        DisplayInfo(convertView,fCurrencyInfo);
    }

    private void DisplayInfo(View convertView, Currency fCurrencyInfo) {
        ImageView img = convertView.findViewById(R.id.imgItem);
        img.setImageResource(fCurrencyInfo.getImgRes_id());
        TextView name = convertView.findViewById(R.id.textCurName);
        name.setText(fCurrencyInfo.getName());
        TextView description = convertView.findViewById(R.id.textCurDescription);
        description.setText(fCurrencyInfo.getDescription());
        TextView amount = convertView.findViewById(R.id.textCurAmount);
        amount.setText((String.valueOf(fCurrencyInfo.getAmountMoney())));
    }
}
