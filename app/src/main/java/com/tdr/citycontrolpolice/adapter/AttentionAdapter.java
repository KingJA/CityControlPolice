package com.tdr.citycontrolpolice.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.entity.ChuZuWu_RoomListOfFavorites;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/3/24 16:07
 * 修改备注：
 */
public class AttentionAdapter extends BaseSimpleAdapter<ChuZuWu_RoomListOfFavorites.ContentBean.MonitorRoomListBean> {

    public AttentionAdapter(Context context, List<ChuZuWu_RoomListOfFavorites.ContentBean.MonitorRoomListBean> list) {
        super(context, list);
    }

    @Override
    public View simpleGetView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View
                    .inflate(context, R.layout.item_attention_room, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.cbattention.setText(list.get(position).getROOMNO());
        viewHolder.cbattention.setChecked(list.get(position).isChecked());
        viewHolder.tvattentiontype.setText(getAttentionType(list.get(position).getREMIND_TYPE()));
        if (selectPosition == -1) {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.bg_transparent));
        }else{
        convertView.setBackgroundColor(position == selectPosition ? context.getResources().getColor(R.color.bg_gray_kj) : context.getResources().getColor(R.color.bg_transparent));
        }


        viewHolder.cbattention.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!buttonView.isPressed())
                    return;
                list.get(position).setChecked(isChecked);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    public class ViewHolder {
        public final CheckBox cbattention;
        public final TextView tvattentiontype;
        public final View root;

        public ViewHolder(View root) {
            cbattention = (CheckBox) root.findViewById(R.id.cb_attention);
            tvattentiontype = (TextView) root.findViewById(R.id.tv_attention_type);
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

    public void setAttentionByList(int type, String phone, String fromDate, String toDate, String fromTime, String toTime) {
        for (ChuZuWu_RoomListOfFavorites.ContentBean.MonitorRoomListBean bean : list) {
            if (bean.isChecked()) {
                bean.setREMIND_TYPE(type);
                bean.setTARGET(phone);
                bean.setSTARTDATE(fromDate);
                bean.setENDDATE(toDate);
                bean.setSTARTTIME(fromTime);
                bean.setENDTIME(toTime);
            }
        }
        notifyDataSetChanged();
    }

    public void setAttentionByPosition(int type, String phone, String fromDate, String toDate, String fromTime, String toTime, int position) {
        ChuZuWu_RoomListOfFavorites.ContentBean.MonitorRoomListBean bean = list.get(position);
        bean.setREMIND_TYPE(type);
        bean.setTARGET(phone);
        bean.setSTARTDATE(fromDate);
        bean.setENDDATE(toDate);
        bean.setSTARTTIME(fromTime);
        bean.setENDTIME(toTime);
        notifyDataSetChanged();
    }

    public List<ChuZuWu_RoomListOfFavorites.ContentBean.MonitorRoomListBean> getAttentionList() {
        List<ChuZuWu_RoomListOfFavorites.ContentBean.MonitorRoomListBean> result = new ArrayList<>();
        for (ChuZuWu_RoomListOfFavorites.ContentBean.MonitorRoomListBean bean : list) {
            if (bean.isChecked()) {
                result.add(bean);
            }
        }
        return result;
    }

    public void reset() {
        selectPosition=-1;
        notifyDataSetChanged();
    }
}
