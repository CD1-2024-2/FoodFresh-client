package com.example.foodfresh;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RefrigListAdapter extends BaseAdapter {
//    ArrayList<RefrigListItem> refrigers = new ArrayList<RefrigListItem>();
    ArrayList<RefrigDM> refrigers = new ArrayList<RefrigDM>();
    Context context;
    private String user_id;

    public RefrigListAdapter() {}
    public RefrigListAdapter(String user_id){
        this.user_id = user_id;
    }

    @Override
    public int getCount() {
        return refrigers.size();
    }

    @Override
    public RefrigDM getItem(int i) {
        return refrigers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void addItem(RefrigDM refriger) {
        refrigers.add(refriger);
    }

    // position 에 위치한 데이터를 화면에 출력
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context = parent.getContext();
        RefrigDM refriglistItem = refrigers.get(position);

        // 기존 View 가 없다면 convertView 에 listview_refriglist 를 inflate
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_refriglist, parent, false);
        }

        TextView name_tv = convertView.findViewById(R.id.refrig_name_textview);
        TextView description_tv  = convertView.findViewById((R.id.description_textview));
        TextView num_tv = convertView.findViewById(R.id.num_members_textview);
        TextView date_tv = convertView.findViewById((R.id.createdDate_textview));
        ImageView add_mem_iv = convertView.findViewById(R.id.member_add_imageview);
        ImageView curr_mem_iv = convertView.findViewById(R.id.member_current_imageview);
        ImageView delete_iv = convertView.findViewById(R.id.refrig_delete_imageview);

        // 데이터 set
        name_tv.setText(refriglistItem.getName());
        description_tv.setText("공유 냉장고");
        if (!refriglistItem.isShared()) description_tv.setText("개인 냉장고");
        num_tv.setText(Integer.toString(refriglistItem.getSharedUsers().length+1));
        date_tv.setText(refriglistItem.getCreatedDate());

        // delete 이미지 버튼 클릭 이벤트
        delete_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getContext(), (position+1)+"번째 아이템이 삭제됩니다.", Toast.LENGTH_SHORT).show();
                String refrig_id = (refrigers.get(position)).getId();
                Log.d("삭제: 냉장고", "UI id : " + position + "\nDB id : " + refrig_id);
                // TODO: 냉장고 삭제 요청

                Call<MessageDM> call;
                call = RetrofitClient.getApiService().delRefrig_api_delete(refrig_id, user_id);
                call.enqueue(new Callback<MessageDM>() {
                    @Override
                    public void onResponse(Call<MessageDM> call, Response<MessageDM> response) {
                        if(!response.isSuccessful()) {
                            Log.e("연결 비정상", response.toString());
                            return;
                        }
                        Log.d("연결 성공", "냉장고 삭제 완료");
                        ((RefrigListActivity)context).refreshActivtiy();
                    }
                    @Override
                    public void onFailure(Call<MessageDM> call, Throwable t) {
                        Log.v("연결 실패", t.getMessage());
                    }
                });
            }
        });

        // add_member 화면 전환
        add_mem_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String refrig_id = (refrigers.get(position)).getId();
                Intent intent = new Intent(context, AddMemberActivity.class);
                intent.putExtra("사용자 id", user_id);
                intent.putExtra("냉장고 id", refrig_id);
                ((Activity)context).startActivity(intent);
            }
        });

        curr_mem_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PopupMemberActivity.class);
                intent.putExtra("사용자 id", user_id);
                System.out.println("아아"+(refrigers.get(position)).getId());
                intent.putExtra("냉장고 id",(refrigers.get(position)).getId());
                intent.putExtra("소유자 id", (refrigers.get(position)).getManager());
                intent.putExtra("공유자 목록", (refrigers.get(position)).getSharedUsers());
                ((Activity)context).startActivity(intent);
            }
        });

        return convertView; // 만든 convertView 라는 View 객체 리턴
    }
}
