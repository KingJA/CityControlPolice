package com.tdr.citycontrolpolice.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.entity.ChuZuWu_LKSelfReportingList;

import java.util.List;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/3/24 16:07
 * 修改备注：
 */
public class CzfApplyAdapter extends BaseSimpleAdapter<ChuZuWu_LKSelfReportingList.ContentBean.PERSONNELINFOLISTBean> {

    public CzfApplyAdapter(Context context, List<ChuZuWu_LKSelfReportingList.ContentBean.PERSONNELINFOLISTBean> list) {
        super(context, list);
    }

    @Override
    public View simpleGetView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View
                    .inflate(context, R.layout.item_czf_apply2, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvinfoname.setText(list.get(position).getNAME());
        viewHolder.tvinfophone.setText(list.get(position).getPHONENUM());
        viewHolder.tvinfocard.setText(list.get(position).getIDENTITYCARD());
//        viewHolder.tvinforoomNo.setText(list.get(position).get);
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.rltop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalViewHolder.llexpand.getVisibility() == View.GONE) {
                    finalViewHolder.llexpand.setVisibility(View.VISIBLE);
                    finalViewHolder.ivapplyarrow.setBackgroundResource(R.drawable.bg_arrow_up);
                } else {
                    finalViewHolder.llexpand.setVisibility(View.GONE);
                    finalViewHolder.ivapplyarrow.setBackgroundResource(R.drawable.bg_arrow_down);
                }
            }
        });
        return convertView;
    }

    public class ViewHolder {
        public final TextView tvinfoname;
        public final ImageView ivapplyarrow;
        public final RelativeLayout rltop;
        public final TextView tvinfophone;
        public final TextView tvinfocard;
        //        public final TextView tvinforoomNo;
        public final LinearLayout llexpand;
        public final View root;

        public ViewHolder(View root) {
            tvinfoname = (TextView) root.findViewById(R.id.tv_info_name);
            ivapplyarrow = (ImageView) root.findViewById(R.id.iv_apply_arrow);
            rltop = (RelativeLayout) root.findViewById(R.id.rl_top);
            tvinfophone = (TextView) root.findViewById(R.id.tv_info_phone);
            tvinfocard = (TextView) root.findViewById(R.id.tv_info_card);
//            tvinforoomNo = (TextView) root.findViewById(R.id.tv_info_roomNo);
            llexpand = (LinearLayout) root.findViewById(R.id.ll_expand);
            this.root = root;
        }
    }

//    public int getVisibility(int visibility) {
//        return visibility==0?View.GONE:View.VISIBLE;
//    }


}
