package com.example.foodfresh;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class LineChartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linechart);

        LineChart lineChart = findViewById(R.id.line_chart);

        ArrayList<Entry> entries = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                entries.add(new Entry(i, (float)Math.random()*100));
            }
        }

        LineDataSet dataSet = new LineDataSet(entries, "");
        dataSet.setColor(Color.parseColor("#ff28afc7"));
        dataSet.setValueTextColor(Color.parseColor("#ff28afc7"));
        dataSet.setLineWidth(2f);
        dataSet.setValueTextSize(12f);
        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.format("%.1f", value);
            }
        });
        dataSet.setDrawCircles(false);
        dataSet.setDrawValues(true);

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setDrawAxisLine(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setGridLineWidth(1f);
        xAxis.setGridColor(Color.LTGRAY);
        xAxis.setDrawGridLinesBehindData(true);

        xAxis.setTextColor(Color.GRAY);
        xAxis.setTextSize(14f);
        xAxis.setTypeface(Typeface.DEFAULT_BOLD);

        xAxis.setValueFormatter(new IndexAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    return DateTimeFormatter.ofPattern("MM.dd").format(LocalDate.now().plusDays((long)value-30));
                }
                return "";
            }
        });

        lineChart.getAxisLeft().setEnabled(false);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getLegend().setEnabled(false);
        lineChart.getDescription().setEnabled(false);
        lineChart.setPadding(16, 16, 16, 16);
        lineChart.setTouchEnabled(false);
        lineChart.setExtraOffsets(30f, 0f, 30f, 30f);

        lineChart.invalidate();
    }
}
