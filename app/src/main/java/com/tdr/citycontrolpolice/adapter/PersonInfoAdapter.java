package com.tdr.citycontrolpolice.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.entity.ChuZuWu_ComprehensiveInfo;

import java.util.List;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/3/25 17:19
 * 修改备注：
 */
public class PersonInfoAdapter extends BaseSimpleAdapter<ChuZuWu_ComprehensiveInfo.ContentBean.PERSONNELINFOLISTBean> {
    public PersonInfoAdapter(Context context, List<ChuZuWu_ComprehensiveInfo.ContentBean.PERSONNELINFOLISTBean> list) {
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

        viewHolder.ivtf.setVisibility(list.get(position).getTF()==0?View.GONE:View.VISIBLE);
        viewHolder.ivxj.setVisibility(list.get(position).getISXJ()==0?View.GONE:View.VISIBLE);
        viewHolder.ivliu.setVisibility(list.get(position).getLK()==0?View.GONE:View.VISIBLE);
        viewHolder.ivyao.setVisibility(list.get(position).getDEV() == 0 ? View.GONE : View.VISIBLE);
        viewHolder.iv_zi.setVisibility(list.get(position).getZZSB() == 0 ? View.GONE : View.VISIBLE);

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
