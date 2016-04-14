package com.tdr.citycontrolpolice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.entity.ChuZuWuInfo;
import com.tdr.citycontrolpolice.entity.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/21.
 */
public class ManagerAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater mInflater = null;
    private List<Room> list;

    public ManagerAdapter(List<Room> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ManagerHoler holer = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.lv_czf_manager_item, null);
            holer = new ManagerHoler(convertView);
            convertView.setTag(holer);
        } else {
            holer = (ManagerHoler) convertView.getTag();
        }

        holer.tv_room.setText(list.get(position).getROOMNO());

//        int count =czf.getCount();
//        if(list.size()>10){
//            holer.tv_count.setText("10+");
//        }else{
//            holer.tv_count.setText(count+"");
//        }

        return convertView;
    }
}


class ManagerHoler {

    TextView tv_room, tv_count;

    public ManagerHoler(View view) {
        tv_room = (TextView) view.findViewById(R.id.tv_room);
        tv_count = (TextView) view.findViewById(R.id.tv_count);
    }
}
