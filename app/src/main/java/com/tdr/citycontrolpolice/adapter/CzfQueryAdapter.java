package com.tdr.citycontrolpolice.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
public class CzfQueryAdapter extends BaseSimpleAdapter<Basic_StandardAddressCodeByKey_Kj.ContentBean> {
    private int selectPosition = -1;

    public CzfQueryAdapter(Context context, List<Basic_StandardAddressCodeByKey_Kj.ContentBean> list) {
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
        viewHolder.llroot.setBackgroundResource(position == selectPosition ? R.drawable.shape_lblue_bnull_r0 : R.drawable.shape_lgray_bnull_r0);
        return convertView;
    }


    public void selectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        this.notifyDataSetChanged();
    }

    public class ViewHolder {
        public final TextView tvaddress;
        public final LinearLayout llroot;
        public final View root;

        public ViewHolder(View root) {
            tvaddress = (TextView) root.findViewById(R.id.tv_address);
            llroot = (LinearLayout) root.findViewById(R.id.ll_root);
            this.root = root;
        }
    }
}
