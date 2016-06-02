package com.tdr.citycontrolpolice.adapter;

import android.app.Activity;
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
    private int outPosition;
    private String roomId;
    private String rommNo;
    private Activity activity;


    public DeviceListAdapter(int outPosition, String roomId, String rommNo, Context context, Activity activity, List<ChuZuWu_DeviceLists.ContentBean> list) {
        super(context, list);
        this.outPosition = outPosition;
        this.roomId = roomId;
        this.rommNo = rommNo;
        this.activity = activity;
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

        viewHolder.tvdevicename.setText(list.get(position).getDEVICENAME() + "  (" + typeMap.get(list.get(position).getDEVICETYPE()) + " : " + list.get(position).getDEVICECODE() + ")");
        setTypeImg(viewHolder.ivdevicestate, list.get(position).getDEVICETYPE(), TimeUtil.isOneDay(list.get(position).getDEVICETIME()));
        viewHolder.ivisbund.setBackgroundResource(list.get(position).getISBUNG() == 0 ? R.drawable.bg_unbund : R.drawable.bg_isbund);

        viewHolder.ivdevice_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDeviceChangeListener != null) {
                    onDeviceChangeListener.onChange(list.get(position), roomId, rommNo, position, outPosition);
                }
            }
        });

        return convertView;
    }

    public class ViewHolder {
        public final ImageView ivdevicestate;
        public final ImageView ivisbund;
        public final TextView tvdevicename;
        public final ImageView ivdevice_more;
        public final RelativeLayout rlroom;
        public final View root;

        public ViewHolder(View root) {
            ivdevicestate = (ImageView) root.findViewById(R.id.iv_device_state);
            ivisbund = (ImageView) root.findViewById(R.id.iv_isbund);
            tvdevicename = (TextView) root.findViewById(R.id.tv_device_name);
            ivdevice_more = (ImageView) root.findViewById(R.id.iv_device_more);
            rlroom = (RelativeLayout) root.findViewById(R.id.rl_room);
            this.root = root;
        }
    }

    public interface OnDeviceChangeListener {
        void onChange(ChuZuWu_DeviceLists.ContentBean bean, String roomId, String roomNo, int position, int outPosition);
    }

    public void setOnDeviceChangeListener(OnDeviceChangeListener onDeviceChangeListener) {
        this.onDeviceChangeListener = onDeviceChangeListener;
    }

    public void changeDevice(int position, String type, String deviceCode) {
        list.get(position).setDEVICETYPE(type);
        list.get(position).setDEVICECODE(deviceCode);
        this.notifyDataSetChanged();
    }

    public void setTypeImg(ImageView iv, String type, boolean isOneDay) {
        if ("1072".equals(type)) {
            iv.setBackgroundResource(isOneDay ? R.drawable.bg_menjie_on : R.drawable.bg_menjie_off);
        } else {
            iv.setBackgroundResource(isOneDay ? R.drawable.bg_other_on : R.drawable.bg_other_off);
        }

    }
}
