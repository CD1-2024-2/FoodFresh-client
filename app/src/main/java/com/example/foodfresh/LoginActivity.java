package com.example.foodfresh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText id_edtv;
    private EditText password_edtv;
    private Button login_btn;
    private TextView message_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        id_edtv = findViewById(R.id.login_id_edittext);
        password_edtv = findViewById(R.id.login_password_edittext);
        login_btn = findViewById(R.id.login_post_button);
        message_tv = findViewById(R.id.login_message_textview);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = id_edtv.getText().toString();
                String password = password_edtv.getText().toString();
                LoginDM loginDM = new LoginDM(id, password);

                // 임시 넘어가기 버튼
                Intent intent = new Intent(LoginActivity.this, RefrigListActivity.class);
                startActivity(intent);
                finish();
                
                /*Call<UserInfoTest> call;
                call = RetrofitClient.getApiService().login_api_post(loginDM);
                call.enqueue(new Callback<UserInfoTest>() {
                    @Override
                    public void onResponse(Call<UserInfoTest> call, Response<UserInfoTest> response) {
                        if(!response.isSuccessful()) {
                            Log.e("연결 비정상", Integer.toString(response.code()));
                            message_tv.setText("로그인에 실패했습니다.");
                            return;
                        }
                        UserInfoTest LoginResponse = response.body();
                        Log.d("연결 성공", response.body().toString());
                        Intent intent = new Intent(LoginActivity.this, RefrigListActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<UserInfoTest> call, Throwable t) {
                        Log.v("연결 실패", t.getMessage());
                        message_tv.setText("로그인에 실패했습니다.");
                        return;
                    }
                });*/
            }
        });
    }

    class UserInfoTest implements Serializable {
        private String id; // user 가 login 에 사용한 id

        public UserInfoTest (String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public String getAll() {
            String str = "id: " + id;
            return str;
        }
    }

    // 로그인 user 정보 저장용  intent 로 전달하기 위해서 Serializable implement 함
    class UserInfo implements Serializable {
        private String id; // user 가 login 에 사용한 id
        private String password;
        private String nickname; // 닉네임

        public UserInfo (String id, String password, String nickname) {
            this.id = id;
            this.password = password;
            this.nickname = nickname;
        }

        public String getId() {
            return id;
        }
        public String getPassword() {
            return password;
        }
        public String getNickname() {
            return nickname;
        }

        public String getAll() {
            String str = "id: " + id + "\npassword: " + password + "\nnickname: " + nickname;
            return str;
        }
    }

}