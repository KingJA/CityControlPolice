package com.tdr.citycontrolpolice.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.entity.OCR_Kj;

import java.util.List;

/**
 * 项目名称：
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/6/20 15:33
 * 修改备注：
 */
public class QueueAdapter extends BaseSimpleAdapter<OCR_Kj> {
    public QueueAdapter(Context context, List<OCR_Kj> list) {
        super(context, list);
    }

    @Override
    public View simpleGetView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View
                    .inflate(context, R.layout.item_ocr, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_name.setText(list.get(position).getNAME());
        viewHolder.tv_card.setText(list.get(position).getIDENTITYCARD());

        return convertView;
    }

    public class ViewHolder {
        public final TextView tv_name;
        public final TextView tv_card;
        public final View root;

        public ViewHolder(View root) {
            tv_name = (TextView) root.findViewById(R.id.et_name);
            tv_card = (TextView) root.findViewById(R.id.tv_card);
            this.root = root;
        }
    }
}
