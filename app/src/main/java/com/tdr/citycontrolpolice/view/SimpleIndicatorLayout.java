package com.tdr.citycontrolpolice.view;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：滚动标签，横线下滑块，可与ViewPager绑定，支持标题颜色，和下滑块颜色自定义。
 * 创建人：KingJA
 * 创建时间：2016/3/23 12:30
 * 修改备注：
 */
public class SimpleIndicatorLayout extends LinearLayout {
    private static final float RATIO = 1.0f / 15.0f;
    private static final int COUNT_DEFAULT_VISIBLE = 4;
    private static final String TAG = "SimpleIndicatorLayout";
    private Paint mPaint;
    private Path mPath;
    private int mRecWidth;
    private int mRecHeight;
    private int mTranslation;
    private int mInitTranslation;
    private int mTabWidth;
    private int mVisibleCount;
    private int mTextColor;
    private int mTextSelColor;
    private int mTabBgColor;
    private int screenWidth;
    private List<String> mTitleList = new ArrayList<>();
    private List<TextView> mTextViewList = new ArrayList<>();
    private ViewPager mViewPager;
    private int lastX;

    public SimpleIndicatorLayout(Context context) {
        this(context, null);
    }

    public SimpleIndicatorLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.IndicatorLayout);
        mVisibleCount = typedArray.getInt(R.styleable.IndicatorLayout_kjVisibleCount, COUNT_DEFAULT_VISIBLE);
        mTextColor = typedArray.getColor(R.styleable.IndicatorLayout_kjTabTextColor, 0xffff0000);
        mTextSelColor = typedArray.getColor(R.styleable.IndicatorLayout_kjTabTextSelColor, 0xffff0000);
        mTabBgColor = typedArray.getColor(R.styleable.IndicatorLayout_kjTabBgColor, 0xff0000ff);
        typedArray.recycle();
    }

    public SimpleIndicatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化画笔，颜色，抗锯齿，样式，圆角setPathEffect
        initPaint();


    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mTabBgColor);
        mPaint.setColor(Color.RED);
    }

    /**
     * 初始化矩形
     */
    private void initRec() {
        mPath = new Path();
        mPath.moveTo(0, 0);
        mPath.lineTo(mRecWidth, 0);
        mPath.lineTo(mRecWidth, -mRecHeight);
        mPath.lineTo(0, -mRecHeight);
        mPath.close();


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTabWidth = getWidth() / mVisibleCount;
        mRecWidth = (int) (w / mVisibleCount * 1.0f);
        mRecHeight = (int) (h * RATIO);
        initRec();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        mPaint.setColor(mTabBgColor);
        canvas.save();
        canvas.translate(mInitTranslation + mTranslation, getHeight());
        canvas.drawPath(mPath, mPaint);
//        canvas.drawRect(0,-mRecHeight,mRecWidth,0,mPaint);
        canvas.restore();
        super.dispatchDraw(canvas);
    }

    /**
     * 关联ViewPager的滑动
     *
     * @param position
     * @param offset
     */
    public void scroll(int position, float offset) {
        mTranslation = (int) (mTabWidth * (offset + position));
        if (position >= (mVisibleCount - 2) && offset > 0 && getChildCount() > mVisibleCount) {
            if (mVisibleCount != 1) {
                this.scrollTo((position - (mVisibleCount - 2)) * mTabWidth + (int) (mTabWidth * offset), 0);
            } else {
                this.scrollTo(position * mTabWidth + (int) (mTabWidth * offset), 0);
            }
        }
        invalidate();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = (int) getX();
                break;
            case MotionEvent.ACTION_MOVE:
                int currentX = (int) getX();
                int distance = lastX - currentX;
                this.scrollTo(distance, 0);
                Log.e(TAG, "distance: " + distance);
                invalidate();
                lastX = (int) getX();
                break;
            case MotionEvent.ACTION_UP:
                break;

        }
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            LayoutParams lp = (LayoutParams) childView.getLayoutParams();
            lp.weight = 0;
            lp.width = getScreenWidth() / mVisibleCount;
            childView.setLayoutParams(lp);
        }
    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */

    public int getScreenWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        return isScreenLandscape()?dm.heightPixels:dm.widthPixels;
    }

    /**
     * 动态设置标题
     *
     * @param list
     */
    public void setTitles(List<String> list) {
        TextView titleView;
        if (list != null && list.size() > 0) {
//            mVisibleCount = list.size();
            this.removeAllViews();
            this.mTitleList = list;
            for (String title : mTitleList) {
                titleView = getTitleView(title);
                mTextViewList.add(titleView);
                addView(titleView);
            }
        }
        setItemClickListener();
    }

    /**
     * 设置点击事件Tab关联ViewPager滑动
     */
    private void setItemClickListener() {
        for (int i = 0; i < mTextViewList.size(); i++) {
            final int position = i;
            mTextViewList.get(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(position);
                }
            });
        }
    }

    /**
     * 设置标题样式
     *
     * @param title
     * @return
     */
    private TextView getTitleView(String title) {
        TextView tv = new TextView(getContext());
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        tv.setText(title);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(mTextColor);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        lp.width = getScreenWidth() / mVisibleCount;
        tv.setLayoutParams(lp);
        return tv;
    }

    /**
     * 绑定ViewPager
     *
     * @param viewPager
     * @param position  第一个显示的页面
     */

    public void setUpWithViewPager(ViewPager viewPager, int position) {
        this.mViewPager = viewPager;
        setTextSelColor(position);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                SimpleIndicatorLayout.this.scroll(position, positionOffset);
                if (onIndictatorChangeListener != null) {
                    onIndictatorChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {

                setTextSelColor(position);
                if (onIndictatorChangeListener != null) {
                    onIndictatorChangeListener.onPageSelected(position);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (onIndictatorChangeListener != null) {
                    onIndictatorChangeListener.onPageScrollStateChanged(state);
                }
            }
        });
    }

    /**
     * 设置选中标题颜色
     *
     * @param position
     */
    private void setTextSelColor(int position) {
        for (int i = 0; i < mTextViewList.size(); i++) {
            if (i == position) {
                mTextViewList.get(i).setTextColor(mTextSelColor);
            } else {
                mTextViewList.get(i).setTextColor(mTextColor);
            }
        }
    }

    public void setOnPageChangeListener(OnIndictatorChangeListener onIndictatorChangeListener) {
        this.onIndictatorChangeListener = onIndictatorChangeListener;
    }

    interface OnIndictatorChangeListener {

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

        public void onPageSelected(int position);

        public void onPageScrollStateChanged(int state);
    }

    private OnIndictatorChangeListener onIndictatorChangeListener;

    private boolean isScreenLandscape() {
        Configuration mConfiguration = getResources().getConfiguration(); //获取设置的配置信息
        int ori = mConfiguration.orientation; //获取屏幕方向
        if (ori == mConfiguration.ORIENTATION_LANDSCAPE) {
            //横屏
            return true;
        } else if (ori == mConfiguration.ORIENTATION_PORTRAIT) {
            //竖屏
            return false;
        }
        return false;
    }
}

