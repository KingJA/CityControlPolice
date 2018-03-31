package com.tdr.citycontrolpolice.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.entity.ChuZuWu_LKSelfReportingList;
import com.tdr.citycontrolpolice.entity.SQL_Query;

import java.util.List;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/3/24 16:07
 * 修改备注：
 */
public class StandardCzfHistoryAdapter extends BaseSimpleAdapter<SQL_Query> {

    public StandardCzfHistoryAdapter(Context context, List<SQL_Query> list) {
        super(context, list);
    }

    @Override
    public View simpleGetView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View
                    .inflate(context, R.layout.item_history, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvhistory.setText(list.get(position).getKeyWord());
        return convertView;
    }


    public class ViewHolder {
        public final TextView tvhistory;
        public final View root;

        public ViewHolder(View root) {
            tvhistory = (TextView) root.findViewById(R.id.tv_history);
            this.root = root;
        }
    }
}
