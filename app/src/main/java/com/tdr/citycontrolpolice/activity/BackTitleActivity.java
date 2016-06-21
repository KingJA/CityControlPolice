package com.tdr.citycontrolpolice.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：带返回按钮的Activity
 * 创建人：KingJA
 * 创建时间：2016/3/24 10:56
 * 修改备注：
 */
public abstract class BackTitleActivity extends BaseActivity {
    protected View view;
    protected boolean isFinished;
    @Bind(R.id.rl_top_back_left)
    RelativeLayout rlTopBackLeft;
    @Bind(R.id.rl_top_back_right)
    RelativeLayout rlTopBackRight;
    @Bind(R.id.rl_parent)
    RelativeLayout rlParent;
    @Bind(R.id.tv_right)
    TextView tvRight;
    private OnRightClickListener onRightClickListener;

    @Bind(R.id.tv_top_back_title)
    TextView tvTopBackTitle;
    @Bind(R.id.fl_top_back_content)
    FrameLayout flTopBackContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_top_back);
        ButterKnife.bind(this);
        view = setContentView();
        initTopView();
        super.onCreate(savedInstanceState);

    }


    @OnClick(R.id.rl_top_back_left)
    void onBack() {
        finish();
    }

    @OnClick(R.id.rl_top_back_right)
    void onRight() {
        if (onRightClickListener != null) {
            onRightClickListener.onRightClick();
        }
    }

    @OnClick(R.id.tv_right)
    void onRightText() {
        if (onRightClickListener != null) {
            onRightClickListener.onRightClick();
        }
    }

    /**
     * 初始化TopView
     */
    private void initTopView() {
        View child = setContentView();
        if (child != null) {
            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT);
            flTopBackContent.addView(child, params);
        }
    }


    /**
     * 设置标题
     */
    public void setTitle(String title) {
        tvTopBackTitle.setText(title);
    }

    /**
     * 设置右侧按钮是否可见
     */
    public void setRightImageVisibility(int visibility) {
        rlTopBackRight.setVisibility(visibility);
    }

    public void setRightTextVisibility(String tv) {
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(tv);
    }


    /**
     * 右侧按钮触发事件
     */
    interface OnRightClickListener {
        void onRightClick();
    }


    public abstract View setContentView();

    public void setOnRightClickListener(OnRightClickListener onRightClickListener) {
        this.onRightClickListener = onRightClickListener;
    }

}
