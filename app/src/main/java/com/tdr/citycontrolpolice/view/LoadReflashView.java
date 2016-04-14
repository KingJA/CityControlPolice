package com.tdr.citycontrolpolice.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;

import java.text.SimpleDateFormat;
import java.util.Date;


public class LoadReflashView extends ListView implements OnScrollListener {
    View header;//顶部布局文件
    int headerHeight;//顶部布局文件的高度
    int firstVisibleItem;//当前第一个可见Item的位置
    boolean isRemark;//标记当前是在ListView最顶端恩下的
    int scrollState;//listView当前滚动状态
    IReflashListener iReflashListener;//刷新数据的接口
    int startY;//恩下的Y值
    int state;//当前的状态
    final int NONE = 0;//正常状态
    final int PULL = 1;//提示下拉状态
    final int RELESE = 2;//提示释放状态
    final int REFLASHING = 3;//刷新状态

    View footer;//底部布局
    int totalItemCount;//总数量
    int lastVisibleItem;//最后一个可见的item
    boolean isLoading;//正在加载
    ILoadListener iLoadListener;

    public LoadReflashView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        initView(context);
    }

    public LoadReflashView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        initView(context);
    }

    public LoadReflashView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
        initView(context);
    }

    /*
    *
    * 初始化界面添加顶部、底部布局文件到ListView里面
    */
    private void initView(Context context) {
        //头布局
        LayoutInflater inflater = LayoutInflater.from(context);
        header = inflater.inflate(R.layout.header_layout, null);
        measureView(header);
        headerHeight = header.getMeasuredHeight();
        Log.i("tag", "headerHeight = " + headerHeight);
        topPadding(-headerHeight);

        this.addHeaderView(header);
        this.setOnScrollListener(this);
        //尾布局
        footer = inflater.inflate(R.layout.footer_layout, null);
        footer.findViewById(R.id.load_layout).setVisibility(View.GONE);
        this.addFooterView(footer);
        this.setOnScrollListener(this);
    }

    /*
    * 通知父布局占用的宽高
    */
    private void measureView(View view) {
        ViewGroup.LayoutParams p = view.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int width = ViewGroup.getChildMeasureSpec(0, 0, p.width);
        int height;
        int tempHelight = p.height;
        if (tempHelight > 0) {
            height = MeasureSpec.makeMeasureSpec(tempHelight, MeasureSpec.EXACTLY);
        } else {
            height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        view.measure(width, height);

    }

    /*
    * 设置Header的上边距
    */
    private void topPadding(int topPadding) {
        header.setPadding(header.getPaddingLeft(), topPadding, header.getPaddingRight(), header.getPaddingBottom());
        header.invalidate();
    }

    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        // TODO Auto-generated method stub
        //头布局
        this.firstVisibleItem = firstVisibleItem;
        //尾布局
        this.lastVisibleItem = firstVisibleItem + visibleItemCount;
        this.totalItemCount = totalItemCount;

    }


    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // TODO Auto-generated method stub
        //
        this.scrollState = scrollState;
        if (totalItemCount == lastVisibleItem &&
                scrollState == SCROLL_STATE_IDLE) {

            if (!isLoading) {
                isLoading = true;
                footer.findViewById(R.id.load_layout).setVisibility(View.VISIBLE);
                //加载更多
                iLoadListener.onLoad();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (firstVisibleItem == 0) {
                    isRemark = true;
                    startY = (int) ev.getY();
                }
            case MotionEvent.ACTION_MOVE:
                onMove(ev);
                break;

            case MotionEvent.ACTION_UP:
                if (state == RELESE) {
                    state = REFLASHING;
                    //加载最新数据
                    reflashViewByState();
                    iReflashListener.onReflash();
                } else if (state == PULL) {
                    state = NONE;
                    isRemark = false;
                    reflashViewByState();
                }
                break;

            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    /*
    * 判断移动过程中的操作
    */
    private void onMove(MotionEvent ev) {
        if (!isRemark) {
            return;
        }
        int tempY = (int) ev.getY();
        int space = tempY - startY;
        int topPadding = space - headerHeight;
        switch (state) {
            case NONE:
                if (space > 0)
                    state = PULL;
                reflashViewByState();
                break;

            case PULL:
                topPadding(topPadding);
                if (space > headerHeight + 30 && scrollState == SCROLL_STATE_TOUCH_SCROLL) {
                    state = RELESE;
                    reflashViewByState();
                }
                break;

            case RELESE:
                topPadding(topPadding);
                if (space < headerHeight + 30) {
                    state = PULL;
                    reflashViewByState();
                } else if (space <= 0) {
                    state = NONE;
                    isRemark = false;
                    reflashViewByState();
                }
                break;

            case REFLASHING:

                break;

            default:
                break;
        }
    }

    /*
    * 根据当前状态改变界面显示
    */
    private void reflashViewByState() {
        TextView tip = (TextView) header.findViewById(R.id.tip);
        ImageView arrow = (ImageView) header.findViewById(R.id.arrow);
        ProgressBar progress = (ProgressBar) header.findViewById(R.id.progressBar);
        RotateAnimation anim = new RotateAnimation(0, 180, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(500);
        anim.setFillAfter(true);
        RotateAnimation anim1 = new RotateAnimation(180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        anim1.setDuration(500);
        anim1.setFillAfter(true);
        switch (state) {
            case NONE:
                arrow.clearAnimation();
                topPadding(-headerHeight);
                break;

            case PULL:
                arrow.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                arrow.clearAnimation();
                arrow.setAnimation(anim1);

                tip.setText("下拉可以刷新");
                break;

            case RELESE:
                arrow.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                tip.setText("松开可以刷新");
                arrow.clearAnimation();
                arrow.setAnimation(anim);
                break;

            case REFLASHING:
                topPadding(50);
                arrow.setVisibility(View.GONE);
                progress.setVisibility(View.VISIBLE);
                tip.setText("正在刷新");
                arrow.clearAnimation();
                break;
            default:
                break;
        }
    }

    /*
    * 获取完数据
    */
    public void reflashComplete() {
        state = NONE;
        isRemark = false;
        reflashViewByState();
        TextView lastupdatetime = (TextView) header.findViewById(R.id.lastupdate_time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String time = format.format(date);
        lastupdatetime.setText(time);

    }

    public void setReflashInterface(IReflashListener iReflashListener) {
        this.iReflashListener = iReflashListener;
    }

    /*
    * 刷新数据接口
    */
    public interface IReflashListener {
        public void onReflash();
    }


    public void loadComplete() {
        isLoading = false;
        footer.findViewById(R.id.load_layout).setVisibility(View.GONE);
    }

    public void setLoadInterface(ILoadListener iLoadListener) {
        this.iLoadListener = iLoadListener;
    }

    //加载更多数据的回调接口
    public interface ILoadListener {
        public void onLoad();
    }

}

 

