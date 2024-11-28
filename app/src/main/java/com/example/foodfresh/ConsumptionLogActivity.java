package com.example.foodfresh;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import java.time.LocalDate;

public class ConsumptionLogActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumption_log);
        ListView lv = findViewById(R.id.consumption_listview);

        ConsumptionListAdapter adapter = new ConsumptionListAdapter();
        // 초기 데이터
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            adapter.addItem(new ConsumptionListItem("001", null, LocalDate.of(2024, 9, 10), "양파", "매움", 0, "2.5kg"));
            adapter.addItem(new ConsumptionListItem("002", null, LocalDate.of(2024, 9, 8), "당근", "길다", 1, "1.5kg"));
            adapter.addItem(new ConsumptionListItem("003", null, LocalDate.of(2024, 9, 5), "오렌지", "동그랗다", 2, "2.5kg"));
        }
        lv.setAdapter(adapter);

        Button button = findViewById(R.id.goto_piechart_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ConsumptionLogActivity.this, PieChartActivity.class));
            }
        });
    }
}
