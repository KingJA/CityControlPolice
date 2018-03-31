package com.kingja.ui.popupwindow;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kingja.ui.R;

import java.util.List;

/**
 * Description：TODO
 * Create Time：2016/8/18 10:32
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public abstract class BottomSelectAdapter<T> extends BaseLvAdapter<T> {

    public BottomSelectAdapter(Context context, List<T> list) {
        super(context, list);
    }

    @Override
    public View simpleGetView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View
                    .inflate(context, R.layout.item_tv_center, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
       fillData(list,position,viewHolder.tvroomnumber);
        return convertView;
    }

    protected abstract void fillData(List<T> list, int position, TextView tvroomnumber);


    public class ViewHolder {
        public final TextView tvroomnumber;
        public final View root;

        public ViewHolder(View root) {
            tvroomnumber = (TextView) root.findViewById(R.id.tv_room_number);
            this.root = root;
        }
    }
}
