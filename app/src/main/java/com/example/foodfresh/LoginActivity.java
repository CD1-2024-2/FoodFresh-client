package com.example.foodfresh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button login_btn = findViewById(R.id.login_post_button);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RefrigListActivity.class);
                startActivity(intent);
                finish();
//                Toast.makeText(getApplicationContext(), "Login -> RefrigList", Toast.LENGTH_SHORT).show();
            }
        });
    }
}