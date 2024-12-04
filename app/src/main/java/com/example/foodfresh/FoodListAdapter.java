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

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FoodListAdapter extends BaseAdapter {
    ArrayList<FoodDM> foods = new ArrayList<FoodDM>();
    Context context;

    private String user_id;
    private String refrig_id;

    public FoodListAdapter(String user_id, String refrig_id) {
        this.user_id = user_id;
        this.refrig_id = refrig_id;
    }

    @Override
    public int getCount() {
        return foods.size();
    }

    @Override
    public FoodDM getItem(int i) {
        return foods.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void addItem(FoodDM item) {
        foods.add(item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_foodlist, parent, false);
        }
        // 화면에 보여질 데이터를 참조 image, text etc...
        ImageView food_iv = convertView.findViewById(R.id.food_imageview);
        TextView name_tv = convertView.findViewById(R.id.food_name_textview);
        TextView description_tv  = convertView.findViewById((R.id.food_discription_textview));
        ImageView expiry_dot_iv = convertView.findViewById(R.id.food_expiry_dot_imageview);
        TextView expiry_date_iv = convertView.findViewById(R.id.food_expiry_date_textview);


        FoodDM foodDM = foods.get(position); // 현재 식품

        // 남은 유통기한 계산
        long now = System.currentTimeMillis();
        Date temp_date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str_today = sdf.format(temp_date);
        String str_expirydate = foodDM.getExpirationDate();

        System.out.println("ㅎㅎ:"+str_today);
        System.out.println("ㅈㅅㅣ:"+str_expirydate);


        // 데이터 set
        food_iv.setImageResource(R.drawable.ic_sample_apple);
        name_tv.setText(foodDM.getName());
        description_tv.setText(foodDM.getDescription());
        expiry_dot_iv.setImageResource(R.drawable.ic_dot_green);

//        expiry_date_iv.setText(foodDM.getExpiryDate());

        return convertView;
    }
}
