package com.tdr.citycontrolpolice.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.entity.ChuZuWu_SwipeCardList;

import java.util.List;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/3/24 16:07
 * 修改备注：
 */
public class CzfCardAdapter extends BaseSimpleAdapter<ChuZuWu_SwipeCardList.ContentBean.PERSONNELINFOLISTBean> {
    private final String[] typeArr = {};

    public CzfCardAdapter(Context context, List<ChuZuWu_SwipeCardList.ContentBean.PERSONNELINFOLISTBean> list) {
        super(context, list);
    }

    @Override
    public View simpleGetView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View
                    .inflate(context, R.layout.item_card, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        if (list.get(position).isExplend()) {
//            viewHolder.llexpand.setVisibility(View.VISIBLE);
//            viewHolder.ivoutinarrow.setBackgroundResource(R.drawable.bg_arrow_up);
//        } else {
//            viewHolder.llexpand.setVisibility(View.GONE);
//            viewHolder.ivoutinarrow.setBackgroundResource(R.drawable.bg_arrow_down);
//        }

        viewHolder.tvoutin_name.setText(list.get(position).getNAME());
        viewHolder.tvoutincard.setText(list.get(position).getIDENTITYCARD());
        viewHolder.tvoutincardNo.setText(list.get(position).getCARDID());
        viewHolder.tvoutintype.setText(getType(list.get(position).getCARDTYPE()));
        viewHolder.tvoutintime.setText(list.get(position).getDEVICETIME());

//        viewHolder.lloutintop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                list.get(position).setExplend(!list.get(position).isExplend());
//                notifyDataSetChanged();
//            }
//        });
        return convertView;
    }


    public class ViewHolder {
        public final TextView tvoutin_name;
        public final ImageView ivoutinarrow;
        public final LinearLayout lloutintop;
        public final TextView tvoutincard;
        public final TextView tvoutincardNo;
        public final TextView tvoutintype;
        public final TextView tvoutintime;
        public final LinearLayout llexpand;
        public final View root;

        public ViewHolder(View root) {
            tvoutin_name = (TextView) root.findViewById(R.id.tv_outin_name);
            ivoutinarrow = (ImageView) root.findViewById(R.id.iv_outin_arrow);
            lloutintop = (LinearLayout) root.findViewById(R.id.ll_outin_top);
            tvoutincard = (TextView) root.findViewById(R.id.tv_outin_card);
            tvoutincardNo = (TextView) root.findViewById(R.id.tv_outin_cardNo);
            tvoutintype = (TextView) root.findViewById(R.id.tv_outin_type);
            tvoutintime = (TextView) root.findViewById(R.id.tv_outin_time);
            llexpand = (LinearLayout) root.findViewById(R.id.ll_expand);
            this.root = root;
        }
    }

    private String getType(int typeNum) {
        switch (typeNum) {
            case 1:
                return "房东卡";
            case 2:
                return "用户卡";
            case 3:
                return "警察卡";
            case 4:
                return "协警卡";
            case 5:
                return "e居卡";
            case 6:
                return "电子门钥";
            case 9:
                return "身份证";
            case 25:
                return "运维卡";
            default:
                return "未知卡";
        }
    }
}
