package com.tdr.citycontrolpolice.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.entity.CHUZUWU_ROOMLKSELFREPORTINGLIST;
import com.tdr.citycontrolpolice.util.ToastUtil;
import com.tdr.citycontrolpolice.view.FixedListView;

import java.util.List;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：设备列表适配器
 * 创建人：KingJA
 * 创建时间：2016/3/24 16:07
 * 修改备注：
 */
public class DeviceCertAdapter extends BaseSimpleAdapter<CHUZUWU_ROOMLKSELFREPORTINGLIST.ContentBean> {


    private DeviceCertPersonAdapter deviceCertPersonAdapter;

    public DeviceCertAdapter(Context context, List<CHUZUWU_ROOMLKSELFREPORTINGLIST.ContentBean> list) {
        super(context, list);
    }

    @Override
    public View simpleGetView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View
                    .inflate(context, R.layout.item_device_cert, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (list.get(position).isEXPLAND()) {
            viewHolder.flvperson.setVisibility(View.VISIBLE);
            viewHolder.iv_arrow.setBackgroundResource(R.drawable.bg_arrow_up);
        } else {
            viewHolder.flvperson.setVisibility(View.GONE);
            viewHolder.iv_arrow.setBackgroundResource(R.drawable.bg_arrow_down);
        }
        viewHolder.tvroomnumber.setText(list.get(position).getROOMNO());
        List<CHUZUWU_ROOMLKSELFREPORTINGLIST.ContentBean.PERSONNELINFOLISTBean> personnelinfolist = list.get(position).getPERSONNELINFOLIST();

        deviceCertPersonAdapter = new DeviceCertPersonAdapter(context, personnelinfolist);
        viewHolder.flvperson.setAdapter(deviceCertPersonAdapter);

        viewHolder.rl_expland.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).getPERSONNELINFOLIST().size() == 0) {
                    ToastUtil.showMyToast("该房间无申报人员");
                    return;
                }
                list.get(position).setEXPLAND(!list.get(position).isEXPLAND());
                notifyDataSetChanged();
            }
        });
        return convertView;
    }



    public class ViewHolder {
        public final TextView tvroomnumber;
        public final ImageView iv_arrow;
        public final FixedListView flvperson;
        public final RelativeLayout rl_expland;
        public final View root;

        public ViewHolder(View root) {
            iv_arrow = (ImageView) root.findViewById(R.id.iv_arrow);
            tvroomnumber = (TextView) root.findViewById(R.id.tv_room_number);
            flvperson = (FixedListView) root.findViewById(R.id.flv_person);
               rl_expland = (RelativeLayout) root.findViewById(R.id.rl_expland);
            this.root = root;
        }
    }
}
