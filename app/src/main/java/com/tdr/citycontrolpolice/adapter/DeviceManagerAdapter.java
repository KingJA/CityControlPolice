package com.tdr.citycontrolpolice.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.entity.KjChuZuWuInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/3/24 14:07
 * 修改备注：
 */
public class DeviceManagerAdapter extends BaseSimpleAdapter<KjChuZuWuInfo.ContentBean.RoomListBean> {

    private OnExplandListener onExplandListener;
    private Map<Integer, DeviceListAdapter> adapterMap = new HashMap<>();

    public DeviceManagerAdapter(Context context, List<KjChuZuWuInfo.ContentBean.RoomListBean> list) {
        super(context, list);
    }

    @Override
    public View simpleGetView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View
                    .inflate(context, R.layout.item_device_manager, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvdeviceroom.setText(list.get(position).getROOMNO() + "");

        final boolean expland = list.get(position).isExpland();
        if (expland) {
            viewHolder.ivdevicearrow.setBackgroundResource(R.drawable.bg_arrow_up);
            if (getAdapter(position) != null) {
                viewHolder.lvdevice.setAdapter(getAdapter(position));
            }
            viewHolder.lvdevice.setVisibility(View.VISIBLE);

        } else {
            viewHolder.ivdevicearrow.setBackgroundResource(R.drawable.bg_arrow_down);
            viewHolder.lvdevice.setVisibility(View.GONE);

        }
        final ViewHolder finalViewHolder = viewHolder;

        viewHolder.rlroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onExplandListener != null) {
                    if (expland) {//开=》关
                        finalViewHolder.ivdevicearrow.setBackgroundResource(R.drawable.bg_arrow_down);
                        finalViewHolder.lvdevice.setVisibility(View.GONE);
                        setVisibility(!expland, position);
                    } else {//关=》开
                        if (getAdapter(position) != null) {
                            setVisibility(!expland, position);
                            return;
                        }
                        onExplandListener.onExpland(list.get(position).getROOMID() + "", list.get(position).getROOMNO() + "", finalViewHolder.lvdevice, finalViewHolder.ivdevicearrow, position, expland);
                    }
                }
            }
        });

        return convertView;
    }

    public void saveAdapter(int position, DeviceListAdapter adapter) {
        this.adapterMap.put(position, adapter);
    }

    public DeviceListAdapter getAdapter(int position) {
        return this.adapterMap.get(position);
    }

    public void setVisibility(boolean expand, int position) {
        list.get(position).setExpland(expand);
        this.notifyDataSetChanged();
    }

    public interface OnExplandListener {
        void onExpland(String roomid, String roomno, ListView lv, ImageView iv, int position, boolean expland);
    }

    public void setOnExplandListener(OnExplandListener onExplandListener) {

        this.onExplandListener = onExplandListener;
    }

    public class ViewHolder {
        public final TextView tvdeviceroom;
        public final ImageView ivdevicearrow;
        public final RelativeLayout rlroom;
        public final ListView lvdevice;
        public final LinearLayout lldevice;
        public final View root;

        public ViewHolder(View root) {
            tvdeviceroom = (TextView) root.findViewById(R.id.tv_device_room);
            ivdevicearrow = (ImageView) root.findViewById(R.id.iv_device_arrow);
            rlroom = (RelativeLayout) root.findViewById(R.id.rl_room);
            lvdevice = (ListView) root.findViewById(R.id.lv_device);
            lldevice = (LinearLayout) root.findViewById(R.id.ll_device);
            this.root = root;
        }
    }
}
