package com.tdr.citycontrolpolice.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.entity.Basic_StandardAddressCodeByKey_Kj;

import java.util.List;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/4/13 13:45
 * 修改备注：
 */
public class CzfInitAdapter extends BaseSimpleAdapter<Basic_StandardAddressCodeByKey_Kj.ContentBean> {

    public CzfInitAdapter(Context context, List<Basic_StandardAddressCodeByKey_Kj.ContentBean> list) {
        super(context, list);
    }

    @Override
    public View simpleGetView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View
                    .inflate(context, R.layout.item_czf_init, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (selectPosition == position) {
            viewHolder.ivaddress.setBackgroundResource(R.drawable.cb_address_sel);
        } else {
            viewHolder.ivaddress.setBackgroundResource(R.drawable.cb_address_nor);
        }
        viewHolder.tvsingle.setText(list.get(position).getAddress().substring(6));
//        viewHolder.tvsingle.setTextColor(context.getResources().getColor(selectPosition==position?R.color.blue_light_kj:R.color.gray_content));
        return convertView;
    }

    public void reset() {
        this.list.clear();
        notifyDataSetChanged();
    }


    public class ViewHolder {
        public final TextView tvsingle;
        public final ImageView ivaddress;
        public final View root;

        public ViewHolder(View root) {
            tvsingle = (TextView) root.findViewById(R.id.tv_single);
            ivaddress = (ImageView) root.findViewById(R.id.iv_address);
            this.root = root;
        }
    }
}
