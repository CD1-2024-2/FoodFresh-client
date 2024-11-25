package com.example.foodfresh;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.TextView;

public class SigninActivity extends AppCompatActivity {

    private EditText id_edtv;
    private EditText password_edtv;
    private EditText nickname_edtv;
    private Button signin_post_btn;
    private TextView message_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        id_edtv = findViewById(R.id.siginin_id_edittext);
        password_edtv = findViewById(R.id.siginin_password_edittext);
        nickname_edtv = findViewById(R.id.siginin_nickname_edittext);
        signin_post_btn = findViewById(R.id.siginin_post_button);
        message_tv = findViewById(R.id.signin_message_textview);
    }
}