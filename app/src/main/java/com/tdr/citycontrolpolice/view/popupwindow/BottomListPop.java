package com.tdr.citycontrolpolice.view.popupwindow;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.adapter.SingleTextAdapter;
import com.tdr.citycontrolpolice.entity.Basic_Dictionary_Kj;

import java.util.List;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/4/11 16:34
 * 修改备注：
 */
public class BottomListPop extends PopupWindowBaseDown<List<Basic_Dictionary_Kj>> {

    private SingleTextAdapter singleTextAdapter;
    private OnBottemPopSelectListener onBottemPopSelectListener;

    public BottomListPop(View parentView, Activity activity, List<Basic_Dictionary_Kj> data) {
        super(parentView, activity, data);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setAnimationStyle(R.style.PopupBottomAnimation);

    }


    @Override
    public View setPopupView(Activity activity) {
        popupView = View.inflate(activity, R.layout.pop_bottom_list, null);
        return popupView;
    }

    @Override
    public void initChildView() {
        ListView lv_pop_bottom = (ListView) popupView.findViewById(R.id.lv_pop_bottom);
        singleTextAdapter = new SingleTextAdapter(activity, data);
        lv_pop_bottom.setAdapter(singleTextAdapter);
        lv_pop_bottom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Basic_Dictionary_Kj bean = (Basic_Dictionary_Kj) parent.getItemAtPosition(position);
                if (onBottemPopSelectListener != null) {
                    onBottemPopSelectListener.onSelect(position, bean);
                    dismiss();

                }
            }
        });

    }

    @Override
    public void OnChildClick(View v) {

    }

    public interface OnBottemPopSelectListener {
        void onSelect(int position, Basic_Dictionary_Kj bean);
    }

    public void setOnBottemPopSelectListener(OnBottemPopSelectListener onBottemPopSelectListener) {

        this.onBottemPopSelectListener = onBottemPopSelectListener;
    }

}
