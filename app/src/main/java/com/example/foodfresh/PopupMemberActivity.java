package com.example.foodfresh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopupMemberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE); //타이틀바 없애기
        setContentView(R.layout.activity_popup_member);

        Button close_btn = findViewById(R.id.popUp_close_btn);
        ListView member_lv = findViewById(R.id.memberList_listview);

        Intent intent = getIntent();
        String user_id = intent.getStringExtra("사용자 id");
        String refrig_id = intent.getStringExtra("냉장고 id");
        String manager = intent.getStringExtra("소유자 id");
        String[] sharedUsers = (String[])intent.getSerializableExtra("공유자 목록");

        MemberListAdapter adapter = new MemberListAdapter(user_id, refrig_id, manager, sharedUsers);
        for (int i=0; i<sharedUsers.length; i++) {
            MemberListItem temp = new MemberListItem(sharedUsers[i]);
            adapter.addItem(temp);
        }
        member_lv.setAdapter(adapter);


        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void refreshActivtiy(){
        recreate();
    }
}

class MemberListAdapter extends BaseAdapter {
    ArrayList<MemberListItem> members = new ArrayList<MemberListItem>();
    Context context;
    String user_id;
    String refrig_id;
    String manager;
    String[] sharedUsers;

    public MemberListAdapter(String user_id, String refrig_id, String manager, String[] sharedUsers) {
        this.user_id = user_id;
        this.refrig_id = refrig_id;
        this.manager = manager;
        this.sharedUsers = sharedUsers;
    }

    @Override
    public int getCount() {
        return members.size();
    }

    @Override
    public Object getItem(int i) {
        return members.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void addItem(MemberListItem item) {
        members.add(item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context = parent.getContext();
        MemberListItem memberListItem = members.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_memberlist, parent, false);
        }

        ImageView del_iv = convertView.findViewById(R.id.delMember_imageview);
        TextView name_tv = convertView.findViewById(R.id.delMember_textview);

        del_iv.setImageResource(R.drawable.trash_bin);
        name_tv.setText(memberListItem.getId());

        del_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> usersArr = new ArrayList<String>();
                usersArr.add(members.get(position).getId());
                MemberDM memberDM = new MemberDM(usersArr.toArray(new String[0]));

                Call<MessageDM> call;
                call = RetrofitClient.getApiService().delMember_api_post(refrig_id, user_id, memberDM);
                call.enqueue(new Callback<MessageDM>() {
                    @Override
                    public void onResponse(Call<MessageDM> call, Response<MessageDM> response) {
                        if(!response.isSuccessful()) {
                            Log.e("연결 비정상", response.toString());
                            return;
                        }
                        Log.d("연결 성공", "멤버 삭제");
//                        ((PopupMemberActivity)context).refreshActivtiy();
                        Toast.makeText(context.getApplicationContext(), "멤버가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(Call<MessageDM> call, Throwable t) {
                        Log.v("연결 실패", t.getMessage());
                    }
                });
            }
        });

        return convertView;
    }
}

class MemberListItem {
    private String id;

    public MemberListItem(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
