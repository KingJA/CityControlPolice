package com.kingja.ui.popupwindow;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kingja.ui.R;

import java.util.List;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/4/11 16:34
 * 修改备注：
 */
public class ListPop extends BasePopupWindow<List<String>> {

    private SingleTextAdapter singleTextAdapter;
    private OnPopItemClickListener onPopItemClickListener;

    public ListPop(View parentView, Activity activity, List<String> data) {
        super(parentView, activity, data);
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
                String bean = (String) parent.getItemAtPosition(position);
                if (onPopItemClickListener != null) {
                    onPopItemClickListener.onPopItemClick(position, bean);
                    dismiss();
                }
            }
        });

    }

    @Override
    public void OnChildClick(View v) {

    }

    public interface OnPopItemClickListener {
        void onPopItemClick(int position, String tag);
    }

    public void setOnPopItemClickListener(OnPopItemClickListener onPopItemClickListener) {

        this.onPopItemClickListener = onPopItemClickListener;
    }

}
