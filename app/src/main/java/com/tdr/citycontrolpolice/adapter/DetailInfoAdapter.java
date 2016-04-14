package com.tdr.citycontrolpolice.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.entity.ChuZuWu_MenPaiAuthorizationList;

import java.util.List;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/3/25 17:19
 * 修改备注：
 */
public class DetailInfoAdapter extends BaseSimpleAdapter<ChuZuWu_MenPaiAuthorizationList.ContentBean.PERSONNELINFOLISTBean> {
    public DetailInfoAdapter(Context context, List<ChuZuWu_MenPaiAuthorizationList.ContentBean.PERSONNELINFOLISTBean> list) {
        super(context, list);
    }

    @Override
    public View simpleGetView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View
                    .inflate(context, R.layout.item_czf_person, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvinfoname.setText(list.get(position).getNAME());
        viewHolder.tvinfophone.setText(list.get(position).getPHONENUM());
        viewHolder.tvinfocard.setText(list.get(position).getIDENTITYCARD());

        return convertView;
    }

    public class ViewHolder {
        public final TextView tvinfoname;
        public final TextView tvinfophone;
        public final TextView tvinfocard;
        public final View root;

        public ViewHolder(View root) {
            tvinfoname = (TextView) root.findViewById(R.id.tv_info_name);
            tvinfophone = (TextView) root.findViewById(R.id.tv_info_phone);
            tvinfocard = (TextView) root.findViewById(R.id.tv_info_card);
            this.root = root;
        }
    }
}
