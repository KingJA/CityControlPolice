package com.kingja.ui.popupwindow;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.kingja.ui.R;

import java.util.List;

/**
 * Created by Shinelon on 2016/3/24.
 */
public abstract class BaseTopPop<T> extends PopupWindowBase<T> implements AdapterView.OnItemClickListener {


    public BaseTopPop(Activity activity, List<T> data) {
        super(activity,data);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setAnimationStyle(R.style.PopupTop2BottomAnimation);
    }



    @Override
    public View setPopupView(Activity activity) {
        return View.inflate(activity, R.layout.pop, null);
    }

    @Override
    public void initChildView(List<T> data) {
        ListView lv = (ListView) popupView.findViewById(R.id.lv);
        BottomSelectAdapter<T> bottomSelectAdapter = new BottomSelectAdapter<T>(activity, data) {
            @Override
            protected void fillData(List<T> list, int position, TextView tv) {
                fillLvData( list,  position,  tv);
            }
        };
        lv.setAdapter(bottomSelectAdapter);
        lv.setOnItemClickListener(this);

    }

    protected abstract void fillLvData(List<T> list, int position, TextView tv);

    @Override
    public void OnChildClick(View v) {

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        T t = (T) parent.getItemAtPosition(position);
        onItemSelect(t);
       dismiss();
    }

    protected abstract void onItemSelect( T t);
}
