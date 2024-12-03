package com.example.foodfresh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText id_edtv;
    private EditText password_edtv;
    private EditText nickname_edtv;
    private Button signin_post_btn;
    private TextView message_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        id_edtv = findViewById(R.id.siginin_id_edittext);
        password_edtv = findViewById(R.id.siginin_password_edittext);
        nickname_edtv = findViewById(R.id.siginin_nickname_edittext);
        signin_post_btn = findViewById(R.id.siginin_post_button);
        message_tv = findViewById(R.id.signin_message_textview);


        signin_post_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = id_edtv.getText().toString();
                String nickname = nickname_edtv.getText().toString();
                String password = password_edtv.getText().toString();
                RegisterDM registerDM = new RegisterDM(id, nickname, password);

                Call<String> call;
                call = RetrofitClient.getApiService().register_api_post(registerDM);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(!response.isSuccessful()) {
                            Log.e("연결 비정상", Integer.toString(response.code()));
                            Log.e("연결 비정상", response.toString());
                            message_tv.setText("회원가입에 실패했습니다.");
                            return;
                        }
                        String registerResponse = response.body();
                        Log.d("연결 성공", response.body().toString());
                        Intent intent = getParentActivityIntent();
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.v("연결 실패", t.getMessage());
                        message_tv.setText("회원가입에 실패했습니다.");
                        return;
                    }
                });
            }
        });
    }
}