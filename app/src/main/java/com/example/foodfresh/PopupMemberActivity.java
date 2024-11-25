package com.example.foodfresh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class PopupMemberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE); //타이틀바 없애기
        setContentView(R.layout.activity_popup_member);

        TextView test_tv = findViewById(R.id.popUp_test_textview);
        Button close_btn = findViewById(R.id.popUp_close_btn);

        Intent intent = getIntent();
//        List memberList = (List)intent.getSerializableExtra("memberList");
        test_tv.setText("냉장고 id: " + intent.getStringExtra("냉장고 id"));

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}