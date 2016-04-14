package com.tdr.citycontrolpolice.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.entity.ChuZuWuInfo;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/21.
 */
public class DeclareAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ChuZuWuInfo> list;

    public DeclareAdapter(ArrayList<ChuZuWuInfo> list, Context context) {
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

        Holer holer = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.lv_czf_declare_item, null);
            holer = new Holer(convertView);
            convertView.setTag(holer);
        } else {
            holer = (Holer) convertView.getTag();
        }
        ChuZuWuInfo czf = (ChuZuWuInfo) getItem(position);
        holer.tv_room.setText(czf.getHOUSENAME());

        return convertView;
    }
}


class Holer {

    TextView tv_room, tv_count;

    public Holer(View view) {
        tv_room = (TextView) view.findViewById(R.id.tv_room);
        tv_count = (TextView) view.findViewById(R.id.tv_count);
    }
}