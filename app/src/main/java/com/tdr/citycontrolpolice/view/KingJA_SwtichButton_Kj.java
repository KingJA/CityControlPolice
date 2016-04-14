package com.tdr.citycontrolpolice.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
public class KingJA_SwtichButton_Kj extends LinearLayout {
    private Context context;
    private OnSwitchListener onSwitchListener;
    private TextView leftButton;
    private TextView rightButton;
    private boolean isLeft = true;
    private Drawable leftNorBg;
    private Drawable leftSelBg;
    private Drawable rightNorBg;
    private Drawable rightSelBg;
    private int textSel;
    private int textNor;
    private String leftText;
    private String rightText;


    public KingJA_SwtichButton_Kj(Context context) {
        this(context, null);

    }

    public KingJA_SwtichButton_Kj(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KingJA_SwtichButton_Kj(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.KingJA_SwtichButton);
        leftNorBg = typedArray.getDrawable(R.styleable.KingJA_SwtichButton_leftNorBg);
        leftSelBg = typedArray.getDrawable(R.styleable.KingJA_SwtichButton_leftSelBg);
        rightNorBg = typedArray.getDrawable(R.styleable.KingJA_SwtichButton_rightNorBg);
        rightSelBg = typedArray.getDrawable(R.styleable.KingJA_SwtichButton_rightSelBg);
        textSel = typedArray.getColor(R.styleable.KingJA_SwtichButton_textSel, 0x00000000);
        textNor = typedArray.getColor(R.styleable.KingJA_SwtichButton_textNor, 0x00000000);
        leftText = typedArray.getString(R.styleable.KingJA_SwtichButton_leftText);
        rightText = typedArray.getString(R.styleable.KingJA_SwtichButton_rightText);
        typedArray.recycle();
        this.context = context;
        initView();
    }

    private void initView() {
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1.0f);
        leftButton = new TextView(context);
        leftButton.setGravity(Gravity.CENTER);
        leftButton.setPadding(AppUtil.dip2px(8), AppUtil.dip2px(8), AppUtil.dip2px(8), AppUtil.dip2px(8));
        leftButton.setText(leftText);
        leftButton.setTextColor(isLeft ? textSel : textNor);
        leftButton.setBackgroundDrawable(isLeft ? leftSelBg : leftNorBg);
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
        rightButton.setText(rightText);
        rightButton.setGravity(Gravity.CENTER);
        rightButton.setPadding(AppUtil.dip2px(8), AppUtil.dip2px(8), AppUtil.dip2px(8), AppUtil.dip2px(8));
        rightButton.setTextColor(isLeft ? textNor : textSel);
        rightButton.setBackgroundDrawable(isLeft ? rightNorBg : rightSelBg);
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
        leftButton.setBackgroundDrawable(isLeft ? leftSelBg : leftNorBg);
        leftButton.setTextColor(isLeft ? textSel : textNor);
        rightButton.setBackgroundDrawable(isLeft ? rightNorBg : rightSelBg);
        rightButton.setTextColor(isLeft ? textNor : textSel);
//        leftButton.setBackgroundResource(isLeft?R.drawable.switch_left_sel:R.drawable.switch_left_nor);
//        rightButton.setBackgroundResource(isLeft?R.drawable.switch_right_nor:R.drawable.switch_right_sel);
    }
}
