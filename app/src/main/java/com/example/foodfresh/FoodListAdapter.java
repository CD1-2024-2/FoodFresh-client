package com.example.foodfresh;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Response;

public class FoodListAdapter extends BaseAdapter {
    ArrayList<FoodDM> foods = new ArrayList<FoodDM>();
    ArrayList<Bitmap> images = new ArrayList<Bitmap>();
    Context context;
    ImageView food_iv;

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
    public void addImage(Bitmap image) {
        images.add(image);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_foodlist, parent, false);
        }
        // 화면에 보여질 데이터를 참조 image, text etc...
        food_iv = convertView.findViewById(R.id.foodList_food_imageview);
        TextView name_tv = convertView.findViewById(R.id.food_name_textview);
        TextView description_tv  = convertView.findViewById((R.id.food_discription_textview));
        ImageView expiry_dot_iv = convertView.findViewById(R.id.food_expiry_dot_imageview);
        TextView expiry_date_tv = convertView.findViewById(R.id.food_expiry_date_textview);

        FoodDM foodDM = foods.get(position); // 현재 식품

        food_iv.setImageBitmap(images.get(position));

        name_tv.setText(foodDM.getName());
        description_tv.setText(foodDM.getDescription());
        expiry_dot_iv.setImageResource(R.drawable.ic_dot_green);

        // 남은 유통기한 계산
        String str_start = foodDM.getRegisteredDate();
        String str_end = foodDM.getExpirationDate();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate startDate = LocalDate.parse(str_start,dtf);
            LocalDate endDate = LocalDate.parse(str_end,dtf);

            long days = startDate.until(endDate, ChronoUnit.DAYS);

            String str = "D";
            if (days < 0) {
                str += Long.toString(days);
                expiry_dot_iv.setImageResource(R.drawable.ic_dot_red);
            }
            else {
                str += "+"+Long.toString(days);
                if (days < 10) {
                    expiry_dot_iv.setImageResource(R.drawable.ic_dot_orange);
                }
            }
            expiry_date_tv.setText(str);
        }

        return convertView;
    }

}
