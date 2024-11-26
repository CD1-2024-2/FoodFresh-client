package com.example.foodfresh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class StatisticActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        TextView stat_test_tv = findViewById(R.id.statistic_test_textview);
        // intent 데이터 받아오기 test
        Intent intent = getIntent();
        stat_test_tv.setText("사용자 id: " + intent.getStringExtra("사용자 id"));

    }
}