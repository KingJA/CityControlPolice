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
public class CzfInAdapter extends BaseSimpleAdapter<ChuZuWu_LKSelfReportingList.ContentBean.PERSONNELINFOLISTBean> {

    private OnItemDeleteListener onItemDeleteListener;

    public CzfInAdapter(Context context, List<ChuZuWu_LKSelfReportingList.ContentBean.PERSONNELINFOLISTBean> list) {
        super(context, list);
    }

    @Override
    public View simpleGetView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View
                    .inflate(context, R.layout.item_apply_in, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (list.get(position).isExplend()) {
            viewHolder.llexpand.setVisibility(View.VISIBLE);
            viewHolder.ivapplyarrow.setBackgroundResource(R.drawable.bg_arrow_up);
        } else {
            viewHolder.llexpand.setVisibility(View.GONE);
            viewHolder.ivapplyarrow.setBackgroundResource(R.drawable.bg_arrow_down);
        }
        viewHolder.tvinfoname.setText(list.get(position).getNAME());
        setCheckStatus(viewHolder.iv_checkStatus,list.get(position).getSTATUS());
        viewHolder.tvinfomac.setText(getMultiMacs(list.get(position).getMACLIST()));
        viewHolder.tvinfophone.setText(list.get(position).getPHONENUM());
        viewHolder.tvinfocard.setText(list.get(position).getIDENTITYCARD());
        viewHolder.tvinfotime.setText(list.get(position).getINTIME().substring(0, 10));
        viewHolder.tv_info_height.setText(list.get(position).getHEIGHT()+" cm");

        viewHolder.rltop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.get(position).setExplend(!list.get(position).isExplend());
                notifyDataSetChanged();
            }
        });
        viewHolder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemDeleteListener != null) {
                    onItemDeleteListener.onDelete(position, list.get(position).getLISTID());
                }
            }
        });

        return convertView;
    }

    public void deleteItem(int position) {
        list.remove(position);
        notifyDataSetChanged();
    }


    public class ViewHolder {
        public final TextView tvinfoname;
        public final TextView tvinfotime;
        public final ImageView ivapplyarrow;
        public final ImageView iv_checkStatus;
        public final ImageView iv_delete;
        public final RelativeLayout rltop;
        public final TextView tvinfophone;
        public final TextView tvinfocard;
        public final TextView tvinfomac;
        public final TextView tv_info_height;
        public final LinearLayout llexpand;
        public final View root;

        public ViewHolder(View root) {
            tv_info_height = (TextView) root.findViewById(R.id.tv_info_height);
            tvinfoname = (TextView) root.findViewById(R.id.tv_outin_name);
            tvinfotime = (TextView) root.findViewById(R.id.tv_info_time);
            ivapplyarrow = (ImageView) root.findViewById(R.id.iv_apply_arrow);
            iv_delete = (ImageView) root.findViewById(R.id.iv_delete);
            iv_checkStatus = (ImageView) root.findViewById(R.id.iv_checkStatus);
            rltop = (RelativeLayout) root.findViewById(R.id.rl_top);
            tvinfophone = (TextView) root.findViewById(R.id.tv_info_phone);
            tvinfocard = (TextView) root.findViewById(R.id.tv_info_card);
            tvinfomac = (TextView) root.findViewById(R.id.tv_info_mac);
            llexpand = (LinearLayout) root.findViewById(R.id.ll_expand);
            this.root = root;
        }
    }

    public interface OnItemDeleteListener {
        void onDelete(int position, String LISTID);
    }

    public void setonItemDeleteListener(OnItemDeleteListener onItemDeleteListener) {
        this.onItemDeleteListener = onItemDeleteListener;
    }
//    审核状态（0未审核，1审核通过，2审核不通过）

    public void setCheckStatus(ImageView imageView,String checkStatus) {
        switch (checkStatus) {
            case "0":
                imageView.setBackgroundResource(R.drawable.bg_unchecked);
                break;
            case "1":
                imageView.setBackgroundResource(R.drawable.bg_checked);
                break;
            case "2":
                imageView.setBackgroundResource(R.drawable.bg_refused);
                break;
            default:
                break;

        }
    }

    public String getMultiMacs(String macList) {
        String[] macs = macList.split(",");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < macs.length; i++) {
            if (i == macs.length - 1) {
                sb.append(macs[i]);
            }else{
                sb.append(macs[i]+"\n");
            }
        }
        return sb.toString();
    }
}
