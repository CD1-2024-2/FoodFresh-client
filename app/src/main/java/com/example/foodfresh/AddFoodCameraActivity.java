package com.example.foodfresh;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSource;

class FoodData {
    String name, exdate, barcode;
    Bitmap image;
    public FoodData(String name, String exdate, String barcode, Bitmap image) {
        this.name = name;
        this.exdate = exdate;
        this.barcode = barcode;
        this.image = image;
    }
}

public class AddFoodCameraActivity extends AppCompatActivity {
    private int foodCnt;
    // 카메라
    private static final int REQUEST_IMAGE_CAPTURE = 672;
    private String imageFilePath;
    private String userId, received_data;
    private File photoFile; // 촬영 or 선택한 이미지 파일
    private TextView test_tv, page_tv;
    private ImageView item_iv;
    private EditText barcode_edtv, name_edtv, mfd_edtv, efd_edtv, category_edtv, num_edtv, note_edtv;
    ArrayList<FoodData> foodDataList = new ArrayList<FoodData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_camera);

        item_iv = findViewById(R.id.addFoodCam_item_imageview);
        barcode_edtv = findViewById(R.id.addFoodCam_barcode_edittext);
        name_edtv = findViewById(R.id.addFoodCam_name_edittext);
        mfd_edtv = findViewById(R.id.addFoodCam_mfd_edittext);
        efd_edtv = findViewById(R.id.addFoodCam_efd_edittext);
        category_edtv = findViewById(R.id.addFoodCam_category_edittext);
        num_edtv = findViewById(R.id.addFoodCam_num_edittext);
        note_edtv = findViewById(R.id.addFoodCam_note_edittext);
        Button post_btn = findViewById(R.id.addFoodCam_post_btn);
        Button cancel_btn = findViewById(R.id.addFoodCam_cancel_btn);
        test_tv = findViewById(R.id.addFoodCam_message_textview);
        page_tv = findViewById(R.id.addFoodCam_page_textview);

        // 냉장고 id 등 받아오기
        Intent received_intent = getIntent();
        userId = received_intent.getStringExtra("유저 id");
        received_data = received_intent.getStringExtra("냉장고 id");

        // 카메라 권한 체크
        TedPermission.create()
                .setPermissionListener(permissionListener)
                .setDeniedMessage("Permission denied.\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(android.Manifest.permission.CAMERA, Manifest.permission.READ_MEDIA_IMAGES)
//                Android 11 (sdk33) 이후부터 READ_EXTERNAL_STORAGE 가 3가지로 세분화 되었음. READ 권한 받았으면 WRITE_EXTERNAL_STORAGE 권한 따로 받지 않아도 됨
                .check();

        Intent popup_intent = new Intent(AddFoodCameraActivity.this, PopupCameraActivity.class);
        startPopupActivityResult.launch(popup_intent); // Popup 담당 ActivityResultLauncher 띄우기

        post_btn.setOnClickListener(new View.OnClickListener() { // 식품 등록 POST 버튼
            @Override
            public void onClick(View view) {
                if (!foodDataList.isEmpty()) {
                    //TODO: 현재 UI에 있는 정보로 backend에 POST 요청
                    setData(foodDataList.get(0));
                    foodDataList.remove(0);
                }
                else {
                    Intent intent = new Intent(AddFoodCameraActivity.this, FoodListActivity.class);
                    intent.putExtra("유저 id", userId);
                    intent.putExtra("냉장고 id", received_data);
                    startActivity(intent);
                }
            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() { // 넘어가기 버튼
            @Override
            public void onClick(View view) {
                if (!foodDataList.isEmpty()) {
                    setData(foodDataList.get(0));
                    foodDataList.remove(0);
                }
                else {
                    Intent intent = new Intent(AddFoodCameraActivity.this, FoodListActivity.class);
                    intent.putExtra("유저 id", userId);
                    intent.putExtra("냉장고 id", received_data);
                    startActivity(intent);
                }
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
                                String select = result.getData().getStringExtra("select");
                                if (select.equals("camera")) { // 카메라 열기
                                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    if( intent.resolveActivity(getPackageManager()) != null) {
                                        photoFile = null;
                                        try { // 파일 쓰기를 할때는 항상 try catch 문을 적어야함 !
                                            photoFile = createImageFile(); // File 얻기
                                        } catch (IOException e) {
                                        }
                                        if(photoFile != null) {
                                            Uri photoUri = FileProvider.getUriForFile(getApplicationContext(), getPackageName(),photoFile);
                                            intent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                                            startCameraActivityResult.launch(intent);
                                        }
                                    }
                                }
                                else if (select.equals("gallery")) { //갤러리 열기
                                    Intent intent = new Intent(Intent.ACTION_PICK);
                                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                                    intent.setAction(Intent.ACTION_PICK);
                                    startGalleryActivityResult.launch(intent);
                                }
                            }
                        }
                    });


    ActivityResultLauncher<Intent> startGalleryActivityResult = registerForActivityResult( // 갤러리에서 사진 선택
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        analyseImage(intent.getData());
                    }
                }
            }
    );

    ActivityResultLauncher<Intent> startCameraActivityResult = registerForActivityResult( // 카메라 촬영
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() { // callBack 등록
                @Override
                public void onActivityResult(ActivityResult result) {
                    //원하는 기능 작성
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath);
                        ExifInterface exif = null;

                        // 저장위치: /storage/emulated/0/Android/data/com.example.foodfresh/files/Pictures/TEST_20241126_024628_8188262984632492968.jpg
//                        System.out.println(imageFilePath);
//                        Toast.makeText(getApplicationContext(), "저장위치: "+imageFilePath, Toast.LENGTH_SHORT).show();

                        try {
                            exif = new ExifInterface(imageFilePath);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        int exifOrientation;
                        int exifDegree;

                        if (exif != null) {
                            exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                            exifDegree = exifOrientationToDegress(exifOrientation);
                        } else {
                            exifDegree = 0;
                        }
//                        Log.d("확인 카메라 uri: ", imageFilePath);
//                        Log.d("확인 카메라 path: ", photoFile.getPath());
                        item_iv.setImageBitmap(rotate(bitmap, exifDegree));
                    }
                }
            });

    // timeStampsms 이미지의 이름을 시간단위로 파일이름을 생성하여 중복되지 않게 파일 저장
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "TEST_" + timeStamp + "_";
        // 공용 Pictures 디렉토리의 AppFolder 경로 가져오기
        File picturesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File appFolder = new File(picturesDir, "FoodFresh");

        if (!appFolder.exists()) { // FoodFresh 폴더가 없으면 생성
            appFolder.mkdirs();
        }
        File image = File.createTempFile(imageFileName, ".jpg", appFolder);
        MediaScannerConnection.scanFile(this, new String[] { image.getPath() }, new String[] { "image/jpeg" }, null); // 파일 저장후 갤러리에서 확인 가능하도록 미디어스캐닝
        imageFilePath = image.getAbsolutePath();
        return image;
    }

    // 카메라에서 사진을 찍을 때 화면 회전에 따라 이미지 회전
    private int exifOrientationToDegress(int exifOrientation) {
        if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_90){
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180){
            return 180;
        } else if ((exifOrientation == ExifInterface.ORIENTATION_ROTATE_270)) {
            return 270;
        }
        return 0;
    }

    // 카메라에서 사진을 찍을 때 화면을 돌리는 것에 대한 이미지 회전
    private Bitmap rotate(Bitmap bitmap, int exifDegree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(exifDegree);
        return Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
    }

    // 권한 허용 체크창
    PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onPermissionGranted() { // 권한 허용되면 발생
            Toast.makeText(getApplicationContext(), "권한 허용됨", Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onPermissionDenied(List<String> deniedPermissions) {
            Toast.makeText(getApplicationContext(), "권한 거부됨", Toast.LENGTH_SHORT).show();
        }
    };

    private void analyseImage(Uri uri) {
        try {
            InputStream is = getContentResolver().openInputStream(uri);
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

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                OkHttpClient client = new OkHttpClient.Builder()
                        .addNetworkInterceptor(new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request request = chain.request().newBuilder().addHeader("Connection", "close").build();
                                return chain.proceed(request);
                            }
                        })
                        .connectTimeout(0, TimeUnit.MILLISECONDS)
                        .readTimeout(0, TimeUnit.MILLISECONDS)
                        .writeTimeout(0, TimeUnit.MILLISECONDS).build();
                JSONObject json = new JSONObject();
                json.put("image", Base64.getEncoder().encodeToString(fileBytes));

                Dialog dialog = new LoadingDialog(this);
                dialog.setCancelable(false);
                dialog.show();

                RequestBody body = RequestBody.create(
                        MediaType.get("application/json; charset=utf-8"),
                        json.toString()
                );
                Request request = new Request.Builder()
                        .url("http://10.0.2.2:5000/analyse")
                        .post(body)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        dialog.dismiss();
                        if (response.isSuccessful()) {
                            BufferedSource source = response.body().source();
                            String json = source.readString(Charset.forName("UTF-8"));
                            try {
                                JSONArray jsonArray = new JSONArray(json);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    JSONArray rect = jsonObject.getJSONArray("rect");
                                    String date = jsonObject.getString("date");
                                    String barcode = jsonObject.getString("barcode");
                                    String tag = jsonObject.getString("tag");
                                    Log.d("rect", json);
                                    int width = bitmap.getWidth();
                                    int height = bitmap.getHeight();
                                    Bitmap image = Bitmap.createBitmap(
                                            bitmap,
                                            (int)(rect.getDouble(0)*width),
                                            (int)(rect.getDouble(1)*height),
                                            (int)(rect.getDouble(2)*width),
                                            (int)(rect.getDouble(3)*height)
                                    );
                                    foodDataList.add(new FoodData(tag, date, barcode, image));
                                }
                            } catch (Exception e) {
                                Log.e("Error: ", e.toString());
                            }
                            foodCnt = 0;
                        }
                        if (!foodDataList.isEmpty()) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    setData(foodDataList.get(0));
                                    foodDataList.remove(0);
                                }
                            });
                        }
                        if (foodDataList.isEmpty()) {
                            Intent intent = new Intent(AddFoodCameraActivity.this, FoodListActivity.class);
                            intent.putExtra("유저 id", userId);
                            intent.putExtra("냉장고 id", received_data);
                            startActivity(intent);
                        }
                    }
                });
            }
        } catch (Exception e) {
            Log.e("Error: ", e.toString());
        }
    }
    
    private void setData(FoodData data) {
        foodCnt++;
        barcode_edtv.setText(data.barcode);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mfd_edtv.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        }
        efd_edtv.setText(data.exdate);
        name_edtv.setText(data.name);
        item_iv.setImageBitmap(data.image);
        num_edtv.setText("1");
        page_tv.setText(String.format("(%d/%d)", foodCnt, foodCnt+foodDataList.size()-1));
    }
}