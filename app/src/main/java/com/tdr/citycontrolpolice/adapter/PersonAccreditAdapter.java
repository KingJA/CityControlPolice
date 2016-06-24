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
public class PersonAccreditAdapter extends BaseSimpleAdapter<ChuZuWu_MenPaiAuthorizationList.ContentBean.PERSONNELINFOLISTBean> {
    public PersonAccreditAdapter(Context context, List<ChuZuWu_MenPaiAuthorizationList.ContentBean.PERSONNELINFOLISTBean> list) {
        super(context, list);
    }

    @Override
    public View simpleGetView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View
                    .inflate(context, R.layout.item_person_accredit, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvinfoname.setText(list.get(position).getNAME());
        viewHolder.tvinfophone.setText(list.get(position).getPHONENUM());
        viewHolder.tvinfocard.setText(list.get(position).getIDENTITYCARD());
        viewHolder.tvinfocardId.setText(list.get(position).getCARDID());
        viewHolder.tvinfocardType.setText(getCarType(list.get(position).getCARDTYPE()));
        return convertView;
    }


    public class ViewHolder {
        public final TextView tvinfoname;
        public final TextView tvinfophone;
        public final TextView tvinfocard;
        public final TextView tvinfocardId;
        public final TextView tvinfocardType;
        public final View root;

        public ViewHolder(View root) {
            tvinfoname = (TextView) root.findViewById(R.id.tv_infoname);
            tvinfophone = (TextView) root.findViewById(R.id.tv_info_phone);
            tvinfocard = (TextView) root.findViewById(R.id.tv_info_card);
            tvinfocardId = (TextView) root.findViewById(R.id.tv_info_cardId);
            tvinfocardType = (TextView) root.findViewById(R.id.tv_info_cardType);
            this.root = root;
        }
    }

    private String getCarType(int typeNum) {
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
