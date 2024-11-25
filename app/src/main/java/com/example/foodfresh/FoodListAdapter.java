package com.example.foodfresh;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class FoodListAdapter extends BaseAdapter {
    ArrayList<FoodListItem> items = new ArrayList<FoodListItem>();
    Context context;


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void addItem(FoodListItem item) {
        items.add(item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context = parent.getContext();
        FoodListItem foodListItem = items.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_foodlist, parent, false);
        }
        // 화면에 보여질 데이터를 참조 image, text etc...
        ImageView food_iv = convertView.findViewById(R.id.food_imageview);
        TextView name_tv = convertView.findViewById(R.id.food_name_textview);
        TextView category_tv  = convertView.findViewById((R.id.food_category_textview));
        ImageView expiry_dot_iv = convertView.findViewById(R.id.food_expiry_dot_imageview);
        TextView expiry_date_iv = convertView.findViewById(R.id.food_expiry_date_textview);

        // 데이터 set
        food_iv.setImageResource(R.drawable.ic_sample_apple);
        name_tv.setText(foodListItem.getName());
        category_tv.setText(foodListItem.getCategory());
        expiry_dot_iv.setImageResource(R.drawable.ic_dot_green);
        expiry_date_iv.setText(foodListItem.getExpiryDate());

        return convertView;
    }
}
