package com.tdr.citycontrolpolice.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.util.CheckUtil;
import com.tdr.citycontrolpolice.util.ToastUtil;

import java.util.regex.Pattern;

/**
 * Description：TODO
 * Create Time：2016/10/17 14:43
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class MultieEditText extends LinearLayout {
    private Context context;
    private EditText et_single;
    private TextView tv_single;

    public MultieEditText(Context context) {
        this(context,null);
    }

    public MultieEditText(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MultieEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        View view = View.inflate(context, R.layout.single_edittext, null);
        et_single = (EditText) view.findViewById(R.id.et_single);
        tv_single = (TextView) view.findViewById(R.id.tv_single);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        this.addView(view, layoutParams);
    }

    public String getText() {
        String text = et_single.getText().toString().trim();
        return text;
    }

    public void setText(String text) {
        et_single.setText(text);
    }

    public boolean checkFormat() {
        String text = et_single.getText().toString().trim();
        if (TextUtils.isEmpty(text)) {
            ToastUtil.showMyToast("房东手机号码不能为空");
            return false;
        }

        // 判断手机号格式
        if (!Pattern.matches(
                "^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\\d{8}$", text)) {
            ToastUtil.showMyToast("房东手机号码格式不对");
            return false;
        }
        return true;
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("View", super.onSaveInstanceState());
        bundle.putString("et_single", et_single.getText().toString().trim());
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            et_single.setText(bundle.getString("et_single"));
            super.onRestoreInstanceState(bundle.getParcelable("View"));
        } else {
            super.onRestoreInstanceState(state);
        }
    }
}
