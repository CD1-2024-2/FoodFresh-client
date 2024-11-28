package com.example.foodfresh;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import android.graphics.drawable.GradientDrawable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ConsumptionListAdapter extends BaseAdapter {
    ArrayList<ConsumptionListItem> list = new ArrayList<ConsumptionListItem>();
    Context context;

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public ConsumptionListItem getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void addItem(ConsumptionListItem item) {
        list.add(item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context = parent.getContext();
        ConsumptionListItem item = list.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_comsumption_list, parent, false);
        }

        TextView date_tv = convertView.findViewById(R.id.date_textview);
        TextView name_tv = convertView.findViewById(R.id.food_name_textview);
        TextView des_tv = convertView.findViewById(R.id.food_description_textview);
        TextView tag_tv = convertView.findViewById(R.id.tag_textview);
        TextView amount_tv = convertView.findViewById(R.id.food_amount_textview);
        ImageView iv = convertView.findViewById(R.id.food_imageview);

        LocalDate date = item.getDate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd");
            date_tv.setText(date.format(formatter));
        }
        name_tv.setText(item.getName());
        des_tv.setText(item.getDescription());
        tag_tv.setText(ConsumptionListItem.tags[item.getTag()]);

        GradientDrawable background = (GradientDrawable) ContextCompat.getDrawable(context, R.drawable.rounded_background);
        background.setColor(ConsumptionListItem.tag_colors[item.getTag()]);
        tag_tv.setBackground(background);

        amount_tv.setText(item.getAmount());

        return convertView;
    }
}
