package com.tdr.citycontrolpolice.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/5/9 8:58
 * 修改备注：
 */
public class HomeAdapter extends BaseAdapter {
    private Context context;
    private String[] titles;
    private int[] imgs;

    public HomeAdapter(Context context, String[] titles, int[] imgs) {
        this.context = context;
        this.titles = titles;
        this.imgs = imgs;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View
                    .inflate(context, R.layout.item_gd_home, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvhometitle.setText(titles[position]);
        viewHolder.ivhomeicon.setBackgroundResource(imgs[position]);
        return convertView;
    }

    public class ViewHolder {
        public final ImageView ivhomeicon;
        public final TextView tvhometitle;
        public final RelativeLayout layouthomelist;
        public final View root;

        public ViewHolder(View root) {
            ivhomeicon = (ImageView) root.findViewById(R.id.iv_home_icon);
            tvhometitle = (TextView) root.findViewById(R.id.tv_home_title);
            layouthomelist = (RelativeLayout) root.findViewById(R.id.layout_home_list);
            this.root = root;
        }
    }
}
