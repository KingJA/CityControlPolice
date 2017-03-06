package com.tdr.citycontrolpolice.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.entity.CHUZUWU_ROOMLKSELFREPORTINGLIST;
import com.tdr.citycontrolpolice.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：MAC设备人员列表适配器
 * 创建人：KingJA
 * 创建时间：2016/3/24 16:07
 * 修改备注：
 */
public class DeviceCertPersonAdapter extends BaseSimpleAdapter<CHUZUWU_ROOMLKSELFREPORTINGLIST.ContentBean.PERSONNELINFOLISTBean> {


    public DeviceCertPersonAdapter(Context context, List<CHUZUWU_ROOMLKSELFREPORTINGLIST.ContentBean.PERSONNELINFOLISTBean> list) {
        super(context, list);
    }

    @Override
    public View simpleGetView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View
                    .inflate(context, R.layout.item_device_cert_person, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvname.setText(list.get(position).getNAME());
        viewHolder.tvphone.setText(list.get(position).getPHONENUM());
        viewHolder.tvcard.setText(list.get(position).getIDENTITYCARD());
        viewHolder.tvbind.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(list.get(position));
            }
        });

        return convertView;
    }


    public class ViewHolder {
        public final TextView tvname;
        public final TextView tvphone;
        public final TextView tvcard;
        public final TextView tvbind;
        public final View root;

        public ViewHolder(View root) {
            tvname = (TextView) root.findViewById(R.id.tv_name);
            tvphone = (TextView) root.findViewById(R.id.tv_phone);
            tvcard = (TextView) root.findViewById(R.id.tv_card);
            tvbind = (TextView) root.findViewById(R.id.tv_bind);
            this.root = root;
        }
    }
}
