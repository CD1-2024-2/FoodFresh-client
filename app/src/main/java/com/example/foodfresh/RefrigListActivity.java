package com.example.foodfresh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class RefrigListActivity extends AppCompatActivity {
    private ListView refrig_lv;
    private RefrigListAdapter refrig_adapter;
    private Button refrig_add_btn;
    private Button goto_statistic_btn;
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refrig_list);

        refrig_lv = findViewById(R.id.RefrigList_listview);
        refrig_add_btn = findViewById(R.id.refrig_add_button);
        goto_statistic_btn = findViewById(R.id.goto_statistic_button);

        refrig_adapter = new RefrigListAdapter();
        // 초기 데이터
        refrig_adapter.addItem(new RefrigListItem("001", "냉장고1", 1, "1111-11-11", true));
        refrig_adapter.addItem(new RefrigListItem("002", "냉장고2", 2, "2222-22-22", false));
        refrig_adapter.addItem(new RefrigListItem("003", "냉장고3", 3, "3333-33-33", false));

        refrig_lv.setAdapter(refrig_adapter);

        // Listview 클릭 이벤트
        refrig_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(RefrigListActivity.this, FoodListActivity.class);
                intent.putExtra("냉장고 id", refrig_adapter.getItem(i).getId()); // 수정필요: index 활용 말고 별개 냉장고 id 값을 보유, 사용해야함 (RefrigListItem 수정 필요)
                startActivity(intent);
//                Toast.makeText(getApplicationContext(), "RefrigList -> Refrig", Toast.LENGTH_SHORT).show();
            }
        });
        refrig_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RefrigListActivity.this, AddRefrigActivity.class);
                startActivity(intent);
            }
        });

        goto_statistic_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RefrigListActivity.this, ConsumptionLogActivity.class);
                user_id = "1206";
                intent.putExtra("사용자 id", user_id); // intent 테스트용
                startActivity(intent);
//                finish();
            }
        });
    }
}