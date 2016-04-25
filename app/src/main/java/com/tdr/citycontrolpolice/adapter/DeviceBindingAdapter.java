package com.tdr.citycontrolpolice.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.entity.KjChuZuWuInfo;
import com.tdr.citycontrolpolice.util.ToastUtil;

import java.util.List;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/4/6 10:40
 * 修改备注：
 */
public class DeviceBindingAdapter extends BaseSimpleAdapter<KjChuZuWuInfo.ContentBean.RoomListBean> {
    private OnBindingStationListener onBindingStationListener;

    public DeviceBindingAdapter(Context context, List<KjChuZuWuInfo.ContentBean.RoomListBean> list) {
        super(context, list);
    }

    @Override
    public View simpleGetView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View
                    .inflate(context, R.layout.item_binding, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvroom.setText(list.get(position).getROOMNO() + "");
        final boolean isStation = !TextUtils.isEmpty(list.get(position).getSTATIONNO());
        viewHolder.station.setBackgroundResource(isStation ? R.drawable.shape_lgray_bgray_r4 : R.drawable.shape_lgreen_bgreen_r4);
        viewHolder.station.setEnabled(isStation ? false : true);
        viewHolder.station.setBackgroundResource(isStation ? R.drawable.binding_off : R.drawable.bing_on);
        viewHolder.station.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBindingStationListener != null) {
                    onBindingStationListener.onBindingStation(list.get(position).getROOMID(), list.get(position).getROOMNO());
                }
            }
        });
        viewHolder.device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isStation) {
                    ToastUtil.showMyToast("请先绑定基站");
                    return;
                }
                if (onBindingStationListener != null) {
                    onBindingStationListener.onBindingDevice(list.get(position).getROOMID(), list.get(position).getROOMNO());
                }
            }
        });


        return convertView;
    }

    public class ViewHolder {
        public final TextView tvroom;
        public final ImageView station;
        public final ImageView device;
        public final View root;

        public ViewHolder(View root) {
            tvroom = (TextView) root.findViewById(R.id.tv_room);
            station = (ImageView) root.findViewById(R.id.station);
            device = (ImageView) root.findViewById(R.id.device);
            this.root = root;
        }
    }

    public interface OnBindingStationListener {
        void onBindingStation(String roomId, int roomNo);

        void onBindingDevice(String roomId, int roomNo);
    }

    public void setOnBindingStationListener(OnBindingStationListener onBindingStationListener) {

        this.onBindingStationListener = onBindingStationListener;
    }
}
