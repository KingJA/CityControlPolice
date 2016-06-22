package com.tdr.citycontrolpolice.view;

/**
 * 项目名称：
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/6/22 14:44
 * 修改备注：
 */

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author KingJA
 * @data 2015-8-10 上午11:18:28
 * @use 自定义
 */
public class MyViewPager extends ViewPager {
    private List<View> dotList;
    private Context context;
    private List<View> viewList;
    private RelativeLayout relativeLayout;

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

    }

    public void setContent(List<View> viewList, RelativeLayout relativeLayout) {
        this.viewList = viewList;
        this.relativeLayout = relativeLayout;
        initDot();
        this.setAdapter(new MyPagerAdapter());
        this.addOnPageChangeListener(onPageChangeListener);

    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int height = 0;
        //下面遍历所有child的高度
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec,
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > height) //采用最大的view的高度。
                height = h;
        }

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
                MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private OnPageChangeListener onPageChangeListener=new OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            int index = position % dotList.size();
            for (int i = 0; i < dotList.size(); i++) {
                if (i == index) {
                    dotList.get(index).setBackgroundResource(R.drawable.bg_dot_on);
                } else {
                    dotList.get(i).setBackgroundResource(R.drawable.bg_dot_off);
                }
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };
    /**
     * 初始化点
     */
    public void initDot() {
        dotList = new ArrayList<View>();
        LinearLayout llLayout = new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(AppUtil.dp2px(6),AppUtil.dp2px(6));
        layoutParams.setMargins(0,0, AppUtil.dp2px(6),AppUtil.dp2px(6));
        for (int i = 0; i < viewList.size(); i++) {
            View dotView = new View(context);
            if (i == 0) {
                dotView.setBackgroundResource(R.drawable.bg_dot_on);

            } else {
                dotView.setBackgroundResource(R.drawable.bg_dot_off);
            }
            dotList.add(dotView);
            llLayout.addView(dotView, layoutParams);
        }
        RelativeLayout.LayoutParams rlParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        rlParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        rlParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        relativeLayout.addView(llLayout, rlParams);

    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = viewList.get(position);
            container.addView(view);
            return view;
        }
    }


}