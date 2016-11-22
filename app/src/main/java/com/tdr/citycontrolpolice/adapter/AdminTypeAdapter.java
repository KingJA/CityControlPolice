package com.tdr.citycontrolpolice.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.entity.Basic_Dictionary_Kj;

import java.util.List;


/**
 * Description：TODO
 * Create Time：2016/8/5 14:32
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class AdminTypeAdapter extends BaseSimpleAdapter<Basic_Dictionary_Kj> {
    public AdminTypeAdapter(Context context, List<Basic_Dictionary_Kj> list) {
        super(context, list);
    }

    @Override
    public View simpleGetView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View
                    .inflate(context, R.layout.item_single_text, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_label.setText(list.get(position).getCOLUMNCOMMENT());

        return convertView;
    }


    public class ViewHolder {
        public final TextView tv_label;
        public final View root;

        public ViewHolder(View root) {
            tv_label = (TextView) root.findViewById(R.id.tv_label);
            this.root = root;
        }
    }
}
