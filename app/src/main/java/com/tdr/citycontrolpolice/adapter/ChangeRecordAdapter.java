package com.tdr.citycontrolpolice.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.entity.ChuZuWu_ChangeMenPaiList;

import java.util.List;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/3/24 16:07
 * 修改备注：
 */
public class ChangeRecordAdapter extends BaseSimpleAdapter<ChuZuWu_ChangeMenPaiList.ContentBean> {

    public ChangeRecordAdapter(Context context, List<ChuZuWu_ChangeMenPaiList.ContentBean> list) {
        super(context, list);
    }

    @Override
    public View simpleGetView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View
                    .inflate(context, R.layout.item_code_record, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvrecordold.setText(list.get(position).getOLDDEVICECODE());
        viewHolder.tvrecordnew.setText(list.get(position).getNEWDEVICECODE());
        viewHolder.tvrecordmsg.setText(list.get(position).getREASON());
        viewHolder.tvrecordinfo.setText(list.get(position).getRECORDTIME()+"("+getReasonType(list.get(position).getREASON_TYPE())+")");

        return convertView;
    }


    private String getReasonType(int reason) {
        switch (reason) {
            case 1:
                return "丢失";
            case 2:
                return "损坏";
            case 9:
                return "其他";
            default:
                return "未知";
        }
    }


    public class ViewHolder {
        public final TextView tvrecordold;
        public final ImageView ivrecordchange;
        public final TextView tvrecordnew;
        public final TextView tvrecordinfo;
        public final TextView tvrecordmsg;
        public final View root;

        public ViewHolder(View root) {
            tvrecordold = (TextView) root.findViewById(R.id.tv_record_old);
            ivrecordchange = (ImageView) root.findViewById(R.id.iv_record_change);
            tvrecordnew = (TextView) root.findViewById(R.id.tv_record_new);
            tvrecordinfo = (TextView) root.findViewById(R.id.tv_record_info);
            tvrecordmsg = (TextView) root.findViewById(R.id.tv_record_msg);
            this.root = root;
        }
    }
}
