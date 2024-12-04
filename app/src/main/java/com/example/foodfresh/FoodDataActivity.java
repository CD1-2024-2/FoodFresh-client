package com.example.foodfresh;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FoodDataActivity extends AppCompatActivity {
    private String user_id;
    private String refrig_id;
    private FoodDM foodDM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_data);

        ImageView food_iv = findViewById(R.id.foodData_item_imageview);
        TextView barcode_edtv = findViewById(R.id.foodData_barcode_textview);
        TextView name_edtv = findViewById(R.id.foodData_name_textview);
        TextView mfd_edtv = findViewById(R.id.foodData_mfd_textview);
        TextView efd_edtv = findViewById(R.id.foodData_efd_textview);
        TextView category_edtv = findViewById(R.id.foodData_category_textview);
        TextView num_edtv = findViewById(R.id.foodData_num_textview);
        TextView note_edtv = findViewById(R.id.foodData_note_textview);
        Button del_btn = findViewById(R.id.foodData_del_button);
        Button del_reason_btn = findViewById(R.id.foodData_del_reason_button);


        Intent intent = getIntent();
        user_id = intent.getStringExtra("사용자 id");
        refrig_id = intent.getStringExtra("냉장고 id");
        foodDM = (FoodDM)intent.getSerializableExtra("식품 정보");


        Bitmap image;
        MyRunnable runnable = new MyRunnable(foodDM.getImageURL());
        // 단순히 subthread 에서 전부 작업하면 ui hierarchy 상 subthread 에서 ui 접근을 못하므로 변경 불가능함
        // runonUIthread 는 결국 mainthread 에서 동작함
        // main 에서 uri -> bitmap 변환 network 작업 수행 불가능
        // 따라서 network 작업만 thread 에서 진행후 bitmap 값을 반환 받아 main 에서 setimage 하였음
        Thread thread = new Thread(runnable);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        image = runnable.getImage();
        food_iv.setImageBitmap(image);

        barcode_edtv.setText(foodDM.getBarcode());
        name_edtv.setText(foodDM.getName());
        mfd_edtv.setText("default value");
        if (foodDM.getRegisteredDate() != null) mfd_edtv.setText(foodDM.getRegisteredDate());
        efd_edtv.setText("default value");
        if (foodDM.getExpirationDate() != null) efd_edtv.setText(foodDM.getExpirationDate());
        category_edtv.setText(foodDM.getCategory());
        num_edtv.setText(Integer.toString(foodDM.getQuantity()));
        note_edtv.setText(foodDM.getDescription());

        del_reason_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent popup_intent = new Intent(FoodDataActivity.this, PopupDeleteActivity.class);
                startPopupActivityResult.launch(popup_intent); // Popup 담당 ActivityResultLauncher 띄우기
            }
        });
    }

    public class MyRunnable implements Runnable {
        private Bitmap image;
        private String uri;
        MyRunnable(String uri) {
            this.uri=uri;
        }

        public Bitmap getImage() {
            return image;
        }

        @Override
        public void run() {
            this.image = getBitmapFromURL(foodDM.getImageURL());
        }
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

    public static Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src",src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap","returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
            return null;
        }
    }
}