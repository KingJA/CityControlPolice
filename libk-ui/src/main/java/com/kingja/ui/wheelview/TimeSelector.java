package com.kingja.ui.wheelview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.kingja.ui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/6/23 13:46
 * 修改备注：
 */
public class TimeSelector extends Dialog implements View.OnClickListener {

    private Context context;

    private WheelView wv_hour;
    private WheelView wv_second;


    private List<String> hourList = new ArrayList<>();
    private List<String> secondList = new ArrayList<>();


    private TimeAdapter mHourAdapter;
    private TimeAdapter mSecondAdapter;


    private int maxTextSize = 24;
    private int minTextSize = 14;

    private String selectHour = "09";
    private String selectSecond = "30";


    private OnTimeSelectListener onTimeSelectListener;

    public TimeSelector(Context context) {
        super(context, R.style.KjAlertDialog);
        this.context = context;
    }
    public TimeSelector(Context context,String time) {
        super(context, R.style.KjAlertDialog);
        this.context = context;
        if (!TextUtils.isEmpty(time)) {
            String[] split = time.split(":");
            this.selectHour = split[0];
            this.selectSecond = split[1];
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selector_time);
        wv_hour = (WheelView) findViewById(R.id.wv_hour);
        wv_second = (WheelView) findViewById(R.id.wv_second);

        RelativeLayout rl_confirm = (RelativeLayout) findViewById(R.id.rl_confirm);
        RelativeLayout rl_cancel = (RelativeLayout) findViewById(R.id.rl_cancel);

        rl_confirm.setOnClickListener(this);
        rl_cancel.setOnClickListener(this);


        initHours();
        initSeconds();


        mHourAdapter = new TimeAdapter(context, hourList, Integer.valueOf(selectHour), maxTextSize, minTextSize);
        wv_hour.setVisibleItems(5);
        wv_hour.setViewAdapter(mHourAdapter);
        wv_hour.setCurrentItem(Integer.valueOf(selectHour));

        mSecondAdapter = new TimeAdapter(context, secondList, Integer.valueOf(selectSecond), maxTextSize, minTextSize);
        wv_second.setVisibleItems(5);
        wv_second.setViewAdapter(mSecondAdapter);
        wv_second.setCurrentItem(Integer.valueOf(selectSecond));


        wv_hour.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                selectHour = (String) mHourAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(selectHour, mHourAdapter);
            }
        });

        wv_hour.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                selectHour = (String) mHourAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(selectHour, mHourAdapter);
            }
        });

        wv_second.addChangingListener(new OnWheelChangedListener() {


            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                selectSecond = (String) mSecondAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(selectSecond, mSecondAdapter);
            }
        });

        wv_second.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                selectSecond = (String) mSecondAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(selectSecond, mSecondAdapter);
            }
        });


    }


    private class TimeAdapter extends AbstractWheelTextAdapter {
        List<String> list;

        protected TimeAdapter(Context context, List<String> list, int currentItem, int maxsize, int minsize) {
            super(context, R.layout.item_birth_year, NO_RESOURCE, currentItem, maxsize, minsize);
            this.list = list;
            setItemTextResource(R.id.tempValue);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }

        @Override
        public int getItemsCount() {
            return list.size();
        }

        @Override
        protected CharSequence getItemText(int index) {
            return list.get(index) + "";
        }
    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.rl_confirm) {
            if (onTimeSelectListener != null) {
                onTimeSelectListener.onTimeSelect(selectHour,selectSecond);
                dismiss();
                return;
            }
        }
        if (v.getId() == R.id.rl_cancel) {
            dismiss();
            return;
        }

    }
    public void setOnTimeSelectListener(OnTimeSelectListener onTimeSelectListener) {
        this.onTimeSelectListener = onTimeSelectListener;
    }
    public interface OnTimeSelectListener {
        void onTimeSelect(String hour, String second);
    }

    /**
     * 设置字体大小
     *
     * @param curriteItemText
     * @param adapter
     */
    public void setTextviewSize(String curriteItemText, TimeAdapter adapter) {
        ArrayList<View> arrayList = adapter.getTestViews();
        int size = arrayList.size();
        String currentText;
        for (int i = 0; i < size; i++) {
            TextView textvew = (TextView) arrayList.get(i);
            currentText = textvew.getText().toString();
            if (curriteItemText.equals(currentText)) {
                textvew.setTextSize(maxTextSize);
            } else {
                textvew.setTextSize(minTextSize);
            }
        }
    }

    public void initHours() {
        for (int i = 0; i < 24; i++) {
            hourList.add(String.format("%02d", i));
        }
    }

    private void initSeconds() {
        for (int i = 0; i < 60; i++) {
            secondList.add(String.format("%02d", i));
        }
    }
}
