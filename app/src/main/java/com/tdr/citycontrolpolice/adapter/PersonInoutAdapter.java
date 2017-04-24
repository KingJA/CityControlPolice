package com.tdr.citycontrolpolice.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.entity.ChuZuWu_DeviceInOutList;

import java.util.List;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/3/24 16:07
 * 修改备注：
 */
public class PersonInoutAdapter extends BaseSimpleAdapter<ChuZuWu_DeviceInOutList.ContentBean.PERSONNELINFOLISTBean> {
    private final String[] typeArr = {};

    public PersonInoutAdapter(Context context, List<ChuZuWu_DeviceInOutList.ContentBean.PERSONNELINFOLISTBean> list) {
        super(context, list);
    }

    @Override
    public View simpleGetView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View
                    .inflate(context, R.layout.item_person_inout, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvdeviceId.setText(list.get(position).getDEVICECODE());//设备编号
        viewHolder.tvmaxHeight.setText(list.get(position).getMAXHEIGHT()+" cm");//最大身高
        viewHolder.tvinoutCount.setText(list.get(position).getPEOPLENUMBER());//进出人数
        viewHolder.tvinoutStatus.setText(list.get(position).getINOROUT());//进出状态

        viewHolder.tvpassSpeed.setText(getPassSpeedStatus(list.get(position).getPASSTIME()));//过门速度
        viewHolder.tvalarmLevel.setText(list.get(position).getSHOCKRANGE()+" °");//震动幅度
        viewHolder.tvmaxAlarmTime.setText(list.get(position).getMAXSHOCKTIME()+" s");//最大震动时刻
        viewHolder.tvpassTime.setText(list.get(position).getPASSTIME()+" s");//经过时间
        viewHolder.tvcheckTime.setText(list.get(position).getDEVICETIME());//检测时间
        return convertView;
    }


    public class ViewHolder {
        public final TextView tvdeviceId;
        public final TextView tvmaxHeight;
        public final TextView tvinoutCount;
        public final TextView tvinoutStatus;
        public final TextView tvpassSpeed;
        public final TextView tvcloseLevel;
        public final TextView tvalarmLevel;
        public final TextView tvmaxAlarmTime;
        public final TextView tvpassTime;
        public final TextView tvcheckTime;
        public final View root;

        public ViewHolder(View root) {
            tvdeviceId = (TextView) root.findViewById(R.id.tv_deviceId);
            tvmaxHeight = (TextView) root.findViewById(R.id.tv_maxHeight);
            tvinoutCount = (TextView) root.findViewById(R.id.tv_inoutCount);
            tvinoutStatus = (TextView) root.findViewById(R.id.tv_inoutStatus);
            tvpassSpeed = (TextView) root.findViewById(R.id.tv_passSpeed);
            tvcloseLevel = (TextView) root.findViewById(R.id.tv_closeLevel);
            tvalarmLevel = (TextView) root.findViewById(R.id.tv_alarmLevel);
            tvmaxAlarmTime = (TextView) root.findViewById(R.id.tv_maxAlarmTime);
            tvpassTime = (TextView) root.findViewById(R.id.tv_passTime);
            tvcheckTime = (TextView) root.findViewById(R.id.tv_checkTime);
            this.root = root;
        }
    }

    private String getPassSpeedStatus(String passTime) {
        passTime=passTime==null?"0":passTime;
        String result = "未知";
        Float time = Float.valueOf(passTime);
        if (time <= 1.5f) {
            result = "快";
        } else if (time > 1.5f && time <= 3.0f) {
            result = "正常";
        } else if (time > 3.0f) {
            result = "慢";
        }
        return result;

    }
}
