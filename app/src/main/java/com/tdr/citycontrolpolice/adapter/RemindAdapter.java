package com.tdr.citycontrolpolice.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.entity.ChuZuWu_PushInfoOfMonitorRoom;

import java.util.List;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/3/24 16:07
 * 修改备注：
 */
public class RemindAdapter extends BaseSimpleAdapter<ChuZuWu_PushInfoOfMonitorRoom.ContentBean> {

    public RemindAdapter(Context context, List<ChuZuWu_PushInfoOfMonitorRoom.ContentBean> list) {
        super(context, list);
    }

    @Override
    public View simpleGetView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View
                    .inflate(context, R.layout.item_remind, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvremindaddress.setText(list.get(position).getADDRESS()+list.get(position).getROOMNO()+"室");
        viewHolder.tvremindinfo.setText(list.get(position).getPUSHTIME()+"("+getAttentionType(list.get(position).getREMIND_TYPE())+")");
        viewHolder.tvremindmsg.setText(list.get(position).getMESSAGE());
        return convertView;
    }


    public class ViewHolder {
        public final ImageView ivremind;
        public final TextView tvremindaddress;
        public final TextView tvremindinfo;
        public final TextView tvremindmsg;
        public final View root;

        public ViewHolder(View root) {
            ivremind = (ImageView) root.findViewById(R.id.iv_remind);
            tvremindaddress = (TextView) root.findViewById(R.id.tv_remind_address);
            tvremindinfo = (TextView) root.findViewById(R.id.tv_remind_info);
            tvremindmsg = (TextView) root.findViewById(R.id.tv_remind_msg);
            this.root = root;
        }
    }
    private String getAttentionType(int type) {
        String result;
        switch (type) {
            case -1:
                result = "取消关注";
                break;
            case 0:
                result = "仅关注";
                break;
            case 1:
                result = "提醒一次";
                break;
            case 2:
                result = "提醒多次";
                break;
            default:
                result = "未知类型";
                break;
        }
        return result;
    }

}
