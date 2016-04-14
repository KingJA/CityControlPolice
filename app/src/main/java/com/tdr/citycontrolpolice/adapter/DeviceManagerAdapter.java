package com.tdr.citycontrolpolice.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.entity.KjChuZuWuInfo;
import com.tdr.citycontrolpolice.entity.ZhuFang_DeviceLists;

import java.util.List;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/3/24 14:07
 * 修改备注：
 */
public class DeviceManagerAdapter extends BaseSimpleAdapter<KjChuZuWuInfo.ContentBean.RoomListBean> {

    private static final String TAG = "DeviceManagerAdapter";
    private OnExplandListener onExplandListener;
    private int selectPosition;
    private List<ZhuFang_DeviceLists.ContentEntity> deviceList;
    private boolean hasDevice;

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
        viewHolder.lvdevice.setVisibility(View.GONE);
        viewHolder.ivdevicearrow.setBackgroundResource(R.drawable.bg_arrow_down);
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.rlroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalViewHolder.lvdevice.getVisibility() == View.GONE) {
                    finalViewHolder.lvdevice.setVisibility(View.VISIBLE);
                    if (onExplandListener != null) {
                        onExplandListener.onExpland(finalViewHolder.lvdevice, finalViewHolder.ivdevicearrow, list.get(position).getROOMID(), list.get(position).getROOMNO());
                    }

                } else {
                    finalViewHolder.lvdevice.setVisibility(View.GONE);
                    finalViewHolder.ivdevicearrow.setBackgroundResource(R.drawable.bg_arrow_down);
                }
            }
        });
        return convertView;
    }

    public void selectItem(int selectPosition, List<ZhuFang_DeviceLists.ContentEntity> deviceList) {
        this.selectPosition = selectPosition;
        this.deviceList = deviceList;
        this.notifyDataSetChanged();
    }


    public interface OnExplandListener {
        void onExpland(ListView lv, ImageView iv, String roomid, int roomno);
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
