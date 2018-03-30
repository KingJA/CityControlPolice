package com.tdr.citycontrolpolice.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.entity.ChuZuWu_ComprehensiveInfo;
import com.tdr.citycontrolpolice.entity.ChuZuWu_RoomInfo;

import java.util.List;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/3/25 17:19
 * 修改备注：
 */
public class RoomInfoAdapter extends BaseSimpleAdapter<ChuZuWu_RoomInfo.ContentBean> {
    public RoomInfoAdapter(Context context, List<ChuZuWu_RoomInfo.ContentBean> list) {
        super(context, list);
    }

    @Override
    public View simpleGetView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View
                    .inflate(context, R.layout.item_person_info, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }


    public class ViewHolder {
        public final TextView tvinfoname;
        public final ImageView ivchang;
        public final ImageView ivyao;
        public final ImageView ivliu;
        public final TextView tvdetail;
        public final TextView tvinfophone;
        public final TextView tvinfocard;
        public final ImageView ivwdj;
        public final ImageView ivtf;
        public final ImageView ivxj;
        public final ImageView ivtz;
        public final ImageView ivwgl;
        public final ImageView iv_zi;
        public final LinearLayout lltag;
        public final View root;

        public ViewHolder(View root) {
            tvinfoname = (TextView) root.findViewById(R.id.tv_outin_name);
            ivchang = (ImageView) root.findViewById(R.id.iv_chang);
            iv_zi = (ImageView) root.findViewById(R.id.iv_zi);
            ivyao = (ImageView) root.findViewById(R.id.iv_yao);
            ivliu = (ImageView) root.findViewById(R.id.iv_liu);
            tvdetail = (TextView) root.findViewById(R.id.tv_detail);
            tvinfophone = (TextView) root.findViewById(R.id.tv_info_phone);
            tvinfocard = (TextView) root.findViewById(R.id.tv_info_card);
            ivwdj = (ImageView) root.findViewById(R.id.iv_wdj);
            ivtf = (ImageView) root.findViewById(R.id.iv_tf);
            ivxj = (ImageView) root.findViewById(R.id.iv_xj);
            ivtz = (ImageView) root.findViewById(R.id.iv_tz);
            ivwgl = (ImageView) root.findViewById(R.id.iv_wgl);
            lltag = (LinearLayout) root.findViewById(R.id.ll_tag);
            this.root = root;
        }
    }
}
