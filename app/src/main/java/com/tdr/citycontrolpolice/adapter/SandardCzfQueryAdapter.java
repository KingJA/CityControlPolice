package com.tdr.citycontrolpolice.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
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
public class SandardCzfQueryAdapter extends BaseSimpleAdapter<Basic_StandardAddressCodeByKey_Kj.ContentBean> {

    public SandardCzfQueryAdapter(Context context, List<Basic_StandardAddressCodeByKey_Kj.ContentBean> list) {
        super(context, list);
    }

    @Override
    public View simpleGetView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View
                    .inflate(context, R.layout.item_single_address, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvaddress.setText(list.get(position).getAddress());
        return convertView;
    }



    public class ViewHolder {
        public final TextView tvaddress;
        public final View root;

        public ViewHolder(View root) {
            tvaddress = (TextView) root.findViewById(R.id.tv_address);
            this.root = root;
        }
    }
}
