package com.kingja.ui.popupwindow;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.kingja.ui.R;

import java.util.List;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/4/11 17:14
 * 修改备注：
 */
public class SingleTextAdapter extends BaseSimpleAdapter<String> {
    public SingleTextAdapter(Context context, List<String> list) {
        super(context, list);
    }

    @Override
    public View simpleGetView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View
                    .inflate(context, R.layout.item_single_text, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvsingle.setText(list.get(position));
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
