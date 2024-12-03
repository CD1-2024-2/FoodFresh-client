package com.example.foodfresh;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;

public class AddRefrigActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_refrig);

        EditText name_edtv = findViewById(R.id.refrigAdd_name_edittext);
        EditText descript_edtv = findViewById(R.id.refrigAdd_description_edittext);
        CheckBox isShared_chkbox = findViewById(R.id.refrigAdd_isShared_checkbox);
        Button post_btn = findViewById(R.id.refrigAdd_post_button);

        post_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}