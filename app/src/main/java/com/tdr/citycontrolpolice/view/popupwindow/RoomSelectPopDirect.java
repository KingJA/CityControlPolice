package com.tdr.citycontrolpolice.view.popupwindow;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.adapter.RoomSelectAdapter;
import com.tdr.citycontrolpolice.adapter.RoomSelectDirectAdapter;
import com.tdr.citycontrolpolice.entity.KjChuZuWuInfo;
import com.tdr.citycontrolpolice.util.AppUtil;

import java.util.List;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/4/11 16:34
 * 修改备注：
 */
public class RoomSelectPopDirect extends PopupWindowBaseDown<List<KjChuZuWuInfo.ContentBean.RoomListBean>> {

    private RoomSelectDirectAdapter roomSelectAdapter;
    private OnRoomSelectListener onRoomSelectListener;

    public RoomSelectPopDirect(View parentView, Activity activity, List<KjChuZuWuInfo.ContentBean.RoomListBean> data) {
        super(parentView, activity, data);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(AppUtil.getScreenHeight()/2);
        this.setAnimationStyle(R.style.PopupTopAnimation);

    }


    @Override
    public View setPopupView(Activity activity) {
        popupView = View.inflate(activity, R.layout.pop_bottom_list, null);
        return popupView;
    }

    @Override
    public void initChildView() {
        ListView lv_pop_bottom = (ListView) popupView.findViewById(R.id.lv_pop_bottom);
        roomSelectAdapter = new RoomSelectDirectAdapter(activity, data);
        lv_pop_bottom.setAdapter(roomSelectAdapter);
        lv_pop_bottom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    KjChuZuWuInfo.ContentBean.RoomListBean bean = (KjChuZuWuInfo.ContentBean.RoomListBean) parent.getItemAtPosition(position);
                    if (onRoomSelectListener != null) {
                        onRoomSelectListener.onSelect(position, bean);
                        dismiss();
                    }
            }
        });

    }

    @Override
    public void OnChildClick(View v) {

    }
    public interface OnRoomSelectListener {
        void onSelect(int position, KjChuZuWuInfo.ContentBean.RoomListBean bean);
    }

    public void setOnRoomSelectListener(OnRoomSelectListener onRoomSelectListener) {
        this.onRoomSelectListener = onRoomSelectListener;
    }

}
