package com.tdr.citycontrolpolice.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;
import android.widget.ScrollView;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/4/16 16:27
 * 修改备注：
 */
public class FixedScrollView extends ScrollView {
    public FixedScrollView(Context context) {
        super(context);
    }

    public FixedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FixedScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}