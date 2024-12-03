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
//        date_tv.setText(refriglistItem.getDate());

        // delete 이미지 버튼 클릭 이벤트
        delete_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getContext(), (position+1)+"번째 아이템이 삭제됩니다.", Toast.LENGTH_SHORT).show();
                String id_db = (refrigers.get(position)).getId();
                Log.d("삭제: 냉장고", "UI id : " + position + "\nDB id : " + id_db);
                // TODO: 냉장고 삭제 요청
                refrigers.remove(position);
                notifyDataSetChanged();
            }
        });

        // add_member 화면 전환
        add_mem_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id_db = (refrigers.get(position)).getId();
                Intent intent = new Intent(context, AddMemberActivity.class);
                intent.putExtra("사용자 id", id_db);
                intent.putExtra("냉장고 id", id_db);
                ((Activity)context).startActivity(intent);
            }
        });

        curr_mem_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String manger = (refrigers.get(position)).getManager();
                String[] sharedUsers = (refrigers.get(position)).getSharedUsers();
                Intent intent = new Intent(context, PopupMemberActivity.class);
                intent.putExtra("소유자 이름", manger);
                intent.putExtra("공유자 목록", sharedUsers);
                ((Activity)context).startActivity(intent);
            }
        });

        return convertView; // 만든 convertView 라는 View 객체 리턴
    }
}
