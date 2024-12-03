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
        String manger = intent.getStringExtra("소유자 이름");
        String[] sharedUsers = (String[])intent.getSerializableExtra("공유자 목록");

        String str = "소유자: " + manger;
        for (int i=0; i<sharedUsers.length; i++) {
            str += "\n공유자" + (i+1) + ": "+ sharedUsers[i];
        }
        test_tv.setText(str);

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}