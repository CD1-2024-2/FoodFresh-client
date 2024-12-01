package com.example.foodfresh;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AddFoodCameraActivity extends AppCompatActivity {
    // 카메라
    private static final int REQUEST_IMAGE_CAPTURE = 672;
    private String imageFilePath;
    private File photoFile; // 촬영 or 선택한 이미지 파일
    private TextView test_tv;
    private ImageView item_iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_camera);

        item_iv = findViewById(R.id.addFoodCam_item_imageview);
        EditText barcode_edtv = findViewById(R.id.addFoodCam_barcode_edittext);
        EditText name_edtv = findViewById(R.id.addFoodCam_name_edittext);
        EditText mfd_edtv = findViewById(R.id.addFoodCam_mfd_edittext);
        EditText efd_edtv = findViewById(R.id.addFoodCam_efd_edittext);
        EditText category_edtv = findViewById(R.id.addFoodCam_category_edittext);
        EditText num_edtv = findViewById(R.id.addFoodCam_num_edittext);
        EditText note_edtv = findViewById(R.id.addFoodCam_note_edittext);
        Button post_btn = findViewById(R.id.addFoodCam_post_btn);
        test_tv = findViewById(R.id.addFoodCam_message_textview);

        // 냉장고 id 등 받아오기
        Intent received_intent = getIntent();
        String received_data = received_intent.getStringExtra("냉장고 id");

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
                        Uri uri = intent.getData();
                        item_iv.setImageURI(uri);
                        photoFile = new File(uri.getPath()); // File 얻기
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
}