package com.tdr.citycontrolpolice.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.util.AppUtil;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：SwintchButton初版
 * 创建人：KingJA
 * 创建时间：2016/4/5 9:20
 * 修改备注：
 */
public class KingJA_SwtichButton extends LinearLayout {
    private Context context;
    private OnSwitchListener onSwitchListener;
    private TextView leftButton;
    private TextView rightButton;
    private boolean isLeft = true;


    public KingJA_SwtichButton(Context context) {
        this(context, null);

    }

    public KingJA_SwtichButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KingJA_SwtichButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    private void initView() {
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1.0f);
        leftButton = new TextView(context);
        leftButton.setGravity(Gravity.CENTER);
        leftButton.setPadding(AppUtil.dp2px(8), AppUtil.dp2px(8), AppUtil.dp2px(8), AppUtil.dp2px(8));
        leftButton.setText("警号");
        leftButton.setTextColor(Color.parseColor("#ffffff"));
        leftButton.setBackgroundResource(isLeft ? R.drawable.switch_left_sel : R.drawable.switch_left_nor);
        leftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isLeft = true;
                if (onSwitchListener != null) {
                    setBackground(true);
                    onSwitchListener.onSwitch(true);
                }
            }
        });


        rightButton = new TextView(context);
        rightButton.setText("用户名");
        rightButton.setGravity(Gravity.CENTER);
        rightButton.setPadding(AppUtil.dp2px(8), AppUtil.dp2px(8), AppUtil.dp2px(8), AppUtil.dp2px(8));
        rightButton.setTextColor(Color.parseColor("#ffffff"));
        rightButton.setBackgroundResource(isLeft ? R.drawable.switch_right_nor : R.drawable.switch_right_sel);
        rightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isLeft = false;
                if (onSwitchListener != null) {
                    setBackground(isLeft);
                    onSwitchListener.onSwitch(isLeft);
                }
            }
        });
        addView(leftButton, layoutParams);
        addView(rightButton, layoutParams);
    }

    public void setOnSwitchListener(OnSwitchListener onSwitchListener) {

        this.onSwitchListener = onSwitchListener;
    }


    public interface OnSwitchListener {
        void onSwitch(boolean isLeft);
    }

    private void setBackground(boolean isLeft) {
        leftButton.setBackgroundResource(isLeft ? R.drawable.switch_left_sel : R.drawable.switch_left_nor);
        rightButton.setBackgroundResource(isLeft ? R.drawable.switch_right_nor : R.drawable.switch_right_sel);
    }

    public void setSwitch(boolean isLeft) {
        this.isLeft = isLeft;
        setBackground(isLeft);
    }
}
