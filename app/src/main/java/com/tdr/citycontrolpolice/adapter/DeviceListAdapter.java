package com.tdr.citycontrolpolice.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.dao.DbDaoXutils3;
import com.tdr.citycontrolpolice.entity.Basic_Dictionary_Kj;
import com.tdr.citycontrolpolice.entity.ChuZuWu_DeviceLists;
import com.tdr.citycontrolpolice.util.TimeUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目名称：常用工具类
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/4/723:36
 * 修改备注：
 */
public class DeviceListAdapter extends BaseSimpleAdapter<ChuZuWu_DeviceLists.ContentBean> {
    private OnDeviceChangeListener onDeviceChangeListener;
    private Map<String, String> typeMap = new HashMap<>();
    private List<Basic_Dictionary_Kj> typeList;
    private String roomId;


    public DeviceListAdapter(String roomId, Context context, List<ChuZuWu_DeviceLists.ContentBean> list) {
        super(context, list);
        this.roomId = roomId;
        initDeviceType();
    }

    private void initDeviceType() {

        typeList = (List<Basic_Dictionary_Kj>) DbDaoXutils3.getInstance().sleectAll(Basic_Dictionary_Kj.class, "COLUMNCODE", "DEVICETYPE");
        for (Basic_Dictionary_Kj bean : typeList) {
            typeMap.put(bean.getCOLUMNVALUE(), bean.getCOLUMNCOMMENT());
        }
    }

    @Override
    public View simpleGetView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View
                    .inflate(context, R.layout.item_device, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvdevicename.setText(list.get(position).getDEVICENAME() + "(" + typeMap.get(list.get(position).getDEVICETYPE()) + ":" + list.get(position).getDEVICECODE() + ")");
        viewHolder.ivdevicestate.setBackgroundResource(TimeUtil.isOneDay(list.get(position).getDEVICETIME()) ? R.drawable.circle_on : R.drawable.circle_off);
        viewHolder.tv_isbund.setText(list.get(position).getISBUNG() == 0 ? "未下" : "下发");
        viewHolder.tv_isbund.setTextColor(list.get(position).getISBUNG() == 0 ? context.getResources().getColor(R.color.font_title) : context.getResources().getColor(R.color.bg_orange));

        viewHolder.ivdevicechange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDeviceChangeListener != null) {
                    onDeviceChangeListener.onChange(list.get(position), roomId);
                }
            }
        });

        return convertView;
    }

    public class ViewHolder {
        public final ImageView ivdevicestate;
        public final TextView tv_isbund;
        public final TextView tvdevicename;
        public final ImageView ivdevicechange;
        public final RelativeLayout rlroom;
        public final View root;

        public ViewHolder(View root) {
            ivdevicestate = (ImageView) root.findViewById(R.id.iv_device_state);
            tv_isbund = (TextView) root.findViewById(R.id.tv_isbund);
            tvdevicename = (TextView) root.findViewById(R.id.tv_device_name);
            ivdevicechange = (ImageView) root.findViewById(R.id.iv_device_change);
            rlroom = (RelativeLayout) root.findViewById(R.id.rl_room);
            this.root = root;
        }
    }

    public interface OnDeviceChangeListener {
        void onChange(ChuZuWu_DeviceLists.ContentBean bean, String roomId);
    }

    public void setOnDeviceChangeListener(OnDeviceChangeListener onDeviceChangeListener) {
        this.onDeviceChangeListener = onDeviceChangeListener;
    }
}
