package com.example.foodfresh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodListActivity extends AppCompatActivity {
    private String user_id;
    private String refrig_id;
    private ListView food_lv;
    private FoodListAdapter food_adapter;
    private Button food_manual_btn;
    private Button food_camera_btn;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        Intent intent = getIntent();
        this.user_id = intent.getStringExtra("사용자 id");
        this.refrig_id = intent.getStringExtra("냉장고 id");

        food_lv = findViewById(R.id.foodList_listview);
        food_manual_btn = findViewById(R.id.food_manual_button);
        food_camera_btn = findViewById(R.id.food_camera_button);

        Call<List<FoodDM>> call;
        call = RetrofitClient.getApiService().food_api_get(refrig_id);
        call.enqueue(new Callback<List<FoodDM>>() {
            @Override
            public void onResponse(Call<List<FoodDM>> call, Response<List<FoodDM>> response) {
                if(!response.isSuccessful()) {
                    Log.e("연결 비정상", Integer.toString(response.code()));
                    Log.e("연결 비정상", (response.toString()));
                    return;
                }
                Log.d("연결 성공", "식품 목록 불러오기 성공");
                List<FoodDM> foodResponse = response.body();

                food_adapter = new FoodListAdapter(user_id, refrig_id);
                for (int i=0; i<foodResponse.size(); i++) {
                    food_adapter.addItem(foodResponse.get(i));
                }
                food_lv.setAdapter(food_adapter);
            }

            @Override
            public void onFailure(Call<List<FoodDM>> call, Throwable t) {
                Log.v("연결 실패", t.getMessage());
                return;
            }
        });

        // Listview 클릭 이벤트
        food_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(FoodListActivity.this, FoodDataActivity.class);
//                FoodItem food = new FoodItem(food_adapter.getItem(position));
                intent.putExtra("사용자 id", user_id);
                intent.putExtra("냉장고 id", refrig_id);
                intent.putExtra("식품 정보", food_adapter.getItem(position));
                startActivity(intent);
//                Toast.makeText(getApplicationContext(), "RefrigList -> Refrig", Toast.LENGTH_SHORT).show();
            }
        });

        food_manual_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodListActivity.this, AddFoodActivity.class);
                intent.putExtra("냉장고 id", refrig_id);
                startActivity(intent);
            }
        });
        food_camera_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodListActivity.this, AddFoodCameraActivity.class);
                intent.putExtra("냉장고 id", refrig_id);
                startActivity(intent);
            }
        });
    }
    class FoodItem implements Serializable {
        private String fid;
        private String imageURL;
        private String name;
        private Object expirationDate;
        private Object registeredDate;
        private int quantity;
        private String category;
        private String barcode;
        private String description;

        public FoodItem(FoodDM foodDM){
            this.fid = foodDM.getId();
            this.imageURL = foodDM.getImageURL();
            this.name = foodDM.getName();
            this.expirationDate = foodDM.getExpirationDate();
            this.registeredDate = foodDM.getRegisteredDate();
            this.quantity = foodDM.getQuantity();
            this.category = foodDM.getCategory();
            this.barcode = foodDM.getBarcode();
            this.description = foodDM.getDescription();
        }
        public FoodItem(String fid, String imageURL, String name, Object expirationDate, Object registeredDate, int quantity, String category, String barcode, String description) {
            this.fid = fid;
            this.imageURL = imageURL;
            this.name = name;
            this.expirationDate = expirationDate;
            this.registeredDate = registeredDate;
            this.quantity = quantity;
            this.category = category;
            this.barcode = barcode;
            this.description = description;
        }

        public String getFid() {
            return fid;
        }

        public String getImageURL() {
            return imageURL;
        }

        public String getName() {
            return name;
        }

        public Object getExpirationDate() {
            return expirationDate;
        }

        public Object getRegisteredDate() {
            return registeredDate;
        }

        public int getQuantity() {
            return quantity;
        }

        public String getCategory() {
            return category;
        }

        public String getBarcode() {
            return barcode;
        }

        public String getDescription() {
            return description;
        }
    }
}

