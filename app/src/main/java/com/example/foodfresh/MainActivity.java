package com.example.foodfresh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button start_btn = findViewById(R.id.start_button);
        TextView signin_tv = findViewById(R.id.signin_textview);

        Spannable span = (Spannable) signin_tv.getText();
        signin_tv.setMovementMethod(LinkMovementMethod.getInstance());
        span.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                // click
                Intent myIntent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(myIntent);
//                finish();
            }
        }, 12, span.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        signin_tv.setMovementMethod(LinkMovementMethod.getInstance());

        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 첫 param 은 현재 context (this), 두번째 param 은 전환 대상 Activity.class
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
//                finish();
//                Toast.makeText(getApplicationContext(), "Main -> Login", Toast.LENGTH_SHORT).show();
            }
        });
    }
}