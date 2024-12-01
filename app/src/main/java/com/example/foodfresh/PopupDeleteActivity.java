package com.example.foodfresh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class PopupDeleteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup_delete);

        Button close_btn = findViewById(R.id.popUp_close_btn);
        
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // EditText 에서 값들 가져와야함
                Intent resultIntent = new Intent();
                resultIntent.putExtra("key", "아아 마이크 테스트");
                setResult(0, resultIntent);
                finish();
            }
        });
    }
}