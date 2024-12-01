package com.example.foodfresh;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class AddFoodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        EditText barcode_edtv = findViewById(R.id.addFood_barcode_edittext);
        EditText name_edtv = findViewById(R.id.addFood_name_edittext);
        EditText mfd_edtv = findViewById(R.id.addFood_mfd_edittext);
        EditText efd_edtv = findViewById(R.id.addFood_efd_edittext);
        EditText category_edtv = findViewById(R.id.addFood_category_edittext);
        EditText num_edtv = findViewById(R.id.addFood_num_edittext);
        EditText note_edtv = findViewById(R.id.addFood_note_edittext);
        Button add_post_btn = findViewById(R.id.addFood_post_btn);

    }
}