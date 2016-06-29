package com.kingja.ui.wheelview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kingja.ui.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * 项目名称：
 * 类描述：截止日期选择器
 * 创建人：KingJA
 * 创建时间：2016/6/23 09:46
 * 修改备注：
 */
public class DeadlineSelector extends Dialog implements View.OnClickListener {

    private static final String TAG = "DeadlineSelector";
    private Context context;

    private WheelView wvYear;
    private WheelView wvMonth;
    private WheelView wvDay;


    private List<String> yearList = new ArrayList<>();
    private List<String> monthList = new ArrayList<>();
    private List<String> dayList = new ArrayList<>();

    private CalendarTextAdapter mYearAdapter;
    private CalendarTextAdapter mMonthAdapter;
    private CalendarTextAdapter mDaydapter;

    private int month;
    private int day;

    private int currentYear = getYear();

    private int maxTextSize = 24;
    private int minTextSize = 14;


    private String selectYear;
    private String selectMonth;
    private String selectDay;

    private OnDateSelectListener onDateSelectListener;

    private boolean defaultDate=true;

    public DeadlineSelector(Context context) {
        super(context, R.style.KjAlertDialog);
        this.context = context;
    }

    public DeadlineSelector(Context context, String date) {
        super(context, R.style.KjAlertDialog);
        this.context = context;
        if (!TextUtils.isEmpty(date)) {
            defaultDate=false;
            String[] split = date.split("-");
            this.selectYear = split[0];
            this.selectMonth = split[1];
            this.selectDay = split[2];
        }
        Log.e("DeadlineSelector", "selectYear: " + selectYear + "selectMonth: " + selectMonth + "selectDay: " + selectDay);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selector_deadline);
        wvYear = (WheelView) findViewById(R.id.wv_birth_year);
        wvMonth = (WheelView) findViewById(R.id.wv_birth_month);
        wvDay = (WheelView) findViewById(R.id.wv_birth_day);


        RelativeLayout rl_confirm = (RelativeLayout) findViewById(R.id.rl_confirm);
        RelativeLayout rl_cancel = (RelativeLayout) findViewById(R.id.rl_cancel);
        rl_confirm.setOnClickListener(this);
        rl_cancel.setOnClickListener(this);


        initDate(defaultDate);
        initYearList();
        mYearAdapter = new CalendarTextAdapter(context, yearList, getYearIndex(currentYear), maxTextSize, minTextSize);
        wvYear.setVisibleItems(5);
        wvYear.setViewAdapter(mYearAdapter);
        wvYear.setCurrentItem(getYearIndex(currentYear));

        initMonthList(Integer.valueOf(selectMonth));
        mMonthAdapter = new CalendarTextAdapter(context, monthList, 0, maxTextSize, minTextSize);
        wvMonth.setVisibleItems(5);
        wvMonth.setViewAdapter(mMonthAdapter);
        wvMonth.setCurrentItem(0);
        initDayList(Integer.valueOf(selectYear),Integer.valueOf(selectMonth));
        mDaydapter = new CalendarTextAdapter(context, dayList, 0, maxTextSize, minTextSize);
        wvDay.setVisibleItems(5);
        wvDay.setViewAdapter(mDaydapter);
        wvDay.setCurrentItem(0);


        wvMonth.addChangingListener(new OnWheelChangedListener() {


            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                selectMonth = (String) mMonthAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(selectMonth, mMonthAdapter);
                initDayList(Integer.valueOf(selectYear),Integer.valueOf(selectMonth));

                mDaydapter = new CalendarTextAdapter(context, dayList, 0, maxTextSize, minTextSize);
                wvDay.setVisibleItems(5);
                wvDay.setViewAdapter(mDaydapter);
                wvDay.setCurrentItem(0);
                if (Integer.valueOf(selectMonth) != getMonth()) {
                    selectDay="1";
                }else{
                    selectDay=getDay()+"";
                }
            }
        });
        wvDay.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                selectDay = (String) mDaydapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(selectDay, mDaydapter);
            }
        });

    }

    public void initYearList() {
        for (int i = getYear(); i <= getYear(); i++) {
            yearList.add(i + "");
        }
    }

    public void initMonthList(int month) {
        monthList.clear();
        for (int i = month; i <= 12; i++) {
            monthList.add(i + "");
        }
    }

    public void initDayList(int year,int month) {
        dayList.clear();
        if (month == getMonth()) {
            for (int i = getDay(); i <= getDays(getYear(), getMonth()); i++) {
                dayList.add(i + "");
            }
        } else {
            for (int i = 1; i <= getDays(year, month); i++) {
                dayList.add(i + "");
            }
        }
    }

    private class CalendarTextAdapter extends AbstractWheelTextAdapter {
        List<String> list;

        protected CalendarTextAdapter(Context context, List<String> list, int currentItem, int maxsize, int minsize) {
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

    public void setOnDateSelectListener(OnDateSelectListener onDateSelectListener) {
        this.onDateSelectListener = onDateSelectListener;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.rl_confirm) {
            if (onDateSelectListener != null) {
                onDateSelectListener.onClick(selectYear, String.format("%02d", Integer.valueOf(selectMonth)), String.format("%02d", Integer.valueOf(selectDay)));
                dismiss();
                return;
            }
        }
        if (v.getId() == R.id.rl_cancel) {
            dismiss();
            return;
        }
    }

    public interface OnDateSelectListener {
        void onClick(String year, String month, String day);
    }

    /**
     * 设置字体大小
     *
     * @param curriteItemText
     * @param adapter
     */
    public void setTextviewSize(String curriteItemText, CalendarTextAdapter adapter) {
        List<View> arrayList = adapter.getTestViews();
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

    public int getYear() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.YEAR);
    }

    public int getMonth() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.MONTH) + 1;
    }

    public int getDay() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.DATE);
    }

    public void initDate(boolean defaultDate) {
        if (!defaultDate) {
            return;
        }
        selectYear = getYear() + "";
        selectMonth = getMonth() + "";
        selectDay = getDay() + "";
//        if (Integer.valueOf(selectYear) == getYear()) {
//            this.month = getMonth();
//        } else {
//            this.month = 12;
//        }
//        calDays(getYear() , getMonth());
    }


    /**
     * 设置年份
     *
     * @param year
     */
    public int getYearIndex(int year) {
        return yearList.indexOf(year + "");
    }

    /**
     * 计算每月多少天
     *
     * @param month
     * @param year
     */
    public void calDays(int year, int month) {
        boolean leayyear;
        if (year % 4 == 0 && year % 100 != 0) {
            leayyear = true;
        } else {
            leayyear = false;
        }
        for (int i = 1; i <= 12; i++) {
            switch (month) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    this.day = 31;
                    break;
                case 2:
                    if (leayyear) {
                        this.day = 29;
                    } else {
                        this.day = 28;
                    }
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    this.day = 30;
                    break;
            }
        }
        if (year == getYear() && month == getMonth()) {
            this.day = getDay();
        }
    }

    public int getDays(int year, int month) {
        boolean leayyear;
        int days = 0;
        if (year % 4 == 0 && year % 100 != 0) {
            leayyear = true;
        } else {
            leayyear = false;
        }
        for (int i = 1; i <= 12; i++) {
            switch (month) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    days = 31;
                    break;
                case 2:
                    if (leayyear) {
                        days = 29;
                        break;
                    } else {
                        days = 28;
                        break;
                    }
                case 4:
                case 6:
                case 9:
                case 11:
                    days = 30;
                    break;
            }
        }
        return days;

    }
}