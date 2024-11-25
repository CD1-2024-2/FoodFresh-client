package com.example.foodfresh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AddMemberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        TextView mem_test_tv = findViewById(R.id.addMember_test_textview);
        Button mem_test_btn = findViewById(R.id.addMember_test_button);
        // intent 데이터 받아오기 test
        Intent intent = getIntent();
        mem_test_tv.setText("냉장고 ID: " + intent.getStringExtra("냉장고 id"));

        mem_test_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}