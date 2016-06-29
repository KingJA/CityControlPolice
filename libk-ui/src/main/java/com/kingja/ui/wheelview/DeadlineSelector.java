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


/**
 * 项目名称：
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/6/23 09:46
 * 修改备注：
 */
public class DeadlineSelector extends Dialog implements View.OnClickListener {

    private Context context;
    private boolean dayMove;
    private boolean mouthMove;
    private boolean yearMove;
    private WheelView wvYear;
    private WheelView wvMonth;
    private WheelView wvDay;


    private ArrayList<String> arry_years = new ArrayList<>();
    private ArrayList<String> arry_months = new ArrayList<>();
    private ArrayList<String> arry_days = new ArrayList<>();
    private CalendarTextAdapter mYearAdapter;
    private CalendarTextAdapter mMonthAdapter;
    private CalendarTextAdapter mDaydapter;

    private int month;
    private int day;

    private int currentYear = getYear();
    private int currentMonth = 1;
    private int currentDay = 1;

    private int maxTextSize = 24;
    private int minTextSize = 14;

    private boolean issetdata = false;

    private String selectYear;
    private String selectMonth="0";
    private String selectDay;
    private boolean all;

    private OnDateSelectListener onDateSelectListener;

    public DeadlineSelector(Context context) {
        super(context, R.style.KjAlertDialog);
        this.context = context;
    }

    public DeadlineSelector(Context context,String date) {
        super(context, R.style.KjAlertDialog);
        this.context = context;
        if (!TextUtils.isEmpty(date)) {
            String[] split = date.split("-");
            this.selectYear = split[0];
            this.selectMonth = split[1];
            this.selectDay = split[2];
        }
        Log.e("DeadlineSelector", "selectYear: "+selectYear+"selectMonth: "+selectMonth+"selectDay: "+selectDay );

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


        initData();
        initYears();
        mYearAdapter = new CalendarTextAdapter(context, arry_years, setYear(currentYear), maxTextSize, minTextSize);
        wvYear.setVisibleItems(5);
        wvYear.setViewAdapter(mYearAdapter);
        wvYear.setCurrentItem(setYear(currentYear));
        initMonths(month);
        Log.e("Adapter", "selectYear: "+selectYear+"selectMonth: "+selectMonth+"selectDay: "+selectDay );
        mMonthAdapter = new CalendarTextAdapter(context, arry_months, 0, maxTextSize, minTextSize);
        wvMonth.setVisibleItems(5);
        wvMonth.setViewAdapter(mMonthAdapter);
        wvMonth.setCurrentItem(0);
        initDays(day);
        mDaydapter = new CalendarTextAdapter(context, arry_days, currentDay - 1, maxTextSize, minTextSize);
        wvDay.setVisibleItems(5);
        wvDay.setViewAdapter(mDaydapter);
        Log.i("currentDay", currentDay + "");
        wvDay.setCurrentItem(currentDay - 1);

        wvYear.addChangingListener(new OnWheelChangedListener() {

            private String currentYearText;

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                currentYearText = (String) mYearAdapter.getItemText(wheel.getCurrentItem());
                selectYear = currentYearText;
                setTextviewSize(currentYearText, mYearAdapter);
                currentYear = Integer.parseInt(currentYearText);
                setYear(currentYear);
                initMonths(month);
                mMonthAdapter = new CalendarTextAdapter(context, arry_months, 0, maxTextSize, minTextSize);
                wvMonth.setVisibleItems(5);
                wvMonth.setViewAdapter(mMonthAdapter);
                wvMonth.setCurrentItem(0);
            }
        });

        wvYear.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = (String) mYearAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentText, mYearAdapter);
                yearMove = true;
                if (yearMove && !mouthMove) {
                    selectMonth = 1 + "";
                }
            }
        });

        wvMonth.addChangingListener(new OnWheelChangedListener() {

            private String currentMonthText;

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                mouthMove = true;
                currentMonthText = (String) mMonthAdapter.getItemText(wheel.getCurrentItem());
                selectMonth = currentMonthText;
                setTextviewSize(currentMonthText, mMonthAdapter);
                setMonth(Integer.parseInt(currentMonthText));
                initDays(day);
                mDaydapter = new CalendarTextAdapter(context, arry_days, Integer.valueOf(selectDay), maxTextSize, minTextSize);
                wvDay.setVisibleItems(5);
                wvDay.setViewAdapter(mDaydapter);
                wvDay.setCurrentItem(Integer.valueOf(selectDay));
            }
        });

        wvMonth.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
                String currentDayText = (String) mMonthAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentDayText, mMonthAdapter);
                mouthMove = true;
                if (mouthMove && !dayMove&&all) {
                    Log.e("selectDay", 1+"");
                    selectDay = 1 + "";
                }
            }
        });

        wvDay.addChangingListener(new OnWheelChangedListener() {
            private String currentDayText;


            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                dayMove = true;
                currentDayText = (String) mDaydapter.getItemText(wheel.getCurrentItem());
                Log.i("addChangingListener", "currentText:" + currentDayText);
                setTextviewSize(currentDayText, mDaydapter);
                selectDay = currentDayText;

            }
        });

        wvDay.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = (String) mDaydapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentText, mDaydapter);
            }
        });

    }

    public void initYears() {
        for (int i = getYear(); i <= getYear(); i++) {
            arry_years.add(i + "");
        }
    }

    public void initMonths(int months) {
        arry_months.clear();
        for (int i = months; i <= 12; i++) {
            arry_months.add(i + "");
        }
    }

    public void initDays(int days) {
        arry_days.clear();
        if (days == getDay()) {
            all=false;
            for (int i = getDay(); i <= getDays(getYear(), getMonth()); i++) {
                arry_days.add(i + "");
            }
            selectDay=getDay()+"";
            Log.e("当前日期", currentDay+"");
        } else {
            all=true;
            for (int i = 1; i <= day; i++) {
                arry_days.add(i + "");
            }
        }
    }

    private class CalendarTextAdapter extends AbstractWheelTextAdapter {
        ArrayList<String> list;

        protected CalendarTextAdapter(Context context, ArrayList<String> list, int currentItem, int maxsize, int minsize) {
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

    public void initData() {
        this.currentDay = 1;
        this.currentMonth = 1;
        setDate(getYear(), getMonth(), getDay());
    }

    /**
     * 设置年月日
     *
     * @param year
     * @param month
     * @param day
     */
    public void setDate(int year, int month, int day) {
        selectYear = year + "";
        selectMonth = month + "";
        selectDay = day + "";
        issetdata = true;
        this.currentYear = year;
        this.currentMonth = month;
        this.currentDay = day;
        Log.i("setDate", "currentDay:" + currentDay);
        if (year == getYear()) {
            this.month = getMonth();
        } else {
            this.month = 12;
        }
        calDays(year, month);
    }

    /**
     * 设置年份
     *
     * @param year
     */
    public int setYear(int year) {
        int yearIndex = 0;
        if (year != getYear()) {
            this.month = 12;
        } else {
            this.month = getMonth();
        }
        for (int i = getYear(); i <= getYear() + 1; i++) {
            if (i == year) {
                return yearIndex;
            }
            yearIndex++;
        }
        return yearIndex;
    }

    /**
     * 设置月份
     *
     * @param month
     * @return
     */
    public int setMonth(int month) {
        int monthIndex = 0;
        calDays(currentYear, month);
        for (int i = 1; i < this.month; i++) {
            if (month == i) {
                return monthIndex;
            } else {
                monthIndex++;
            }
        }
        return monthIndex;
    }

    /**
     * 计算每月多少天
     *
     * @param month
     * @param year
     */
    public void calDays(int year, int month) {
        boolean leayyear = false;
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
        boolean leayyear = false;
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