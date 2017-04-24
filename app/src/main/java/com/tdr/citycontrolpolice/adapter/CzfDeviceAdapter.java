package com.tdr.citycontrolpolice.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.activity.DeviceCertActivity;
import com.tdr.citycontrolpolice.entity.ChuZuWu_LkSelfReportingMacList;

import java.util.List;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：设备列表适配器
 * 创建人：KingJA
 * 创建时间：2016/3/24 16:07
 * 修改备注：
 */
public class CzfDeviceAdapter extends BaseSimpleAdapter<ChuZuWu_LkSelfReportingMacList.ContentBean> {



    public CzfDeviceAdapter(Context context, List<ChuZuWu_LkSelfReportingMacList.ContentBean> list) {
        super(context, list);
    }

    @Override
    public View simpleGetView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View
                    .inflate(context, R.layout.item_czf_device, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvcert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeviceCertActivity.goActivity(context,list.get(position).getHOUSEID(),list.get(position));
            }
        });
        viewHolder.tvoutinname.setText(list.get(position).getCHINESENAME());
        viewHolder.tvinfophone.setText(list.get(position).getPHONE());
        viewHolder.tvmac.setText(list.get(position).getMAC());
        return convertView;
    }


    public class ViewHolder {
        public final TextView tvoutinname;
        public final TextView tvcert;
        public final TextView tvinfophone;
        public final TextView tvmac;
        public final View root;

        public ViewHolder(View root) {
            tvoutinname = (TextView) root.findViewById(R.id.tv_outin_name);
            tvcert = (TextView) root.findViewById(R.id.tv_bind);
            tvinfophone = (TextView) root.findViewById(R.id.tv_info_phone);
            tvmac = (TextView) root.findViewById(R.id.tv_mac);
            this.root = root;
        }
    }
}
