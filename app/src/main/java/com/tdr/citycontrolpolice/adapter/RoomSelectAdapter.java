package com.tdr.citycontrolpolice.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.entity.Basic_Dictionary_Kj;
import com.tdr.citycontrolpolice.entity.KjChuZuWuInfo;

import java.util.List;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/4/11 17:14
 * 修改备注：
 */
public class RoomSelectAdapter extends BaseAdapter {
    private Context context;
    private List<KjChuZuWuInfo.ContentBean.RoomListBean> list;

    public RoomSelectAdapter(Context context, List<KjChuZuWuInfo.ContentBean.RoomListBean> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size()+1;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position-1);
    }

    @Override
    public long getItemId(int position) {
        return position-1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View
                    .inflate(context, R.layout.item_single_text, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (position == 0) {
            viewHolder.tvsingle.setText("全部房间");
            return convertView;
        }
        viewHolder.tvsingle.setText(list.get(position-1).getROOMNO()+"");
        return convertView;
    }

    public class ViewHolder {
        public final TextView tvsingle;
        public final View root;

        public ViewHolder(View root) {
            tvsingle = (TextView) root.findViewById(R.id.tv_single);
            this.root = root;
        }
    }
}
