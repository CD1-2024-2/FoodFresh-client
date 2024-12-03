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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMemberActivity extends AppCompatActivity {
    private String user_id;
    private String refrig_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        TextView message_tv = findViewById(R.id.addMember_message_textview);
        EditText mem_id_edtv = findViewById(R.id.addMember_id_edittext);
        Button mem_post_btn = findViewById(R.id.addMember_post_button);

        Intent intent = getIntent();
        user_id = intent.getStringExtra("사용자 id");
        refrig_id = intent.getStringExtra("냉장고 id");

        mem_post_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> usersArr = new ArrayList<String>();
                usersArr.add(mem_id_edtv.getText().toString());
                MemberDM memberDM = new MemberDM(usersArr.toArray(new String[0]));

                Call<MessageDM> call;
                call = RetrofitClient.getApiService().addMember_api_post(refrig_id, user_id, memberDM);
                call.enqueue(new Callback<MessageDM>() {
                    @Override
                    public void onResponse(Call<MessageDM> call, Response<MessageDM> response) {
                        if(!response.isSuccessful()) {
                            Log.e("연결 비정상", response.toString());
                            return;
                        }
                        Log.d("연결 성공", response.body().toString());
                        MessageDM messageDM = response.body();
                        message_tv.setText(messageDM.getMessage());
                        Toast.makeText(getApplicationContext(), messageDM.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<MessageDM> call, Throwable t) {
                        Log.v("연결 실패", t.getMessage());
                    }
                });
            }
        });
    }
}