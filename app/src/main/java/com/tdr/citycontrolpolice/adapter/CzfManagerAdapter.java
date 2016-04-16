package com.tdr.citycontrolpolice.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.entity.KjChuZuWuInfo;

import java.util.List;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/3/24 14:07
 * 修改备注：
 */
public class CzfManagerAdapter extends BaseSimpleAdapter<KjChuZuWuInfo.ContentBean.RoomListBean> {
    private static final String TAG = "CzfManagerAdapter";

    public CzfManagerAdapter(Context context, List<KjChuZuWuInfo.ContentBean.RoomListBean> list) {
        super(context, list);
    }

    @Override
    public View simpleGetView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View
                    .inflate(context, R.layout.item_czf_manager, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvinforoom.setText(list.get(position).getROOMNO() + "");
        viewHolder.tvinfoperson.setText(list.get(position).getHEADCOUT() == -1 ? "未知" : list.get(position).getHEADCOUT() + "");

        return convertView;
    }

    public class ViewHolder {
        public final TextView tvinforoom;
        public final TextView tvinfoperson;
        public final View root;

        public ViewHolder(View root) {
            tvinforoom = (TextView) root.findViewById(R.id.tv_info_room);
            tvinfoperson = (TextView) root.findViewById(R.id.tv_info_person);
            this.root = root;
        }
    }
}
