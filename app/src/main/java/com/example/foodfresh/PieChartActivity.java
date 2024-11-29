package com.example.foodfresh;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class PieChartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piechart);

        PieChart pieChart = findViewById(R.id.pie_chart);
        ArrayList<PieEntry> entries = new ArrayList();
        for (String tag: ConsumptionListItem.tags) {
            entries.add(new PieEntry((float)Math.floor(Math.random()*100), tag));
        }
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(ConsumptionListItem.pie_chart_colors);
        dataSet.setDrawValues(false);

        PieData data = new PieData(dataSet);
        pieChart.setData(data);

        pieChart.getDescription().setEnabled(false);
        pieChart.setRotationEnabled(false);
        pieChart.setTouchEnabled(false);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleRadius(40f);
        pieChart.setTransparentCircleRadius(40f);
        pieChart.setEntryLabelTextSize(0f);
        pieChart.animateY(600);

        Legend legend = pieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false);
        legend.setTextSize(16f);

        pieChart.invalidate();

        TableLayout tableLayout = findViewById(R.id.table);
        TableLayout.LayoutParams t_params = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT
        );
        t_params.setMargins(64, 64, 64, 64);
        tableLayout.setLayoutParams(t_params);

        for (int i = 0; i < ConsumptionListItem.tags.length; i++) {
            TableRow tableRow = new TableRow(this);
            TableRow.LayoutParams params = new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            );
            tableRow.setLayoutParams(params);
            for (int j = 0; j < 3; j++) {
                TextView tv = new TextView(this);

                if (j == 0) tv.setText(ConsumptionListItem.tags[i]);
                if (j == 1) tv.setText(Integer.toString((int)Math.floor(Math.random()*100)));
                if (j == 2) tv.setText(String.format("(%.1f", Math.random()*100)+" %)");

                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                tv.setTextColor(Color.WHITE);
                tv.setTypeface(null, android.graphics.Typeface.BOLD);

                tv.setBackgroundColor(Color.LTGRAY);
                if (j != 0) tv.setGravity(android.view.Gravity.RIGHT);

                TableRow.LayoutParams tv_params = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, j == 0 ? 1f : .6f);
                tv_params.setMargins(8, 8, 8, 8);
                tv.setPadding(16, 16, 16, 16);
                tv.setLayoutParams(tv_params);

                tableRow.addView(tv);
            }
            tableLayout.addView(tableRow);
        }
    }
}
