package com.example.foodfresh;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class AddFoodActivity extends AppCompatActivity {
    private String imageFilePath;
    private String userId, received_data;
    private File photoFile; // 촬영 or 선택한 이미지 파일
    private TextView test_tv, page_tv;
    private ImageView item_iv;
    private EditText barcode_edtv, name_edtv, mfd_edtv, efd_edtv, category_edtv, num_edtv, note_edtv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        Intent received_intent = getIntent();
        userId = received_intent.getStringExtra("유저 id");
        received_data = received_intent.getStringExtra("냉장고 id");

        barcode_edtv = findViewById(R.id.addFood_barcode_edittext);
        name_edtv = findViewById(R.id.addFood_name_edittext);
        mfd_edtv = findViewById(R.id.addFood_mfd_edittext);
        efd_edtv = findViewById(R.id.addFood_efd_edittext);
        category_edtv = findViewById(R.id.addFood_category_edittext);
        num_edtv = findViewById(R.id.addFood_num_edittext);
        note_edtv = findViewById(R.id.addFood_note_edittext);
        item_iv = findViewById(R.id.addFood_item_imageview);
        Button add_post_btn = findViewById(R.id.addFood_post_btn);

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        intent.setAction(Intent.ACTION_PICK);
        startGalleryActivityResult.launch(intent);
        add_post_btn.setOnClickListener(new View.OnClickListener() { // 식품 등록 POST 버튼
            @Override
            public void onClick(View view) {
                String fileName = UUID.randomUUID().toString() + ".jpg";
                File directory = new File(AddFoodActivity.this.getApplicationContext().getCacheDir(), "images");
                    if (!directory.exists()) directory.mkdirs();

                    File file = new File(directory, fileName);
                    try (FileOutputStream outputStream = new FileOutputStream(file)) {
                        ((BitmapDrawable)item_iv.getDrawable()).getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                        outputStream.flush();
                    } catch (IOException e) {
                        Log.e("Error", e.toString());
                    }
                    Dialog dialog = new LoadingDialog(AddFoodActivity.this);
                    dialog.setCancelable(false);
                    dialog.show();
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                    StorageReference fileReference = storageReference.child(fileName);
                    fileReference.putFile(Uri.fromFile(file))
                            .addOnSuccessListener(taskSnapshot -> {
                                fileReference.getDownloadUrl()
                                        .addOnSuccessListener(uri -> {
                                            dialog.dismiss();
                                            createFood(received_data, getFoodDM(uri.toString()));
                                            Intent intent = new Intent(AddFoodActivity.this, FoodListActivity.class);
                                            intent.putExtra("유저 id", userId);
                                            intent.putExtra("냉장고 id", received_data);
                                            startActivity(intent);
                                        })
                                        .addOnFailureListener(e -> {
                                            dialog.dismiss();
                                            Log.e("Error", e.toString());
                                        });
                            })
                            .addOnFailureListener(e -> {
                                dialog.dismiss();
                                Log.e("Error", e.toString());
                            });
            }
        });
    }
    ActivityResultLauncher<Intent> startGalleryActivityResult = registerForActivityResult( // 갤러리에서 사진 선택
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK) {
                        try {
                            Intent intent = result.getData();
                            InputStream is = getContentResolver().openInputStream(intent.getData());
                            ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
                            int bufferSize = 1024;
                            byte[] buffer = new byte[bufferSize];

                            int len;
                            while ((len = is.read(buffer)) != -1) {
                                byteBuffer.write(buffer, 0, len);
                            }
                            byte[] fileBytes = byteBuffer.toByteArray();
                            Bitmap bitmap = BitmapFactory.decodeByteArray(fileBytes, 0, fileBytes.length);
                            is.close();
                            item_iv.setImageBitmap(bitmap);
                        } catch(Exception e) {
                            Log.e("Error", e.toString());
                        }
                    }
                }
            }
    );
    private AddFoodDM getFoodDM(String imgURL) {
        return new AddFoodDM(
                imgURL,
                name_edtv.getText().toString(),
                efd_edtv.getText().toString(),
                Integer.parseInt(num_edtv.getText().toString()),
                category_edtv.getText().toString(),
                barcode_edtv.getText().toString(),
                note_edtv.getText().toString()
        );
    }
    private void createFood(String refrigeratorId, AddFoodDM food) {
        retrofit2.Call<Void> call;
        call = RetrofitClient.getApiService().create_food_api(refrigeratorId, food);
        call.enqueue(new retrofit2.Callback<Void>() {
            @Override
            public void onResponse(retrofit2.Call<Void> call, retrofit2.Response<Void> response) {
                if(!response.isSuccessful()) {}
            }
            @Override
            public void onFailure(retrofit2.Call<Void> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });
    }
}