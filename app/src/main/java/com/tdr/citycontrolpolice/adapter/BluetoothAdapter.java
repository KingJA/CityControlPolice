package com.tdr.citycontrolpolice.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.entity.BluetoothBean;

import java.util.List;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/4/7 10:56
 * 修改备注：
 */
public class BluetoothAdapter extends BaseSimpleAdapter<BluetoothBean> {
    public BluetoothAdapter(Context context, List<BluetoothBean> list) {
        super(context, list);
    }

    @Override
    public View simpleGetView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View
                    .inflate(context, R.layout.item_dialog_bluetooth, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvblueName.setText(list.get(position).getName());
        return convertView;
    }

    public class ViewHolder {
        public final TextView tvblueName;
        public final View root;

        public ViewHolder(View root) {
            tvblueName = (TextView) root.findViewById(R.id.tv_blueName);
            this.root = root;
        }
    }

}
