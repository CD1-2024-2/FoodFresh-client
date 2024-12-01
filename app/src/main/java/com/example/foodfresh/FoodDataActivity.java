package com.example.foodfresh;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

public class FoodDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_data);

        TextView barcode_edtv = findViewById(R.id.foodData_barcode_textview);
        TextView name_edtv = findViewById(R.id.foodData_name_textview);
        TextView mfd_edtv = findViewById(R.id.foodData_mfd_textview);
        TextView efd_edtv = findViewById(R.id.foodData_efd_textview);
        TextView category_edtv = findViewById(R.id.foodData_category_textview);
        TextView num_edtv = findViewById(R.id.foodData_num_textview);
        TextView note_edtv = findViewById(R.id.foodData_note_textview);
        Button del_btn = findViewById(R.id.foodData_del_button);
        Button fix_post_btn = findViewById(R.id.foodData_post_button);



        del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent popup_intent = new Intent(FoodDataActivity.this, PopupDeleteActivity.class);
                startPopupActivityResult.launch(popup_intent); // Popup 담당 ActivityResultLauncher 띄우기
            }
        });
    }
    ActivityResultLauncher<Intent> startPopupActivityResult = registerForActivityResult( // Popup launcher
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    // result 에는 resultCode 가 있다.
                    // resultCode 의 값으로, 여러가지 구분해서 사용이 가능.
                    if (result.getResultCode() == 0){
                        String test_value = result.getData().getStringExtra("key");
                        // 여기서 받아온 소모기록 + 오늘 날짜 해서 소모기록 생성하여 POST 할 것임
                    }
                }
            });
}