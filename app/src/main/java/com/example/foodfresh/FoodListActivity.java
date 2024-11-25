package com.example.foodfresh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class FoodListActivity extends AppCompatActivity {

    String refrig_id;
    private ListView food_lv;
    private FoodListAdapter food_adapter;
    private Button food_add_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        Intent intent = getIntent();
        this.refrig_id = intent.getStringExtra("냉장고 id");

        food_lv = findViewById(R.id.foodList_listview);
        food_add_btn = findViewById(R.id.food_add_button);

        food_adapter = new FoodListAdapter();
        // 초기 데이터
        food_adapter.addItem(new FoodListItem("001"));
        food_adapter.addItem(new FoodListItem("002"));
        food_adapter.addItem(new FoodListItem("003"));

        food_lv.setAdapter(food_adapter);

        // Listview 클릭 이벤트
        food_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(FoodListActivity.this, FoodDataActivity.class);
                intent.putExtra("냉장고 id", "test value");
                startActivity(intent);
//                Toast.makeText(getApplicationContext(), "RefrigList -> Refrig", Toast.LENGTH_SHORT).show();
            }
        });
        food_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodListActivity.this, CameraActivity.class);
                intent.putExtra("냉장고 id", refrig_id);
                startActivity(intent);
            }
        });
    }
}