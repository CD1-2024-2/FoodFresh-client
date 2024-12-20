package com.example.foodfresh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RefrigListActivity extends AppCompatActivity {
    private ListView refrig_lv;
    private RefrigListAdapter refrig_adapter;
    private Button refrig_add_btn;
    private Button goto_statistic_btn;
    private String user_id;
    private LoginActivity.UserInfoTest userinfo;

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_refrig_list);

        refrig_lv = findViewById(R.id.refrigList_listview);
        refrig_add_btn = findViewById(R.id.refrig_add_button);
        goto_statistic_btn = findViewById(R.id.goto_statistic_button);
        TextView message_tv = findViewById(R.id.refrigList_message_textview);

        Intent intent = getIntent();
        userinfo = (LoginActivity.UserInfoTest)intent.getSerializableExtra("userInfo");
        user_id = userinfo.getId();
//        msg_tv.setText(userinfo.getAll());
        message_tv.setText(null);


        Call<List<RefrigDM>> call;
        call = RetrofitClient.getApiService().refrig_api_get(userinfo.getId());

        call.enqueue(new Callback<List<RefrigDM>>() {
            @Override
            public void onResponse(Call<List<RefrigDM>> call, Response<List<RefrigDM>> response) {
                if(!response.isSuccessful()) {
                    Log.e("연결 비정상", (response.toString()));
                    return;
                }
                List<RefrigDM> refirgResponse = response.body();
                Log.d("연결 성공", "냉장고 목록 불러오기 성공");

                refrig_adapter = new RefrigListAdapter(user_id);
                for (int i=0; i<refirgResponse.size(); i++) {
                    refrig_adapter.addItem(refirgResponse.get(i));
                }
                refrig_lv.setAdapter(refrig_adapter);
            }

            @Override
            public void onFailure(Call<List<RefrigDM>> call, Throwable t) {
                Log.v("연결 실패", t.getMessage());
                return;
            }
        });


        // Listview 클릭 이벤트
        refrig_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(RefrigListActivity.this, FoodListActivity.class);
                intent.putExtra("사용자 id", user_id);
                intent.putExtra("냉장고 id", refrig_adapter.getItem(position).getId());
                startActivity(intent);
            }
        });
        refrig_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RefrigListActivity.this, AddRefrigActivity.class);
                System.out.println(user_id);
                intent.putExtra("사용자 id", user_id);
                startActivity(intent);
            }
        });

        goto_statistic_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RefrigListActivity.this, ConsumptionLogActivity.class);
                intent.putExtra("사용자 id", user_id);
                startActivity(intent);
//                finish();
            }
        });
    }
    public void refreshActivtiy(){
        recreate();
    }
}