package com.tdr.citycontrolpolice.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.activity.AttentionEditActivity;
import com.tdr.citycontrolpolice.entity.ChuZuWu_InquireFavorites;

import java.util.List;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/3/24 16:07
 * 修改备注：
 */
public class AttentionQueryAdapter extends BaseSimpleAdapter<ChuZuWu_InquireFavorites.ContentBean> {

    public AttentionQueryAdapter(Context context, List<ChuZuWu_InquireFavorites.ContentBean> list) {
        super(context, list);
    }

    @Override
    public View simpleGetView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View
                    .inflate(context, R.layout.item_attention_query, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvaddress.setText(list.get(position).getADDRESS());
        viewHolder.tvinfo.setText(list.get(position).getOWNERNAME()+"("+list.get(position).getRoomList().size()+"个房间)  "+list.get(position).getPHONE());
        viewHolder.tvattention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AttentionEditActivity.goActivity(context,list.get(position).getHOUSEID());
            }
        });

        return convertView;
    }


    public class ViewHolder {
        public final TextView tvattention;
        public final TextView tvaddress;
        public final TextView tvinfo;
        public final View root;

        public ViewHolder(View root) {
            tvattention = (TextView) root.findViewById(R.id.tv_attention);
            tvaddress = (TextView) root.findViewById(R.id.tv_address);
            tvinfo = (TextView) root.findViewById(R.id.tv_info);
            this.root = root;
        }
    }
}
