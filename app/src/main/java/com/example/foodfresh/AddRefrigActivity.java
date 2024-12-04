package com.example.foodfresh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddRefrigActivity extends AppCompatActivity {
    private String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_refrig);

        EditText name_edtv = findViewById(R.id.refrigAdd_name_edittext);
        EditText descript_edtv = findViewById(R.id.refrigAdd_description_edittext);
        CheckBox isShared_chkbox = findViewById(R.id.refrigAdd_isShared_checkbox);
        TextView message_tv = findViewById(R.id.refrigAdd_message_textview);
        Button post_btn = findViewById(R.id.refrigAdd_post_button);

        message_tv.setText(null);
        Intent intent = getIntent();
        user_id = intent.getStringExtra("사용자 id");
        post_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                RefrigDM refrig = new RefrigDM(user_id, name_edtv.getText().toString(), user_id, null, isShared_chkbox.isChecked());
                RefrigAddDM refrig2 = new RefrigAddDM(name_edtv.getText().toString(), descript_edtv.getText().toString());

                Call call;
                call = RetrofitClient.getApiService().addRefrig_api_post(user_id, refrig2);
                call.enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        if(!response.isSuccessful()) {
                            Log.e("연결 비정상", response.toString());
                            return;
                        }
                        Log.d("연결 성공", response.body().toString());
//                        MessageDM messageDM = response.body();
//                        message_tv.setText(messageDM.getMessage());
//                        Toast.makeText(getApplicationContext(), messageDM.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Log.v("연결 실패", t.getMessage());
                    }
                });

            }
        });
    }
}