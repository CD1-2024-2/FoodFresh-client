package com.example.foodfresh;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
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

public class CameraActivity extends AppCompatActivity {
    // 카메라
    private static final int REQUEST_IMAGE_CAPTURE = 672;
    private String imageFilePath;
    private Uri photoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        TextView test_tv = findViewById(R.id.test_textview);
        // intent 데이터 받아오기 test
        Intent intent = getIntent();
        test_tv.setText("냉장고 ID: " + intent.getStringExtra("냉장고 id"));

        // 카메라 권한 체크
        TedPermission.create()
                .setPermissionListener(permissionListener)
                .setDeniedMessage("Permission denied.\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_MEDIA_IMAGES)
/*
                Android 11 (sdk33) 이상에서는 READ_EXTERNAL_STORAGE 가 세분화 되었음
                또한 WRITE_EXTERNAL_STORAGE 요청(외부 저장소에 자체 앱별 디렉토리 생성하는거) 불가능. 직접 파일 경로 사용하여 엑세스 해야함
*/
                .check();

        Button camera_btn = findViewById(R.id.camera_button);
        camera_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if( intent.resolveActivity(getPackageManager()) != null) {
                    File photoFile = null;
                    try { // 파일 쓰기를 할때는 항상 try catch 문을 적어야함 !
                        photoFile = createImageFile();
                    } catch (IOException e) {
                    }
                    if(photoFile != null) {
                        photoUri = FileProvider.getUriForFile(getApplicationContext(), getPackageName(),photoFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                        startActivityResult.launch(intent);
                    }
                }
            }
        });
    }
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "TEST_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        imageFilePath = image.getAbsolutePath();
        return image;
    }
    ActivityResultLauncher<Intent> startActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
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

                        ((ImageView) findViewById(R.id.camera_imageView)).setImageBitmap(rotate(bitmap, exifDegree));
                    }
                }
            });
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
    private Bitmap rotate(Bitmap bitmap, int exifDegree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(exifDegree);
        return Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
    }

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